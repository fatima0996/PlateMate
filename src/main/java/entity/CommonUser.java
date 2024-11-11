package entity;

import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String[] getDietaryRestrictions() {
        return new String[0];
    }


    public String[] getAllergies() {
        return new String[0];
    }


    public String[] getHealthGoals() {
        return new String[0];
    }

}
