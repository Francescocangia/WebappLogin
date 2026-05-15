package com.webapplogin.webapplogin.DTO;


//siccome nella richiesta dell'api update posso avere un solo body mi serbe un dto che contenga primo e secondo valore inserito

import com.webapplogin.webapplogin.Entity.User;
import lombok.Data;

@Data
public class UpdateRequest {


        private String value;
        private User updatedUser;

        // getter e setter
    }




