package com.example.internetbankingbackend.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;

/* JwtAuthorizationFilter est le filter qui doit etre executé une fois le user envoie des requets
* (par exemple : getUsers, Create accounts, new Customer...) et ça fait après l'authentification
* avec le filter JwtAuthenticationFilter*/
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*Obtenir le header qui s'appele Authorization et qui se trouve dans la requet reçu*/
        String authorizationToken = request.getHeader("Authorization");
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")/* Bearer est un prefex qui utilise pour dire
        que il y'a un token dans ce header*/){
            /*try/catch pour vérifier si le token est valide ou non */
            try {
                String jwt = authorizationToken.substring(7); /*pour ignorer les 7 premiere caracteres afin d'obtenir notre token*/
                Algorithm algorithm = Algorithm.HMAC256("ie017"); /*il doit utiliser le meme secret qui deja utilisée dans le JwtAuthenticationFilter
                , et tout ça pour le décrypter*/
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();/*Créer un vérificateur basée sur notre algorithme*/
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt); /*Pour verifier notre Jwt TOKEN et stoker les claims
                de notre token comme subject, roles...*/
                /*Si il n'y a pas de probleme*/
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class); /*j4ai un liste des roles il doit le convertir a une collection
                de type GrantedAuthority*/
                Collection<GrantedAuthority> authorities = new ArrayDeque<>();
                for (String r:roles){
                    authorities.add(new SimpleGrantedAuthority(r));
                }
                /*Enregistrer le user avec l'objet UsernamePasswordAuthenticationToken*/
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null,authorities); /* On a pas besoin de
                        password donc noter null dans le deuxieme parametre*/

                /* Faire l'authentification de ce user la*/
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); /* On a besoin de passer sur cette etape
                 parceque l'accées a des ressource doit avoir l'authenrtification (http.authorizeRequests().anyRequest().authenticated();),
                 et pour filtrer si la ressource est disponible a ce user ou non*/

                filterChain.doFilter(request,response); /*Passe a le suivant (dispatcherServlet pour faire un get ou set ou ...)
                 at avec authentification car on a deja fait l'authentification, spring regarder si le resource demander necessite
                  une authentification ou non, si oui il passe sans probleme*/
            } catch (Exception e){
                /* Si le token expirer*/
                response.setHeader("error_message",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else{
            /* Si la requet no contient pas le header Authorization */
             filterChain.doFilter(request,response);/*Passe mais sans authentification
             spring regarder si le resource demander necessite une authentification ou non
             , si non il passe sans probleme*/
        }
    }
}
