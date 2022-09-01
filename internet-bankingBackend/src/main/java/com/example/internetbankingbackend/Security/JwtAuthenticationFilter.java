package com.example.internetbankingbackend.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    /* attemptAuthentication appelé quand je faire l'authentification dans le
    * formulaire*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        /* récupérer le pw et user*/
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+" --- "+password);
        /* Stocker les deux variables dans un objet UsernamePasswordAuthenticationToken
        * qui attend deux parametres */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        /* authenticate est la methode qui fait toutes les operation de la recherche
        * dans la base de données et l'envoie des roles et tot ça ... jusqu'à l'arrivée a
        * la verification de mot de passe */
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    /* successfulAuthentication est appelé quand l'authentification à reussire*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        /* getPrincipal permet de retourner le user authentifier a partir de
        * parametre authResult*/
        User user = (User) authResult.getPrincipal(); /* Cast (User) car getPrincipal retourne valuer de
        type object*/
        /* --- Generer le token JWT mais pour cela il faut ajouter java JWT maven --- */
        /* Calculer la signateur de jwt avec HMAC ou RSA */
        Algorithm algorithm = Algorithm.HMAC256("ie017"); /*HMAC256 damande un cle privée*/
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+7*60*1000))/*7 min par ms*/
                .withIssuer(request.getRequestURI().toString())/*le nom de l'app qui gerer le token*/
                .withClaim("roles",user.getAuthorities().stream().map(g->g.getAuthority()).collect(Collectors.toList()))/*map pour convertir un objet vers un autre
                , collect pou le convertir en liste*/
                .sign(algorithm);
        /* Envoyer cette token vers le client dans un header*/
        response.setHeader("Authorization", jwtAccessToken); /* Authorization est le nom de ce header*/

        /*On se pose que le durée d'expiration est finie, et le user besoin de entree a notre app
        * dans ce cas il faut creer un autre token qui s'appele refreshToken qui y'a un durée
        * plus lang*/

        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000))/*30 min par ms*/
                .withIssuer(request.getRequestURI().toString())/*le nom de l'app qui gerer le token*/
                .sign(algorithm);
        /* creer un map qui contient tout les header de la response qui on a*/
        Map<String, String> tokens=new HashMap<>();
        tokens.put("acess_token", jwtAccessToken);
        tokens.put("refresh_token", jwtRefreshToken);
        /*Envoyer l'objet map en format json vers le client dans un header, et avec l'utilisation de
        * ObjectMapper*/
        response.setContentType("application/json"); /*por informer qui la reponse contient des formats json*/
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
}
