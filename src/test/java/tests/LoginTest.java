package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Login;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    Login loginPage;

    @BeforeEach
    void initPage() {
        loginPage = new Login(driver);
    }

    @Test
    void loginSuccess() {

        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("inventory_list")
                ));

        assertTrue(loginPage.isLoginSuccess());
    }

    @Test
    void loginFailed_wrongPassword_showCorrectErrorMessage() {

        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("wrong_password");
        loginPage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h3[data-test='error']")
                ));

        assertEquals(
                "Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage()
        );
    }

    @ParameterizedTest
    @CsvSource({
            "standard_user, wrong_password",
            "locked_out_user, secret_sauce",
            "'', secret_sauce",
            "standard_user, ''"
    })
    void loginFailed_multipleData(String username, String password) {

        driver.get("https://www.saucedemo.com/"); // reset page

        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h3[data-test='error']")
                ));

        assertTrue(loginPage.isErrorDisplayed());
    }
}
