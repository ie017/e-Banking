package com.example.internetbankingbackend.Security;

import com.example.internetbankingbackend.Security.AppUser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration // Ajouter a la classe qui doit etre traiter au démarrage de l'application
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private SecurityService securityService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            /* SS fait une appelle a cette methode une fois il recuperer username notée dans la formulaire*/
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                /* Après la recuperation de username il faut obtenir le user avec l'utilisation de l'interface
                * securityService*/
                AppUser appUser = securityService.loadUserByUsername(username);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                appUser.getAppRoles().forEach(role->{
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                    authorities.add(authority);
                });
                /* new User() est le user retourner par SS qui admet trois parametres username, pw, collection des roles */
                return new User(appUser.getUsername(), appUser.getPassword(), authorities);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* The default type of authentication of SS is stateful (Session stockée au niveau de serveur) */
        /*CSRF synchronize is enable by default and we can check it in the formLogin by using inspect of browser*/
        /* To unable the form login*/
        //http.formLogin();
        /* Authentification est nécessaire a travers un formulaire formLogin*/
        http.authorizeRequests().anyRequest().authenticated();
        /* Pour accées a l'application directement*/
        //http.authorizeRequests().anyRequest().permitAll();

        /*---------------------------------------------------------------------*/
        /* maintenant on commance par la configuration de l'authentification Stateless */
        /*1- disable CSRF synchronize car on ne pas besoin d'utiliser les session*/
        http.csrf().disable();
        /*2- il faut le supprimer la formulaire Login car on va commancer a travailler le front-end*/
        http.formLogin().disable();
        /*3- Activer Stateless*/
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*4- Ajouter un filter d'authentification*/
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        /*5- add le filter d'authorisation comme le premiere filter qui doit s'execute et de type
        * UsernamePasswordAuthenticationFilter*/
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
