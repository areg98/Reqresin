package pojo;

import lombok.Data;

@Data
public class Login {
    private String email;
    private String password;
    private String token;
    private String error;

    public Login(){};

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
