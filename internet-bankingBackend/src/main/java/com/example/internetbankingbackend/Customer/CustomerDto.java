package com.example.internetbankingbackend.Customer;

import lombok.Data;
/* Ce classe etait crée pour resoudre le probleme d'avoir la multiplicié des variables d'entitie au
niveau des Json file après l'appliquer de Rest ipa*/
@Data /* Just setters and getters*/
public class CustomerDto {
    private long id;
    private String name;
    private String email;
}
