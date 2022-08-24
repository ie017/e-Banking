package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.Exceptions.BalanceNotSufficientException;
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
    @GetMapping(path = "/getbankaccount/{id}")
    public BankAccountDto getbankaccounts(@PathVariable(name = "id") String bankAccountId){
        return bankService.getBankAccount(bankAccountId);
    }

    @PutMapping(path = "/debit/{id}{amount}")
    public void debit(@PathVariable(name = "id") String bankAccountId, @PathVariable(name = "amount") double amount){
        try {
            bankService.debit(bankAccountId, amount, "Put it");
        } catch (BalanceNotSufficientException e) {
            e.printStackTrace();
        }
    }
    @PutMapping(path = "/credit/{id}{amount}")
    public void credit(@PathVariable(name = "id") String bankAccountId, @PathVariable(name = "amount") double amount){
        bankService.credit(bankAccountId, amount, "Put it");
    }
}
