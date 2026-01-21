package brigada4.mpi.maglogisticabackend.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ExtractorPage {

    private final WebDriver driver;
    private final By takeAppButton = By.xpath("//*[@id=\"container\"]/div[2]/div[2]/div[5]/button");
    private final By completeAppButton = By.xpath("//*[@id=\"container\"]/div[2]/div[2]/div[5]/button");
    private final By checkButton = By.xpath("//*[@id=\"modalBody\"]/div/button[1]");
    private final By createAppToHunterButton = By.xpath("//*[@id=\"requst_btn\"]");
    private final String animalSelectString = "//*[@id=\"modalBody\"]/select";
    private final By quantityInput = By.xpath("//*[@id=\"modalBody\"]/input[1]");
    private final By dateInput = By.xpath("//*[@id=\"modalBody\"]/input[2]");
    private final By submitButton = By.xpath("//*[@id=\"modalBody\"]/button[2]");

    public ExtractorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void takeApp() {
        driver.findElement(takeAppButton).click();
    }

    public void completeApp() {
        driver.findElement(completeAppButton).click();
    }

    public void checkAnimalAvailability() {
        driver.findElement(checkButton).click();
    }

    public void openCreateHunterApplication() {
        driver.findElement(createAppToHunterButton).click();
    }

    public void fillHunterApplication(String quantity) throws InterruptedException {
        WebElement animalSelectEl = driver.findElement(By.xpath(animalSelectString));
        Select magicSelect = new Select(animalSelectEl);
        magicSelect.selectByValue("5a76436c-4ab8-405f-82fd-1e07943260b2");

        driver.findElement(quantityInput).sendKeys(quantity);
        driver.findElement(dateInput).sendKeys("01.12.2026");

        driver.findElement(submitButton).click();
    }
}
