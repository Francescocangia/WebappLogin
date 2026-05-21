package com.webapplogin.webapplogin.Controller;


import com.webapplogin.webapplogin.DTO.DTOlogin;
import com.webapplogin.webapplogin.DTO.UpdateRequest;
import com.webapplogin.webapplogin.Entity.User;
import com.webapplogin.webapplogin.Repository.Repository;
import com.webapplogin.webapplogin.Service.CustomUserDetailsService;
import com.webapplogin.webapplogin.Service.JWTService;
import com.webapplogin.webapplogin.Service.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AUTHController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    public Repository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public Service service;

// cross origini mettiamo origins = 5137 perchè con docker avevo imposto quella porta...senza docker bisogna mettere 5173 che è la porta di vite.
    //credo nno sono sicuro
//nota : con docker usare solo quest'annotazione non risolve il problema del cors...ci vuole una configirzione nel backend (una classe annotata con configurazione etc.)classe che in questo programma non ho.
    @CrossOrigin(origins = "http://localhost:5137")
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> autentication (@RequestBody DTOlogin login, HttpServletRequest request)  {
        //NOTA FONDAMENTALE : TRY CATCh FONDAMENTALE PER GESTIRE L'ECCEIZONE. e capire l'errore.
        // non serve per far funzionare tutto ma solo per capire dove st l'errore

        try {
            Authentication auth =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    login.getLogin(),
                                    login.getPassword()
                            ));
            //parte 1: fondamentale parte sotto per salvare l'autenticazione in modo che risulta tra una richiesta htpp e la succesiva (ad es. in modo che nella richiesta successia chi ha fatto lgin resti loggato
            // si puo' fare anche con implementazione di sessioni e jwt toke.
           /* SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);*/
        //parte 2. fondamentale per salvare come sopra ma relativo ala richiesta http.
            //parte uno e due servono per salvare la sessione tra una richiesta http e la successiva.
           /*HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );*/
            User user = repository.findByUsernameOrEmail(login.getLogin(),login.getLogin());

            if (user == null) {
                return ResponseEntity.status(401)
                        .body(Map.of("error", "user not found"));
            }
            System.out.println("AUTH OK: " + auth.isAuthenticated());
            String token = JWTService.generateToken(
                    auth.getName(),
                    auth.getAuthorities().toString()
            );

            return ResponseEntity.ok(Map.of("login effettuato: ecco il token", token));
        }


         catch (Exception e) {
            e.printStackTrace();   // 👈 QUESTO TI DICE IL VERO MOTIVO


             //il system sotto serve a verificare se pass123 e la password hashata matchano.
           /* User user = repository.findByUsername(login.getUsername());

            System.out.println("RAW: " + login.getPassword());
            System.out.println("DB: " + user.getPassword());
            System.out.println("RAW: " + login.getPassword());
            System.out.println("DB: " + user.getPassword());
            System.out.println("MATCH: " + passwordEncoder.matches(login.getPassword(), user.getPassword()));

            */
             return (ResponseEntity<Map<String, String>>) ResponseEntity.status(401).body(Map.of("messaggio","password errata"))
        ;}


    }


        @PostMapping("login/register")
        public String register (@RequestBody User u){

        User user = new User();
        user.setUsername(u.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setRole(u.getRole());
        user.setEmail(u.getEmail());
        user.setName(u.getName());
        user.setSurname(u.getSurname());


        repository.save(user);  //NOTA: QUI PER FAR VELOCE HO USATO DIRETTAMENTE IL METODO DEL REPOSITORY SENZA PASARE DAL SERVICE.

        return "USER CREATED";
    }
    @PostMapping("login/Update")
    public ResponseEntity<String> Update( @RequestBody UpdateRequest request){

    service.update( request.getValue(), request.getUpdatedUser());

        return ResponseEntity.ok("update accomplished");
    }

    }
