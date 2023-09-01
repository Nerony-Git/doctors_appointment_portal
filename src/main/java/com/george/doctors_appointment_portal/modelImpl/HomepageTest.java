package com.george.doctors_appointment_portal.modelImpl;

import com.george.doctors_appointment_portal.Homepage;
import com.github.javafaker.Faker;
import com.ibm.icu.impl.Assert;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import  java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.*;
import static com.ibm.icu.impl.Assert.*;


@GraphWalker(value = "random(edge_coverage(100))", start = "e_init")
public class HomepageTest extends ExecutionContext implements Homepage {

    static WebDriver driver = null;
    static WebDriverWait waiter = null;
    static ErrorCollector collector = new ErrorCollector();
    static Faker faker = new Faker();
    // Format the date in "yyyy-MM-dd" format
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void v_DoctorSignup() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Doctor Register"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Doctor Register"));
    }

    @Override
    public void v_DoctorLogin() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Doctor Login"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Doctor Login"));
    }

    @Override
    public void v_AdminSignup() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Admin Register"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Admin Register"));
    }

    @Override
    public void e_AdminSignupFailed() {
        // Scroll to the end of the page using JavaScript
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", button);

        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();*/
    }

    @Override
    public void e_DoctorLogout() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(4) a"))).click();
    }

    @Override
    public void e_DoctorSignupSuccess() {
        fillUserData();

        // Wait for the speciality dropdown to be present
        WebElement specialityDropdown = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("speciality")));


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));

        // Click on the dropdown to open it
        /*specialityDropdown.click();*/
        waiter.until(ExpectedConditions.visibilityOf(specialityDropdown)).click();

        // Select a random option from the speciality dropdown
        Select speciality = new Select(specialityDropdown);
        List<WebElement> enabledOptions = speciality.getOptions().stream()
                .filter(WebElement::isEnabled)
                .collect(Collectors.toList());

        if (!enabledOptions.isEmpty()) {
            int randomIndex = new Random().nextInt(enabledOptions.size());
            WebElement selectedOption = enabledOptions.get(randomIndex);
            String optionValue = selectedOption.getAttribute("value");
            speciality.selectByValue(optionValue);
        } else {
            // Handle the case when no enabled options are available
            // For example, you can log an error message or take another action.
        }

        // Clear and enter qualification using explicit wait
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("qualification"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("qualification"))).sendKeys(faker.medical().diseaseName());

        // Clear and enter password using explicit wait
        /*waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());*/

        // Click on the "Submit" button
        WebElement password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", password);
        executor.executeScript("arguments[0].value = arguments[1];", password, faker.internet().password());
        executor.executeScript("arguments[0].click();", button);
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();*/
    }

    @Override
    public void e_CustomerSignupFailed() {
        fillUserData();

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", button);
        // Scroll to the end of the page using JavaScript
        /*((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();*/
    }

    @Override
    public void e_AdminLoginSuccess() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys("test");
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys("12345");
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void e_AdminLoginFailed() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys(faker.name().username());
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void e_DoctorLoginSuccess() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys("test");
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys("12345");
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void e_CustomerSignup() {
        // Scroll down by 500 pixels
        /*((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");*/

        // Scroll to the end of the page using JavaScript
        /*((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");*/
        WebElement signupDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("signup")));

        // Hover over the "Signup" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(signupDropdown).perform();

        // Wait for the "Signup" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(1) a"))).click();

        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("SIGNUP"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(1) a"))));*/
    }

    @Override
    public void v_Admin() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void e_CustomerLogin() {
        // Scroll down by 500 pixels
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement loginDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("login")));

        // Hover over the "Login" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(loginDropdown).perform();

        // Wait for the "Login" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(1) a"))).click();

        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("LOGINS"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(1) a"))));*/
    }

    @Override
    public void v_Homepage() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal"));
    }

    @Override
    public void v_Customer() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void e_CustomerSignupSuccess() {
        fillUserData();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));

        /*waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address"))).sendKeys(faker.address().fullAddress());
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("postalAddress"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("postalAddress"))).sendKeys(faker.address().zipCode());
        // Scroll to the end of the page using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());*/

        WebElement address = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address")));
        WebElement postalAddress = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("postalAddress")));
        WebElement password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", address);
        executor.executeScript("arguments[0].value = arguments[1];", address, faker.address().fullAddress());
        executor.executeScript("arguments[0].value = '';", postalAddress);
        executor.executeScript("arguments[0].value = arguments[1];", postalAddress, faker.address().zipCode());
        executor.executeScript("arguments[0].value = '';", password);
        executor.executeScript("arguments[0].value = arguments[1];", password, faker.internet().password());
        executor.executeScript("arguments[0].click();", button);
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();*/
    }

    @Override
    public void e_DoctorSignupFailed() {
        fillUserData();

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", button);
        // Scroll to the end of the page using JavaScript
        /*((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();*/
    }

    @Override
    public void e_DoctorLogin() {
        // Scroll down by 500 pixels
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement loginDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("login")));

        // Hover over the "Login" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(loginDropdown).perform();

        // Wait for the "Login" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(2) a"))).click();

        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("LOGINS"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(2) a"))));*/
    }

    @Override
    public void e_Homepage() {
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".navbar-brand"))).click();
    }

    @Override
    public void e_DoctorLoginFailed() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys(faker.name().username());
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void v_Doctor() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void v_CustomerSignup() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - User Register"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - User Register"));
    }

    @Override
    public void e_AdminLogin() {
        // Scroll down by 500 pixels
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement loginDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("login")));

        // Hover over the "Login" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(loginDropdown).perform();

        // Wait for the "Login" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(3) a"))).click();

        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("LOGINS"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#login ul li:nth-child(3) a"))));*/
    }

    @Override
    public void e_CustomerLogout() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(4) a"))).click();
    }

    @Override
    public void v_AdminLogin() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Admin Login"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Admin Login"));
    }

    @Override
    public void e_AdminSignup() {
        // Scroll down by 500 pixels
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement signupDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("signup")));

        // Hover over the "Signup" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(signupDropdown).perform();
        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("SIGNUP"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(3) a"))));*/

        // Wait for the "Signup" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(3) a"))).click();
    }

    @Override
    public void e_CustomerLoginSuccess() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys("test");
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys("12345");
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void e_init() {
        if (driver == null) {
            driver = new FirefoxDriver();
        }
        /*Assert.assertNotNull(driver);*/
        waiter = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/");
    }

    @Override
    public void e_DoctorSignup() {
        // Scroll down by 500 pixels
        /*((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");*/
        // Scroll to the end of the page using JavaScript
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement signupDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("signup")));

        // Hover over the "Signup" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(signupDropdown).perform();

        // Wait for the "Signup" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(2) a"))).click();

        /*((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("SIGNUP"))));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#signup ul li:nth-child(2) a"))));*/
    }

    @Override
    public void e_AdminSignupSuccess() {
        fillUserData();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));

        /*waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).clear();
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());*/

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", password);
        executor.executeScript("arguments[0].value = arguments[1];", password, faker.internet().password());
        executor.executeScript("arguments[0].click();", button);
        // Scroll to the end of the page using JavaScript
        /*((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();*/
    }

    @Override
    public void e_CustomerLoginFailed() {
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("username"))).sendKeys(faker.name().username());
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("password"))).sendKeys(faker.internet().password());
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Override
    public void e_AdminLogout() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(4) a"))).click();
    }

    @Override
    public void v_CustomerLogin() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - User Login"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - User Login"));
    }

    public void fillUserData() {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName"))), faker.name().firstName());
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName"))), faker.name().lastName());
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName"))), faker.name().username());
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))), faker.name().username());
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("dob"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("dob"))), dateFormat.format(faker.date().birthday()));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("contact"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("contact"))), faker.number().digits(11));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))), faker.internet().emailAddress());
    }

    @org.graphwalker.java.annotation.BeforeExecution
    public void _beforeExecution() {
        System.out.println("Executing: graphwalker_beforeExecution");
        // Create a new instance of the Firefox driver
        /*if (geckoExecPath == null) {
            geckoExecPath = "C:\\Users\\PC\\Documents\\geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", geckoExecPath);
        }*/
        if (driver == null) {
            driver = new FirefoxDriver();
        }
    }

    @org.graphwalker.java.annotation.AfterExecution
    public void _afterExecution() {
        System.out.println("Executing: graphwalker_afterExecution");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @org.graphwalker.java.annotation.BeforeElement
    public void _beforeElement() {
        System.out.println("Executing: graphwalker_beforeElement: " + getCurrentElement().getName());
        /*try {
            Thread.sleep(500); // Sleep for 0.5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    @org.graphwalker.java.annotation.AfterElement
    public void _afterElement() {
        System.out.println("Executing: graphwalker_afterElement: " + getCurrentElement().getName());
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Executing: junit_beforeExecution");
        // Create a new instance of the Firefox driver
        /*if (geckoExecPath == null) {
            geckoExecPath = "C:\\Users\\PC\\Documents\\geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", geckoExecPath);
        }*/
        if (driver == null) {
            driver = new FirefoxDriver();
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("Executing: junit_afterExecution");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Executing: junit_setUp");
        // Create a new instance of the Firefox driver
        /*if (geckoExecPath == null) {
            geckoExecPath = "C:\\Users\\PC\\Documents\\geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", geckoExecPath);
        }*/
        if (driver == null) {
            driver = new FirefoxDriver();
        }
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Executing: junit_tearDown");
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
