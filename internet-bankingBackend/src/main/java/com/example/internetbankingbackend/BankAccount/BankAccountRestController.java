package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.CurrentAccount.CurrentAccountDto;
import com.example.internetbankingbackend.Exceptions.BalanceNotSufficientException;
import com.example.internetbankingbackend.SavingAccount.SavingAccountDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountRestController {
    private BankServiceImp bankService;

    @GetMapping(path = "/getbankaccounts")
    public List<BankAccountDto> getbankaccounts(){
        return bankService.getAllbankaccounts();
    }
    @GetMapping(path = "/getbankAccountsOfUser/{id}")
    public List<BankAccountDto> getbankaccountsOfUser(@PathVariable(name = "id") Long id){
        return bankService.getAllbankaccountsOfUser(id);
    }
    @GetMapping(path = "/getbankaccount/{id}")
    public BankAccountDto getbankaccounts(@PathVariable(name = "id") String bankAccountId){
        return bankService.getBankAccount(bankAccountId);
    }
    @DeleteMapping(path = "/deletebankaccount/{id}")
    public void deleteBankAccount(@PathVariable(name = "id") String bankAccountId){
        bankService.deleteBankAccount(bankAccountId);
    }
    @PostMapping(path = "/debit")
    public void debit(@RequestBody Debit debit){
        try {
            bankService.debit(debit);
        } catch (BalanceNotSufficientException e) {
            e.printStackTrace();
        }
    }
    @PostMapping(path = "/credit")
    public void credit(@RequestBody Credit credit){
        bankService.credit(credit);
    }
    @PostMapping(path = "/transfer")
    public void transfer(@RequestBody Transfer transfer) throws BalanceNotSufficientException {
        bankService.transfer(transfer);
    }
    @PostMapping(path = "/savesavingaccount/")
    public void saveSavingAccount(@RequestBody SavingAccountDto savingAccountDto){
        bankService.saveSavingBankAccount(savingAccountDto.getBalance(), savingAccountDto.getInterestRate(),savingAccountDto.getCustomerDto().getId());
    }
    @PostMapping(path = "/savecurrentaccount/")
    public void saveCurrentAccount(@RequestBody CurrentAccountDto currentAccountDto){
        bankService.saveCurrentBankAccount(currentAccountDto.getBalance(), currentAccountDto.getOverDraft(), currentAccountDto.getCustomerDto().getId());
    }
}
