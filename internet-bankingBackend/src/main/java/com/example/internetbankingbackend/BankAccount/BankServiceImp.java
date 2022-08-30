package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.AccountOperation.AccountOperationDto;
import com.example.internetbankingbackend.AccountOperation.AccountOperationEntity;
import com.example.internetbankingbackend.AccountOperation.AccountOperationRepository;
import com.example.internetbankingbackend.AccountOperation.HistoryDto;
import com.example.internetbankingbackend.CurrentAccount.CurrentAccountEntity;
import com.example.internetbankingbackend.Customer.CustomerDto;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.Customer.CustomerRepository;
import com.example.internetbankingbackend.Enums.OperationType;
import com.example.internetbankingbackend.Exceptions.BalanceNotSufficientException;
import com.example.internetbankingbackend.Exceptions.BankAccountException;
import com.example.internetbankingbackend.Exceptions.CustomerNotFoundException;
import com.example.internetbankingbackend.SavingAccount.SavingAccountEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor /* We can use @Autowired in the three variables*/
@Slf4j /* equal : Logger log = LoggerFactory.getLogger(this.getClass().getName());
 because with Slf4j we have the argument log define by default */
public class BankServiceImp implements BankService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankMapperImpl bankAccountMapper;
    @Override
    public CustomerEntity saveCustomer(CustomerDto customerDto) {
        log.info("Your Customer is saved");
        CustomerEntity customerEntity = bankAccountMapper.fromCustomerDto(customerDto);
        customerRepository.save(customerEntity);
        return customerEntity;
    }

    @Override
    public SavingAccountEntity saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
    throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccountEntity bankAccount = new SavingAccountEntity();
        bankAccount.setInterestRate(interestRate);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        SavingAccountEntity savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }

    @Override
    public List<CustomerDto> getCustomers(String keyword) {
        List<CustomerDto> listOfCustomersDto = new ArrayList<>();
        customerRepository.findByNameContains(keyword).forEach(customerEntity -> {
            listOfCustomersDto.add(bankAccountMapper.fromCustomer(customerEntity));
        });
        return listOfCustomersDto;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        CustomerDto customerDto = new CustomerDto();
        customerDto = bankAccountMapper.fromCustomer(customerRepository.findById(id).orElse(null));
        return customerDto;
    }

    @Override
    public List<CustomerEntity> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CurrentAccountEntity saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccountEntity bankAccount = new CurrentAccountEntity();
        bankAccount.setOverDraft(overDraft);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        CurrentAccountEntity savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }
    @Override
    public List<CustomerDto> listCustomersDto() {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerRepository.findAll().forEach(customer->{
            customerDtoList.add(bankAccountMapper.fromCustomer(customer));
        });
        return customerDtoList;
    }
    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountException {
        BankAccountEntity bankAccountEntity = bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccountEntity == null){
            throw new BankAccountException("Bank Account not found");
        } else {
            if (bankAccountEntity instanceof CurrentAccountEntity){
                return bankAccountMapper.fromCurrentAccountEntity((CurrentAccountEntity) bankAccountEntity);
            } else {
                return bankAccountMapper.fromSavingAccountEntity((SavingAccountEntity) bankAccountEntity);
            }
        }
    }

    @Override
    public void debit(Debit debit) throws BankAccountException, BalanceNotSufficientException {
        BankAccountEntity bankAccount = bankAccountRepository.findById(debit.getId()).orElseThrow(()->new BankAccountException("Bank Account not found"));
        if (bankAccount.getBalance() < debit.getAmount()){
            throw new BalanceNotSufficientException("Balance Not Sufficient");
        }
        AccountOperationEntity accountOperationEntity = new AccountOperationEntity();
        accountOperationEntity.setOperationDate(new Date());
        accountOperationEntity.setType(OperationType.DEBIT);
        accountOperationEntity.setBankAccountEntity(bankAccount);
        accountOperationEntity.setAmount(debit.getAmount());
        accountOperationRepository.save(accountOperationEntity);
        bankAccount.setBalance(bankAccount.getBalance() - debit.getAmount());
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(Credit credit) throws BankAccountException {
        BankAccountEntity bankAccount = bankAccountRepository.findById(credit.getId()).orElseThrow(()->new BankAccountException("Bank Account not found"));
        AccountOperationEntity accountOperationEntity = new AccountOperationEntity();
        accountOperationEntity.setOperationDate(new Date());
        accountOperationEntity.setType(OperationType.CREDIT);
        accountOperationEntity.setBankAccountEntity(bankAccount);
        accountOperationEntity.setAmount(credit.getAmount());
        accountOperationRepository.save(accountOperationEntity);
        bankAccount.setBalance(bankAccount.getBalance() + credit.getAmount());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(Transfer transfer) throws BankAccountException, BalanceNotSufficientException {
        Debit debit = new Debit(transfer.getId(), transfer.getAmount(), "debit");
        debit(debit);
        Credit credit = new Credit(transfer.getDestinationId(), transfer.getAmount(), "credit");
        credit(credit);
    }

    @Override
    public List<BankAccountEntity> listBankAccount(){
        return bankAccountRepository.findAll();
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) throws CustomerNotFoundException {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(()->
                new CustomerNotFoundException("Customer not found"));
        customerDto.setId(id);
        customer = bankAccountMapper.fromCustomerDto(customerDto);
        customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<BankAccountDto> getAllbankaccounts() {
        List<BankAccountDto> bankAccountDtos =  new ArrayList<>();
        listBankAccount().forEach(account->{
            bankAccountDtos.add(getBankAccount(account.getId()));
        });
        return bankAccountDtos;
    }

    @Override
    public void deleteBankAccount(String bankAccountd) {
        bankAccountRepository.deleteById(bankAccountd);
    }

    @Override
    public List<AccountOperationDto> getAllAccountOperation(String bankAccountId) {
        List<AccountOperationDto> accountOperationDtoList = new ArrayList<>();
        List<AccountOperationEntity> listOfAccountOperation = accountOperationRepository.findByBankAccountEntityId(bankAccountId);
        listOfAccountOperation.forEach(accountOperationEntity -> {
            accountOperationDtoList.add(bankAccountMapper.fromAccountOperationEntity(accountOperationEntity));
        });
        return accountOperationDtoList;
    }

    @Override
    public HistoryDto getAccountOperationInPage(String bankAccountId, int page, int size) {
        BankAccountEntity bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(()->new BankAccountException("Bank Account not found"));
        Page<AccountOperationEntity> listOfAccountOperation = accountOperationRepository.findByBankAccountEntityId(bankAccountId, PageRequest.of(page,size));
        HistoryDto historyDto = new HistoryDto();
        historyDto.setAccountOperationDtoList(listOfAccountOperation.getContent().stream().map(accountOperationEntity ->
            bankAccountMapper.fromAccountOperationEntity(accountOperationEntity)).collect(Collectors.toList()));
        historyDto.setSizePage(size);
        historyDto.setCurrentPage(page);
        historyDto.setTotalPages(listOfAccountOperation.getTotalPages());
        return historyDto;
    }

    @Override
    public List<BankAccountDto> getAllbankaccountsOfUser(Long id) {
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        customerRepository.findById(id).get().getBankAccounts().forEach(bankAccountEntity -> {
            if (bankAccountEntity instanceof CurrentAccountEntity){
                bankAccountDtos.add(bankAccountMapper.fromCurrentAccountEntity((CurrentAccountEntity) bankAccountEntity));
            } else {
                bankAccountDtos.add(bankAccountMapper.fromSavingAccountEntity((SavingAccountEntity) bankAccountEntity));
            }
        });
        return bankAccountDtos;
    }
}
