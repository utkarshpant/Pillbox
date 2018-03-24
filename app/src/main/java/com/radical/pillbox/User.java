package com.radical.pillbox;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by utgen on 23-03-2018.
 */

@IgnoreExtraProperties
public class User {
    public String firstName;
    public String lastName;
    public String email;
    public int age;
    public String phone;

    public User() {

    }

    public User (String firstName, String lastName, String email, int age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.phone = phone;
    }
}
