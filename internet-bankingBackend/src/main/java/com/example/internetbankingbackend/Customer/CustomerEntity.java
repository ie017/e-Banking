package com.example.internetbankingbackend.Customer;

import com.example.internetbankingbackend.BankAccount.BankAccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    /* GenerationType.AUTO signifie que la valeur de id peut etre generer par plusieur maniere
    * soit par IDENTITY soit par un autre chose */
    private long id;
    private String name;
    private String email;
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE},mappedBy = "customer") /* mappedBy pour dire a JPA de créer la meme
     tableau que contient custemor et bankacount, si mappedBy n'est pas declarée
      JPA ne génerer pas une seules table*/
    /* Pour dire one customer can have many bankAccount*/
    /* To user the notation @OneToMany BankAccountEntity should be entity */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /*JSON ignore write bankAccounts in our format Json*/
    private List<BankAccountEntity> bankAccounts;
}
