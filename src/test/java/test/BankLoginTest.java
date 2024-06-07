package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import page.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanAuthCodes;
import static data.SQLHelper.cleanDataBase;

public class BankLoginTest {
    LoginPage LoginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }
    @AfterAll
    static void tearDownAll(){
        cleanDataBase();
    }
    @AfterEach
    void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maxinized");
        Map<String,Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enble_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        LoginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = LoginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getСode());
    }
    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWhithoutAddingBase() {
        var authInfo = DataHelper.generateRandomUser();
        LoginPage.validLogin(authInfo);
        LoginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }
    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = LoginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте еще раз.");;
            }
}
