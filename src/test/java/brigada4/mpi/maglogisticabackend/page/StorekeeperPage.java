package brigada4.mpi.maglogisticabackend.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StorekeeperPage {

    private final WebDriver driver;
    private final By takeAppButton = By.xpath("//*[@id=\"container\"]/div[2]/div[2]/div[5]/button");
    private final By completeAppButton = By.xpath("//*[@id=\"container\"]/div[2]/div[2]/div[5]/button");
    private final By checkButton = By.xpath("//*[@id=\"modalBody\"]/div/button[1]");
    private final By createAppToExtractorButton = By.xpath("//*[@id=\"requst_btn\"]");
    private final By volumeInput = By.xpath("//*[@id=\"modalBody\"]/input[1]");
    private final By dateInput = By.xpath("//*[@id=\"modalBody\"]/input[2]");
    private final By submitButton = By.xpath("//*[@id=\"modalBody\"]/button");


    public StorekeeperPage(WebDriver driver) {
        this.driver = driver;
    }

    public void takeApp() {
        driver.findElement(takeAppButton).click();
    }

    public void completeApp() {
        driver.findElement(completeAppButton).click();
    }

    public void checkMagicAvailability() {
        driver.findElement(checkButton).click();
    }

    public void openCreateExtractionApplication() {
        driver.findElement(createAppToExtractorButton).click();
    }

    public void fillExtractionApplication(String extraction) {
        driver.findElement(volumeInput).sendKeys(extraction);
        driver.findElement(dateInput).sendKeys("01.12.2026");
        driver.findElement(submitButton).click();
    }



}