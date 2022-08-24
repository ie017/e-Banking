package com.example.internetbankingbackend.Customer;

import com.example.internetbankingbackend.BankAccount.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor /* He use for injection dependency like @Autowired */
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankService bankService;
    /*@GetMapping("/customers") /* Après la consultation de ce lien nous trouvons que il y'a beaucoup des données
    qui affichées en format JSON au cause de la variable bankAccounts qui definée dans l'entitie customer
    Pour evité ce probleme la on peut annuler la serialisation de ce variable dans notre format JSON apres l'execution de lien /customers
    et pour cela on peut ajouter la notation @JsonProperty(access = JsonProperty.Access.READ_ONLY) dans la classe CustomerEntity */
    /*public List<CustomerDto> customers(){
        return bankService.listCustomersDto();
    }*/
    @GetMapping(path = "/customers")
    public List<CustomerDto> getcustomer(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankService.getCustomer(keyword);
    }
    @PostMapping(path = "/savecustomer")
    public CustomerEntity savecustomer(@RequestBody CustomerDto customerDto){
        return bankService.saveCustomer(customerDto);
    }
    @PutMapping(path = "/updatecustomer/{id}")
    public CustomerDto updatecustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDto customerDto){
        return bankService.updateCustomer(customerId, customerDto);
    }
    @DeleteMapping(path = "/deletecustomer/{id}")
    public void deletecustomer(@PathVariable(name = "id") Long customerId){
        bankService.deleteCustomer(customerId);
    }
}
