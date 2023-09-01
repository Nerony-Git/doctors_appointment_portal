package com.george.doctors_appointment_portal.modelImpl;

import com.george.doctors_appointment_portal.DoctorPage;
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

import java.time.ZoneId;
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
public class DoctorPageTest extends ExecutionContext implements DoctorPage {
    @Override
    public void e_DoctorChangePassword() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Change Password" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(3) a"))).click();
    }

    @Override
    public void v_EditAssignedAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Assigned Appointment Details"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Assigned Appointment Details"));
    }

    @Override
    public void v_AssignedAppointmentsHistory() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Appointments History"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Appointments History"));
    }

    @Override
    public void e_ViewAssignedAppointment() {
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
        e_DoctorHomepage();
    }

    @Override
    public void e_DoctorHomepage() {
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/doctor_dashboard']"))).click();
    }

    @Override
    public void e_AssignedAppointmentsHistory() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/doctor_appointments");
    }

    @Override
    public void v_DoctorHomepage() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void v_ViewAssignedAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Appointment"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Appointment"));
    }

    @Override
    public void e_AssignedAppointments_nav() {
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/doctor_appointment']"))).click();
    }

    @Override
    public void v_DoctorViewProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Profile"));
    }

    @Override
    public void e_EditAssignedAppointment() {
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        for (WebElement link : allLinks) {
            if (link.getText().contains("Edit")) {
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

        System.out.println("No 'Edit' links found.");
        e_DoctorHomepage();
    }

    @Override
    public void v_AssignedAppointments() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Assigned Appointments"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Assigned Appointments"));
    }

    @Override
    public void e_DoctorViewProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(1) a"))).click();
    }

    @Override
    public void v_DoctorEditProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Profile"));
    }

    @Override
    public void e_EditAssignedAppointmentFailed() {
        WebElement status = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("status")));
        WebElement description = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = arguments[1];", status, faker.options().option("Done", "Cancel"));
        executor.executeScript("arguments[0].value = '';", description);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void v_DoctorChangePassword() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Change Password"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Change Password"));
    }

    @Override
    public void e_DoctorEditProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Edit Profile" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(2) a"))).click();
    }

    @Override
    public void e_DoctorEditProfileFailed() {
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement email = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        executor.executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        executor.executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", email);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_DoctorEditProfileSuccess() {
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement email = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        /*JavascriptExecutor executor = (JavascriptExecutor)driver;*/
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        // Clear and set the value for lastName using JavaScript
        executor.executeScript("arguments[0].value = '';", lastName);
        executor.executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());

        // Clear and set the value for otherName using JavaScript
        executor.executeScript("arguments[0].value = '';", otherName);
        executor.executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());

        // Clear the value for email using JavaScript
        executor.executeScript("arguments[0].value = '';", email);
        executor.executeScript("arguments[0].value = arguments[1];", email, faker.internet().emailAddress());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_AssignedAppointments() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/doctor_appointment");
    }

    @Override
    public void e_AssignedAppointmentsHistory_nav() {
        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/doctor_appointments']"))).click();
    }

    @Override
    public void e_EditAssignedAppointmentSuccess() {
        /*WebElement status = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("status")));*/
        WebElement status = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("status")));


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));

        // Click on the dropdown to open it
        waiter.until(ExpectedConditions.visibilityOf(status)).click();

        // Select a random option from the speciality dropdown
        Select speciality = new Select(status);
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
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement response = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("response")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        /*executor.executeScript("arguments[0].value = arguments[1];", status, faker.options().option("Done", "Cancel"));*/
        executor.executeScript("arguments[0].value = '';", response);
        executor.executeScript("arguments[0].value = arguments[1];", response, faker.medical().medicineName());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void v_ViewAppointmentHistoryDetails() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Appointment"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Appointment"));
    }

    @Override
    public void e_ViewAppointmentHistoryDetails() {
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        List<WebElement> viewLinks = allLinks.stream()
                .filter(link -> link.getText().contains("View"))
                .collect(Collectors.toList());

        int numLinks = viewLinks.size();
        if (numLinks > 0) {
            int randomIndex = (int) (Math.random() * numLinks);
            WebElement randomViewLink = viewLinks.get(randomIndex);

            // Scroll the link into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomViewLink);

            // Click the link using JavaScript Executor
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomViewLink);
        } else {
            System.out.println("No 'View' links found.");
        }
    }
}
