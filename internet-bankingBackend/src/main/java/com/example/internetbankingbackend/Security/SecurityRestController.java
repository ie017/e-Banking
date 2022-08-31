package com.example.internetbankingbackend.Security;

import com.example.internetbankingbackend.Security.AppRole.AppRole;
import com.example.internetbankingbackend.Security.AppUser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor /* He use for injection dependency like @Autowired */
@Slf4j
@CrossOrigin("*")
public class SecurityRestController {
    private SecurityService securityService;
    @GetMapping(path = "/users")
    public List<AppUser> getUsers(){
        return securityService.listUsers();
    }
    @GetMapping(path = "/user")
    public AppUser getUser(@RequestBody String username){
        return securityService.loadUserByUsername(username);
    }
    @PostMapping(path = "/saveuser")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return securityService.addNewUser(appUser);
    }
    @PostMapping(path = "/saverole")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return securityService.addNewRole(appRole);
    }
    @PostMapping(path = "/addroletouser")
    public void addRoleToUser(@RequestBody AddRoleToUser addRoleToUser){
        securityService.addRoleToUser(addRoleToUser.getUsername(), addRoleToUser.getRoleName());
    }
}

@Data /* To use getters and setters*/
class AddRoleToUser{
    private String username;
    private String roleName;
}