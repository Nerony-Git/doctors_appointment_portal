package com.george.doctors_appointment_portal.modelImpl;

import com.george.doctors_appointment_portal.CustomerPage;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.george.doctors_appointment_portal.modelImpl.HomepageTest.*;

@GraphWalker(value = "random(edge_coverage(100))")
public class CustomerPageTest extends ExecutionContext implements CustomerPage {
    @Override
    public void e_CustomerEditProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Edit Profile" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(2) a"))).click();
    }

    @Override
    public void e_ViewCustomerAppointment() {
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        for (WebElement link : allLinks) {
            if (link.getText().contains("View")) {
                try {
                    // Re-locate the selected element before interacting with it
                    WebElement editLink = waiter.until(ExpectedConditions.elementToBeClickable(link));

                    // Scroll the link into view
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editLink);

                    // Click the link using JavaScript Executor
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editLink);

                    // Exit the loop after successfully clicking the link
                    return;
                } catch (StaleElementReferenceException e) {
                    // Handle the stale element exception and continue the loop
                    System.out.println("Stale element exception occurred. Retrying...");
                }
            }
        }

        System.out.println("No 'View' links found.");
        e_CustomerHomepage();
    }

    @Override
    public void v_CustomerChangePassword() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Change Password"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Change Password"));
    }

    @Override
    public void v_ViewCustomerAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Appointment Details"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Appointment Details"));
    }

    @Override
    public void e_CustomerEditProfileFailed() {
        threads();
        try {
            // Locate elements immediately before interacting with them
            WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
            WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
            WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
            WebElement address = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address")));

            // Clear and input values using JavaScriptExecutor
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].value = '';", lastName);
            executor.executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
            executor.executeScript("arguments[0].value = '';", otherName);
            executor.executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
            executor.executeScript("arguments[0].value = '';", address);

            // Click the button using JavaScriptExecutor
            executor.executeScript("arguments[0].click();", button);
        } catch (StaleElementReferenceException e) {
            // Handle StaleElementReferenceException by retrying or taking appropriate action
            System.out.println("StaleElementReferenceException occurred: " + e.getMessage());
        }
    }

    @Override
    public void e_CustomerAppointments() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/user_appointment");
    }

    @Override
    public void e_BookAppointment() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/book_appointment");
    }

    @Override
    public void v_CustomerAppointments() {
        threads();
        waiter.until(ExpectedConditions.titleContains("DPA Portal - My Appointments"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - My Appointments"));
    }

    @Override
    public void e_BookAppointmentFailed() {
        // Wait for the speciality dropdown to be present
        WebElement specialityDropdown = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("specialityID")));

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

        WebElement appointmentDate = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("appointmentDate")));
        WebElement description = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", appointmentDate);
        executor.executeScript("arguments[0].value = '';", description);
        executor.executeScript("arguments[0].value = arguments[1];", description, faker.medical().diseaseName());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_CustomerHomepage() {
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/user_dashboard']"))).click();
    }

    @Override
    public void v_BookAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Book Appointment"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Book Appointment"));
    }

    @Override
    public void e_CustomerChangePassword() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Change Password" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(3) a"))).click();
    }

    @Override
    public void v_CustomerHomepage() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void v_CustomerEditProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Profile"));
    }

    @Override
    public void e_BookAppointmentSuccess() {
        threads();
        // Wait for the speciality dropdown to be present
        WebElement specialityDropdown = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("specialityID")));

        // Scroll the dropdown and button elements into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", specialityDropdown);

        // Wait for dropdown options to be loaded and visible
        /*waiter.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("select[name='speciality'] option"), 0));*/

        // Click on the dropdown using JavaScript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", specialityDropdown);

        // Select a random option from the speciality dropdown using selectByValue
        Select specialitySelect = new Select(specialityDropdown);
        List<WebElement> enabledOptions = specialitySelect.getOptions().stream()
                .filter(WebElement::isEnabled)
                .collect(Collectors.toList());

        if (!enabledOptions.isEmpty()) {
            int randomIndex = new Random().nextInt(enabledOptions.size());
            String optionValue = enabledOptions.get(randomIndex).getAttribute("value");
            specialitySelect.selectByValue(optionValue);
        } else {
            // Handle the case when no enabled options are available
            // For example, you can log an error message or take another action.
        }
        // Generate a random future date
        LocalDate futureDate = faker.date().future(7, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String formattedFutureDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        WebElement appointmentDate = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("appointmentDate")));
        WebElement description = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", appointmentDate);
        executor.executeScript("arguments[0].value = arguments[1];", appointmentDate, formattedFutureDate);
        executor.executeScript("arguments[0].value = '';", description);
        executor.executeScript("arguments[0].value = arguments[1];", description, faker.medical().symptoms());
        executor.executeScript("arguments[0].click();", button);
    }

    private void threads() {
        try {
            Thread.sleep(1000); // Sleep for 1 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void e_CustomerViewProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(1) a"))).click();
    }

    @Override
    public void e_CustomerEditProfileSuccess() {
        threads();
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("button[type='submit']"))));

        // Re-locate the elements before interacting with them
        WebElement lastName = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("otherName")));
        WebElement address = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("address")));

        // Clear and set the value for lastName using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", lastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());

        // Clear and set the value for otherName using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", otherName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());

        // Clear the value for address using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", address);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", address, faker.address().fullAddress());

        // Click the button using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }


    @Override
    public void v_CustomerViewProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Profile"));
    }

    /*@Override
    public void e_ChangePasswordFailed() {
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement password2 = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password2")));
        WebElement password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

        // Locate the elements again to avoid stale reference
        password2 = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password2")));
        password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", password2);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", password2, faker.internet().password());
        executor.executeScript("arguments[0].value = '';", password);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", password, faker.internet().password());
        executor.executeScript("arguments[0].click();", button);
    }*/

    @Override
    public void e_CustomerAppointments_nav() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/user_appointment");
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/user_appointment']"))).click();*/
    }
}
