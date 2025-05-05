package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CourierModel {
    private String login;
    private String password;
    private String firstName;

    public CourierModel(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
