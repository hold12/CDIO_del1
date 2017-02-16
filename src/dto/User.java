package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjc on 9/2/17.
 */

public class User {

    private int userId;
    private String userName;
    private String initials;
    private List<String> roles;
    private String cpr;
    private String password;

    public User(int userId, String userName, String initials, List<String> roles, String cpr, String password) {
        this.userId = userId;
        this.userName = userName;
        this.initials = initials;
        this.roles = roles;
        this.cpr = cpr;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getCpr() {
        return cpr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    /**
     * @param role
     * @return true if role existed, false if not
     */
    public boolean removeRole(String role) {
        return this.roles.remove(role);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", initials=" + initials + ", roles=" + roles + "]";
    }


}
