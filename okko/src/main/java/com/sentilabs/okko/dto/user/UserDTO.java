package com.sentilabs.okko.dto.user;

import com.sentilabs.okko.common.LinkedAccounts;

public class UserDTO {

    private String name;
    private String email;
    private String phone;
    private LinkedAccounts linkedAccounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LinkedAccounts getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(LinkedAccounts linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }
}
