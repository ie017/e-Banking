package com.example.internetbankingbackend.BankAccount;

import com.example.internetbankingbackend.AccountOperation.AccountOperationEntity;
import com.example.internetbankingbackend.Customer.CustomerEntity;
import com.example.internetbankingbackend.Enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity /* Pour le mapping objet relationnelle et pour jpa */
/* Si on a une base de données non relationnelle c'est a dire noSQL
on utilisant la notation @Document*/
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) /* Inheritance to make a choice about the three methods
for generate mapping inheritance*/
@DiscriminatorColumn(name = "TYPE", length = 4,discriminatorType = DiscriminatorType.STRING) /* discriminatorType
est string par defaut */
/* length signifie 4 caractere dans la colonne type si discriminatorType est string
* si vous ne mettez length la valeur par defaut est 255 caracteres */

/* Now if we use InheritanceType.TABLE_PER_CLASS we have to remove DiscriminatorColumn and DiscriminatorValue in
 the two classes savingAccount and currentAccount and after that we have to add abstract to bankAccount class
  because in our situation we don't need to create bankAccount Table in DataBase */
/* abstract pour dire creer les tableaux des classes derivée seulement*/
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/* Pour le cas de JOINED meme regles de cas TABLE_PER_CLASS*/
//@Inheritance(strategy = InheritanceType.JOINED)


@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccountEntity {
    @Id
    /* @GeneratedValue doesn't exist here because we will generate the value
    of id with other method not in database*/
    private String id;
    private double balance; /*Solde*/
    private Date createdAt;
    @Enumerated(EnumType.STRING) /* Pour avoir les AccountStatus en format string dans bd,
    par defaut il va afficher en format int */
    private AccountStatus status;
    @ManyToOne /* C'est a dire plusieurs compte pour un client*/
    private CustomerEntity customer;
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "bankAccountEntity", fetch = FetchType.LAZY)
    /* LAZY est par defaut signifie que on ne va charger les données
    que on cas de besoin "we will only load the data we need" null will put in all cases of accountOperationEntityList*/

    /* FetchType.EAGER charger toutes les données une fois on lancée le programme */
    private List<AccountOperationEntity> accountOperationEntityList;
}
