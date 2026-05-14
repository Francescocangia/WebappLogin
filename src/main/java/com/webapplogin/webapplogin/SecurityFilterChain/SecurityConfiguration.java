package com.webapplogin.webapplogin.SecurityFilterChain;


//import com.webapplogin.webapplogin.JWTfilter.Jwtfilter;
import com.webapplogin.webapplogin.JWTfilter.Jwtfilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final Jwtfilter jwtFilter;

    public SecurityConfiguration(Jwtfilter jwtfilter){
        this.jwtFilter = jwtfilter;}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/db/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/login/register").hasRole("ADMIN") //metto hasauthority e non hasrole perchè has role cerca ROLE_ADMIN e non admin mentre hasauthority cerca l'esatta parola che scriviamo cioè ADMIN.
                        .anyRequest().authenticated()
                )
                        .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
               .sessionManagement(session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );




        return http.build();


    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

//NOTA FONDAMENTALE : IL BEAN QUI SOTTO SERVE PER FAR SI CHE SPRINGSECURITY FACCIA SOLO MATCH TRA STRINGHE QUANDO COFROTA LE PASSWORD E NON USI BCRYPT DI CRIPTAZIONE (HASH) CHE è INVECE QUELLO CHE USA DI DEFAULT
    // HO USATO IL BEAN QUI SOTTO PERCHè NON RIECO A USARE IL BEAN QUA SOPRA CON L'HASH DI DEFAULT (LE PASSOWRD SONO UGUALI MA NON FA IL MATCH)
    //IN PRODUZIONE NON SI POTREBBE FAREù/
    //SPRING SECRITY FUNZIONA DI DEFULT CON AUTHENTICATION MANAGER E ENCRIPTION BCRYPT HASH.

       //nota alla fine sono riuscito a usare il bean sopra quello di bcrypt...perchè nella richiesta post bisogna sempre mettere la password in chiaro non quella hashata era uellol'errore.

   // @Bean
   // public PasswordEncoder passwordEncoder() {
    //    return NoOpPasswordEncoder.getInstance();
    }

}
