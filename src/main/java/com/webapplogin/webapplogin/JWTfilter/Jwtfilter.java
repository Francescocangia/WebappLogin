package com.webapplogin.webapplogin.JWTfilter;

import com.webapplogin.webapplogin.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class Jwtfilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;

    //qui faccio inieione di dipendenza. non ho usato autowired perchè essendo final non funziona.
   public Jwtfilter(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        } // da string path a qui il codice serve per impedire al filtro di bloccare la pagina di login quando faccio l'accesso da frontend

        String header = request.getHeader("Authorization");

        System.out.println("JWT FILTER CHIAMATO");
        System.out.println(header);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());


        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        System.out.println("TOKEN: " + token);
        System.out.println("JWT VALID = " + JWTService.isValid(token));
        if (JWTService.isValid(token)) {

            String username = JWTService.extractUsername(token);

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            auth.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("AUTH SET: " +
                    SecurityContextHolder.getContext().getAuthentication());
        }

        System.out.println("FILTER PATH: " + request.getServletPath());
        System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));


        filterChain.doFilter(request, response);

    }
}
