package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProgramKerjaPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator untuk tombol "+ ADD NEW"
    private By addNewButton = By.xpath("//button[normalize-space()='+ ADD NEW']");

    // ================== PERBAIKAN LOKATOR DI SINI ==================
    // Menggunakan CSS Selector untuk menargetkan atribut data-filter
    private By upcomingTab = By.cssSelector("button[data-filter='upcoming']");
    // ==============================================================

    public ProgramKerjaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddNew() {
        System.out.println("Mencari tombol '+ ADD NEW'...");
        wait.until(ExpectedConditions.elementToBeClickable(addNewButton)).click();
        System.out.println("Berhasil mengklik 'Add New'.");
    }

    public void clickUpcomingTab() {
        System.out.println("Mencari tombol tab 'Upcoming'...");
        wait.until(ExpectedConditions.elementToBeClickable(upcomingTab)).click();
        System.out.println("Berhasil mengklik tab 'Upcoming'.");
    }
}