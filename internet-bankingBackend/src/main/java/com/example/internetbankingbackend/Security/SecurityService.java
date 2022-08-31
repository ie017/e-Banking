package com.example.internetbankingbackend.Security;

import com.example.internetbankingbackend.Security.AppRole.AppRole;
import com.example.internetbankingbackend.Security.AppUser.AppUser;

import java.util.List;

public interface SecurityService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roelName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();

}
