package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AdminDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By programKerjaMenu = By.xpath("//a[normalize-space()='Program Kerja']");

    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToProgramKerja() {
        System.out.println("Mencari link dengan XPath: //a[normalize-space()='Program Kerja']");
        wait.until(ExpectedConditions.elementToBeClickable(programKerjaMenu)).click();
        System.out.println("Berhasil mengklik 'Program Kerja'.");
    }
}