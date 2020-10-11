package com.shimkiv.paypalm.pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class Login {
    private void typeUserName(String userName) {
        $(By.id("userName")).setValue(userName);
    }

    private void typePassword(String password) {
        $(By.id("userPassword")).setValue(password);
    }

    private void submit() {
        $(By.xpath("//button[@type='submit']")).click();
    }

    /**
     * Authenticates with provided credentials and optionally checks 'Remember Me'.
     * @param userName username string
     * @param password password string
     * @param shouldBeRemembered if 'true' is passed, checks 'Remember Me'
     */
    public void authenticateWithUserNameAndPassword(String userName, String password, boolean shouldBeRemembered) {
        typeUserName(userName);
        typePassword(password);
        if (shouldBeRemembered) {
            $(By.xpath("//input[@name='remember-me']/..//span[@class='check']")).click();
        }
        submit();
    }
}
