package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.LoginPage;
import pages.ProgramKerjaPage;
import pages.TambahProgramKerjaPage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TambahProkerTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://sirtrw.vansite.cloud/";

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void testTambahProgramKerjaBerhasil() {
        // LANGKAH 1: Navigasi ke Landing Page dan klik Masuk
        driver.get(baseUrl);
        WebElement masukButton = driver.findElement(By.linkText("Masuk"));
        try {
            masukButton.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", masukButton);
        }

        // LANGKAH 2: Tunggu halaman login siap
        wait.until(ExpectedConditions.urlContains("/masuk"));
        System.out.println("Checkpoint: Berhasil navigasi ke halaman login.");

        // LANGKAH 3: Lakukan Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin("adminrt1@gmail.com", "password");

        // LANGKAH 4: Tunggu overlay dashboard menghilang
        System.out.println("Checkpoint: Login berhasil, menunggu data dashboard dimuat...");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading-overlay")));
        System.out.println("Checkpoint: Dashboard siap digunakan.");

        // LANGKAH 5: Masuk ke menu Program Kerja
        AdminDashboardPage adminDashboard = new AdminDashboardPage(driver);
        adminDashboard.goToProgramKerja();

        // LANGKAH 6: Klik "Add New"
        ProgramKerjaPage programKerjaPage = new ProgramKerjaPage(driver);
        programKerjaPage.clickAddNew();

        // LANGKAH 7: Tunggu halaman form tambah program kerja siap
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
        System.out.println("Checkpoint: Halaman Tambah Program Kerja berhasil dimuat.");

        // LANGKAH 8: Isi form
        TambahProgramKerjaPage tambahProkerPage = new TambahProgramKerjaPage(driver);
        String tanggalHariIni = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String judulProker = "Proker Sukses Final " + System.currentTimeMillis();

        tambahProkerPage.fillForm(
                judulProker,
                "1", // Nomor RT
                tanggalHariIni,
                "11:30",
                "Yogyakarta",
                "Deskripsi final dari test automation yang sukses."
        );

        // LANGKAH 9: Klik Simpan
        tambahProkerPage.clickSimpan();

        // LANGKAH 10: Handle alert konfirmasi
        tambahProkerPage.handleAlert();

        // ================== JEDA 5 DETIK ==================
        try {
            System.out.println("Memberikan jeda 5 detik untuk browser memproses redirect...");
            Thread.sleep(5000); // Diubah menjadi 5000 milidetik (5 detik)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // ==================================================

        // LANGKAH 11: Tunggu hingga kembali ke halaman daftar proker
        wait.until(ExpectedConditions.urlContains("/program-kerja/admin"));
        System.out.println("Checkpoint: Berhasil kembali ke halaman Program Kerja.");

        // LANGKAH 12: Pindah ke tab "Upcoming"
        programKerjaPage.clickUpcomingTab();

        // LANGKAH 13: VERIFIKASI AKHIR
        By prokerCardHeader = By.xpath("//div[@id='upcoming']//h5[text()='" + judulProker + "']");
        WebElement prokerDibuat = wait.until(ExpectedConditions.visibilityOfElementLocated(prokerCardHeader));

        Assert.assertTrue(prokerDibuat.isDisplayed(), "Program kerja yang baru dibuat tidak ditemukan.");
        System.out.println("Verifikasi Berhasil: Program kerja '" + judulProker + "' ditemukan di tab Upcoming.");

        System.out.println("\n===================================");
        System.out.println("==  SEMUA LANGKAH TES SELESAI  ==");
        System.out.println("===================================");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}