package brigada4.mpi.maglogisticabackend.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage {

    private final WebDriver driver;
    private final By email = By.xpath("//*[@id=\"login_form\"]/input[1]");
    private final By password = By.xpath("//*[@id=\"login_form\"]/input[2]");
    private final By submit = By.xpath("//*[@id=\"login_form\"]/button");
    private final By logoutMagician = By.xpath("//*[@id=\"container\"]/div[1]/button[3]");
    private final By logout = By.xpath("//*[@id=\"container\"]/div[1]/button");


    public AuthPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:5173");
    }

    public void auth(String emailValue, String passwordValue) {
        driver.findElement(email).sendKeys(emailValue);
        driver.findElement(password).sendKeys(passwordValue);
        driver.findElement(submit).click();
    }

    public void logoutMagician() {
        driver.findElement(logoutMagician).click();
    }

    public void logout() {
        driver.findElement(logout).click();
    }
}
