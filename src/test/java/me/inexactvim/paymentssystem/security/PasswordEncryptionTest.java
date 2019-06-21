package me.inexactvim.paymentssystem.security;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordEncryptionTest {

    private PasswordEncryption passwordEncryption;

    @BeforeTest
    public void setUp() {
        passwordEncryption = PBKDF2WithHmacSHA1PasswordEncryption.getInstance();
    }

    @DataProvider(name = "dataProvider")
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {new String[]{"password", "password"}, true},
                {new String[]{"password", "Password"}, false},
                {new String[]{"123456789", "987654321"}, false}
        };
    }

    @Test(dataProvider = "dataProvider")
    public void test(String[] passwords, boolean result) {
        String encryptedPassword = passwordEncryption.encrypt(passwords[0]);

        assertEquals(passwordEncryption.isValid(passwords[1], encryptedPassword), result);
    }

}
