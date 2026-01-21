package brigada4.mpi.maglogisticabackend;

import brigada4.mpi.maglogisticabackend.page.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullBusinessCycleE2ETest extends BaseE2ETest {

    @Test
    public void fullBusinessCycleE2ETest() throws InterruptedException {

        AuthPage authPage = new AuthPage(driver);
        authPage.open();
        authPage.auth("magician@mail.ru", "admin");

        wait.until(ExpectedConditions.urlContains("/magician"));
        assertTrue(driver.getCurrentUrl().contains("/magician"));

        Thread.sleep(3000);
        MagicianPage magicianPage = new MagicianPage(driver);
        magicianPage.openCreateMagicApplication();
        Thread.sleep(3000);
        magicianPage.fillMagicApplication("10");
        Thread.sleep(3000);

        authPage.logoutMagician();
        Thread.sleep(3000);
        authPage.auth("storekeeper@mail.ru", "admin");
        Thread.sleep(3000);

        StorekeeperPage storekeeperPage = new StorekeeperPage(driver);
        Thread.sleep(3000);
        storekeeperPage.takeApp();
        Thread.sleep(3000);
        storekeeperPage.completeApp();
        Thread.sleep(3000);
        storekeeperPage.checkMagicAvailability();
        Thread.sleep(3000);
        storekeeperPage.openCreateExtractionApplication();
        Thread.sleep(3000);
        storekeeperPage.fillExtractionApplication("10");
        Thread.sleep(3000);

        authPage.logout();
        Thread.sleep(3000);
        authPage.auth("extractor@mail.ru", "admin");
        Thread.sleep(3000);

        ExtractorPage  extractorPage = new ExtractorPage(driver);
        extractorPage.takeApp();
        Thread.sleep(3000);
        extractorPage.completeApp();
        Thread.sleep(3000);
        extractorPage.checkAnimalAvailability();
        Thread.sleep(3000);
        extractorPage.openCreateHunterApplication();
        Thread.sleep(3000);
        extractorPage.fillHunterApplication("10");
        Thread.sleep(3000);

        authPage.logout();
        Thread.sleep(3000);
        authPage.auth("hunter@mail.ru", "admin");
        Thread.sleep(3000);

        HunterPage hunterPage = new HunterPage(driver);
        hunterPage.takeApp();
        Thread.sleep(3000);
        hunterPage.completeApp();
        Thread.sleep(3000);
        hunterPage.enterInfoAboutAnimal();
        Thread.sleep(3000);
        hunterPage.fillHunterApplication("10");
        Thread.sleep(3000);

    }
}
