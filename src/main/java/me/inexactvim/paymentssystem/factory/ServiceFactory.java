package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.service.UserService;

public class ServiceFactory {

    public static AccountService getAccountService() {
        return null;
    }

    public static UserService getUserService() {
        return new TestService();
    }

}
