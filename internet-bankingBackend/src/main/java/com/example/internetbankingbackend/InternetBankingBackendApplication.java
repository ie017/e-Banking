package com.example.internetbankingbackend;

import com.example.internetbankingbackend.AccountOperation.AccountOperationEntity;
import com.example.internetbankingbackend.AccountOperation.AccountOperationRepository;
import com.example.internetbankingbackend.BankAccount.*;
import com.example.internetbankingbackend.CurrentAccount.CurrentAccountEntity;
import com.example.internetbankingbackend.Customer.CustomerDto;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.Customer.CustomerRepository;
import com.example.internetbankingbackend.Enums.AccountStatus;
import com.example.internetbankingbackend.Enums.OperationType;
import com.example.internetbankingbackend.SavingAccount.SavingAccountEntity;
import com.example.internetbankingbackend.Security.AppRole.AppRole;
import com.example.internetbankingbackend.Security.AppUser.AppUser;
import com.example.internetbankingbackend.Security.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class InternetBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetBankingBackendApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("user1", "user2", "user3").forEach(name ->{
                CustomerEntity customer = new CustomerEntity();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
                currentAccountEntity.setId(UUID.randomUUID().toString());
                currentAccountEntity.setCreatedAt(new Date());
                currentAccountEntity.setCustomer(cust);
                currentAccountEntity.setOverDraft(80000);
                currentAccountEntity.setBalance(Math.random()* 80000);
                currentAccountEntity.setStatus(AccountStatus.CREATED);
                bankAccountRepository.save(currentAccountEntity);

                SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
                savingAccountEntity.setId(UUID.randomUUID().toString());
                savingAccountEntity.setCreatedAt(new Date());
                savingAccountEntity.setCustomer(cust);
                savingAccountEntity.setInterestRate(8.5);
                savingAccountEntity.setBalance(Math.random()* 70000);
                savingAccountEntity.setStatus(AccountStatus.CREATED);
                bankAccountRepository.save(savingAccountEntity);
            });
            bankAccountRepository.findAll().forEach(account->{
                for(int i = 0; i < 10; i++){
                    AccountOperationEntity accountOperationEntity = new AccountOperationEntity();
                    accountOperationEntity.setOperationDate(new Date());
                    accountOperationEntity.setAmount(Math.random()* 10000);
                    accountOperationEntity.setType(Math.random() < 0.5 ? OperationType.CREDIT : OperationType.DEBIT);
                    accountOperationRepository.save(accountOperationEntity);
                }
            });
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); /* Utilisée BCrypt pour le cryptage, BCrypte utilise
        la date systeme pour le cryptage*/
    }
    @Bean
    CommandLineRunner commandLineRunner1(BankService bankService, SecurityService securityService){
        return args -> {
            Stream.of("issam", "cochomber1", "cochomber2").forEach(name-> {
                CustomerDto customer = new CustomerDto();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankService.saveCustomer(customer);
            });
            for (CustomerEntity customer: bankService.listCustomers()){
                bankService.saveCurrentBankAccount(Math.random()*40000,5000,customer.getId());
                bankService.saveSavingBankAccount(Math.random()*70000,6000, customer.getId());
            }
            for (BankAccountEntity bankAccount: bankService.listBankAccount()){
                for(int i = 0; i < 6; i++){
                    Debit debit = new Debit(bankAccount.getId(), Math.random()*700 , "debit");
                    Credit credit = new Credit(bankAccount.getId(), Math.random()*900 , "credit");
                    bankService.debit(debit);
                    bankService.credit(credit);
                }
            }
            securityService.addNewUser(new AppUser(null,"ISSAM","1234",null));
            securityService.addNewUser(new AppUser(null,"COCHAMBER1","1234",null));
            securityService.addNewUser(new AppUser(null,"COCHAMBER2","1234",null));

            securityService.addNewRole(new AppRole(null, "ADMIN"));
            securityService.addNewRole(new AppRole(null, "USER"));
            securityService.addNewRole(new AppRole(null, "PRODUCT_MANAGER"));

            securityService.addRoleToUser("ISSAM", "ADMIN");
            securityService.addRoleToUser("ISSAM", "USER");
            securityService.addRoleToUser("COCHAMBER1", "USER");
            securityService.addRoleToUser("COCHAMBER1", "PRODUCT_MANAGER");
            securityService.addRoleToUser("COCHAMBER2", "USER");
        };
    }
    //@Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            BankAccountEntity bankAccountEntity = bankAccountRepository.
                    findById("10e47e6e-e9e1-4a51-9110-e3096ebc61a0").orElse(null);
            if(bankAccountEntity != null){
                System.out.println("**********************");
                System.out.println(bankAccountEntity.getId());
                System.out.println(bankAccountEntity.getBalance());
                System.out.println(bankAccountEntity.getStatus());
                System.out.println(bankAccountEntity.getCreatedAt());
                System.out.println(bankAccountEntity.getCustomer().getName());
                System.out.println(bankAccountEntity.getClass().getSimpleName());
                if (bankAccountEntity instanceof CurrentAccountEntity) {
                    System.out.println("Over Draft =>" + ((CurrentAccountEntity) bankAccountEntity).getOverDraft());
                } else if (bankAccountEntity instanceof SavingAccountEntity){
                    System.out.println("Rate=>"+ ((CurrentAccountEntity) bankAccountEntity).getOverDraft());
                }
                bankAccountEntity.getAccountOperationEntityList().forEach(operation ->{
                    System.out.println(operation.getType() + "\t" + operation.getOperationDate() +"\t"+ operation.getAmount());
                });

            }
        };
    }
}
