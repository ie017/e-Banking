package com.example.internetbankingbackend.Security.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser , Long> {
    AppUser findByUsername(String username);
}
