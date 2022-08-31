package com.example.internetbankingbackend.Security.AppUser;

import com.example.internetbankingbackend.Security.AppRole.AppRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* C'est a dire hidden le password une fois de faire GetMapping
    pour recuperer les Users, acces seulement dans le cas de WRITE (PostMapping)*/
    /*Access setting that means that the property may only be written (set) for deserialization, but will not be read (get)
     on serialization, that is, the value of the property is not included in serialization.*/
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> appRoles = new ArrayList<>();
}
