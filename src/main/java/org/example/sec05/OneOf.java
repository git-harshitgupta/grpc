package org.example.sec05;

import com.harshit.models.sec05.Credentials;
import com.harshit.models.sec05.Email;
import com.harshit.models.sec05.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneOf {
    private static final Logger log = LoggerFactory.getLogger(OneOf.class);

    public static void main(String[] args) {
        var email = Email.newBuilder().setAddress("harshit@gmail.com").setPassword("1234").build();
        var phone = Phone.newBuilder().setNumber(12345).setCode(91).build();
        login(Credentials.newBuilder().setEmail(email).build());
        login(Credentials.newBuilder().setPhone(phone).build());
        login(Credentials.newBuilder().setPhone(phone).setEmail(email).build());
    }

    private static void login(Credentials credentials){
        switch (credentials.getLoginTypeCase()){
            case EMAIL: log.info("email -> {}",credentials.getEmail());
            case PHONE: log.info("phone -> {}",credentials.getPhone());
        }
    }

}
