package pojo;


import lombok.Data;

@Data
public class Register {
    private String email;
    private String password;
    private Integer id;
    private String token;
    private String error;


    public Register(){}


    public Register(String email, String password){
        this.email = email;
        this.password = password;
    }

}
