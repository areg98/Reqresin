package pojo;

import java.util.Date;

import lombok.Data;

@Data
public class UserData {

    public UserData(){}

    public UserData(String name, String job){
        this.name = name;
        this.job = job;
    }
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private String name;
    private String job;
    private Date updatedAt;

}
