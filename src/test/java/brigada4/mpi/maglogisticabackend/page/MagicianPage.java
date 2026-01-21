package brigada4.mpi.maglogisticabackend.page;

import brigada4.mpi.maglogisticabackend.models.MagicApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MagicianPage {

    private final WebDriver driver;
    private final By openCreateModal = By.xpath("//*[@id=\"container\"]/div/button[1]");
    private final By volumeInput = By.name("magicVolume");
    private final By dateInput = By.xpath("//*[@id=\"modalBody\"]/input[2]");
    private final By submitButton = By.xpath("//*[@id=\"modalBody\"]/button[1]");
    private String magicSelectString = "//*[@id=\"modalBody\"]/select[2]";

    public MagicianPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCreateMagicApplication() {
        driver.findElement(openCreateModal).click();
    }

    public void fillMagicApplication(String volumeValue) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"modalBody\"]/input[1]")).sendKeys(volumeValue);
        driver.findElement(dateInput).sendKeys("01.12.2026");

        WebElement magicSelectEl = driver.findElement(By.xpath(magicSelectString));
        Select magicSelect = new Select(magicSelectEl);
        magicSelect.selectByValue("3b626072-75cf-4c2c-9973-8d14febd7bec");

        driver.findElement(submitButton).click();
    }
}
