package com.sentilabs.okko.common;

import java.util.List;

public class LinkedAccounts {

    private List<FacebookAccount> facebookAccounts;
    private List<VKAccount> vkAccounts;

    public List<FacebookAccount> getFacebookAccounts() {
        return facebookAccounts;
    }

    public void setFacebookAccounts(List<FacebookAccount> facebookAccounts) {
        this.facebookAccounts = facebookAccounts;
    }

    public List<VKAccount> getVkAccounts() {
        return vkAccounts;
    }

    public void setVkAccounts(List<VKAccount> vkAccounts) {
        this.vkAccounts = vkAccounts;
    }
}
