package ru.mipt.apps.cleankpm.userObjects;

import java.io.Serializable;

/**
 * Created by User on 2/25/2015.
 */
public class UserInitials implements Serializable {
    protected String name;
    protected String password;
    public UserInitials(String newName, String newPassword){
        name=newName;
        password=newPassword;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
}
