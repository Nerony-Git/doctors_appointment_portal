package com.george.doctors_appointment_portal.modelImpl;

import com.george.doctors_appointment_portal.AdminPage;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.george.doctors_appointment_portal.modelImpl.HomepageTest.*;

@GraphWalker(value = "random(edge_coverage(100))")
public class AdminPageTest extends ExecutionContext implements AdminPage {
    @Override
    public void e_AppointmentsHistory() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/view_appointments");
    }

    @Override
    public void v_AddNewDoctor() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Add New Doctor"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Add New Doctor"));
    }

    @Override
    public void e_ViewCustomer() {
        threads();
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
        e_AdminHomepage();
    }

    @Override
    public void v_ViewDoctor() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Doctor Details"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Doctor Details"));
    }

    @Override
    public void v_AdminEditProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Profile"));
    }

    @Override
    public void v_Customers() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Customers"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Customers"));
    }

    @Override
    public void e_EditDoctorSuccess() {
        threads();
        // Wait for the speciality dropdown to be present
        WebElement specialityDropdown = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("speciality")));

        // Scroll the dropdown and button elements into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", specialityDropdown);

        // Wait for dropdown options to be loaded and visible
        waiter.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("select[name='speciality'] option"), 0));

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

        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement email = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", email);
        executor.executeScript("arguments[0].value = arguments[1];", email, faker.internet().emailAddress());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_Doctors_nav() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/doctors']"))).click();*/
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/doctors");
    }

    @Override
    public void e_EditSpecialty() {
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
        e_AdminHomepage();
    }

    @Override
    public void e_AddNewSpecialtySuccess() {
        threads();
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement specialtyName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("specialtyName")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", specialtyName);
        executor.executeScript("arguments[0].value = arguments[1];", specialtyName, faker.medical().diseaseName());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_EditSpecialtyFailed() {
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement specialtyName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("specialtyName")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", specialtyName);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void v_ViewAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Appointment"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Appointment"));
    }

    @Override
    public void e_AdminViewProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "View Profile" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(1) a"))).click();
    }

    @Override
    public void v_EditDoctor() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Doctor"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Doctor"));
    }

    @Override
    public void v_NewAppointments() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - New Appointments"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - New Appointments"));
    }

    @Override
    public void e_AddNewSpecialty() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New"))).click();*/
        WebElement addButton = waiter.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New")));

        // Click on the "Add New" button using JavaScript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
    }

    @Override
    public void v_Doctors() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Doctors"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Doctors"));
    }

    @Override
    public void e_AssignAppointment() {
        threads();
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        for (WebElement link : allLinks) {
            if (link.getText().contains("Assign")) {
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

        System.out.println("No 'Assign' links found.");
        e_AdminHomepage();
    }

    @Override
    public void e_AdminEditProfile() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Logout" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(2) a"))).click();
    }

    @Override
    public void e_Customers() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/users");
    }

    @Override
    public void e_NewAppointments_nav() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/new_appointments']"))).click();*/
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/new_appointments");
    }

    @Override
    public void e_EditCustomerFailed() {
        threads();
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement address = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", address);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_NewAppointments() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/new_appointments");
    }

    @Override
    public void e_AddNewCustomerFailed() {
        fillUserData();

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_AddNewDoctor() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New"))).click();*/
        WebElement addButton = waiter.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New")));

        // Click on the "Add New" button using JavaScript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
    }

    @Override
    public void e_EditCustomer() {
        threads();
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
        e_AdminHomepage();
    }

    @Override
    public void v_AdminChangePassword() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Change Password"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Change Password"));
    }

    @Override
    public void e_AdminHomepage() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/admin_dashboard']"))).click();*/
        WebElement homeButton = waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/admin_dashboard']")));

        // Click on the "Add New" button using JavaScript Executor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeButton);
    }

    @Override
    public void e_DeleteDoctorSuccess() {
        threads();
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        for (WebElement link : allLinks) {
            if (link.getText().contains("Delete")) {
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

        System.out.println("No 'Delete' links found.");
        e_AdminHomepage();
    }

    @Override
    public void e_AddNewCustomer() {
        // Use JavaScript Executor to click the element
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", waiter.until(ExpectedConditions.elementToBeClickable(By.linkText("Add New"))));
    }

    @Override
    public void v_AppointmentsHistory() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Appointments History"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Appointments History"));
    }

    @Override
    public void v_AddNewCustomer() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Add New User"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Add New User"));
    }

    @Override
    public void e_EditCustomerSuccess() {
        threads();
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement address = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("address")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        executor.executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        executor.executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", address);
        executor.executeScript("arguments[0].value = arguments[1];", address, faker.address().fullAddress());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_ViewDoctor() {
        threads();
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
        e_AdminHomepage();
    }

    @Override
    public void e_AdminChangePassword() {
        WebElement userDropdown = waiter.until(ExpectedConditions.elementToBeClickable(By.id("user")));

        // Hover over the "User" dropdown
        Actions actions = new Actions(driver);
        actions.moveToElement(userDropdown).perform();

        // Wait for the "Change Password" link within the dropdown to be visible
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li#user ul li:nth-child(3) a"))).click();
    }

    @Override
    public void v_EditSpecialty() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Specialty"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Specialty"));
    }

    @Override
    public void e_AppointmentsHistory_nav() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/view_appointments']"))).click();*/
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/view_appointments");
    }

    @Override
    public void e_AddNewDoctorFailed() {
        fillUserData();

        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_DeleteCustomerSuccess() {
        threads();
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        for (WebElement link : allLinks) {
            if (link.getText().contains("Delete")) {
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

        System.out.println("No 'Delete' links found.");
        e_AdminHomepage();
    }

    @Override
    public void v_Specialty() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Specialties"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Specialties"));
    }

    @Override
    public void v_AdminViewProfile() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Profile"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Profile"));
    }

    @Override
    public void e_AdminEditProfileSuccess() {
        threads();
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement email = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", email);
        executor.executeScript("arguments[0].value = arguments[1];", email, faker.internet().emailAddress());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_AddNewCustomerSuccess() {
        fillUserData();

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
    }

    @Override
    public void e_AdminEditProfileFailed() {
        threads();
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
    public void e_ViewAppointment() {
        threads();
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
        e_AdminHomepage();
    }

    @Override
    public void v_AdminHomepage() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Dashboard"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Dashboard"));
    }

    @Override
    public void e_AddNewSpecialtyFailed() {
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement specialtyName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("specialtyName")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", specialtyName);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_EditSpecialtySuccess() {
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement specialtyName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("specialtyName")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", specialtyName);
        executor.executeScript("arguments[0].value = arguments[1];", specialtyName, faker.medical().diseaseName());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_AddNewDoctorSuccess() {
        fillUserData();

        // Wait for the speciality dropdown to be present
        WebElement specialityDropdown = waiter.until(ExpectedConditions.presenceOfElementLocated(By.name("speciality")));


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=\"submit\"]"))));

        // Click on the dropdown to open it
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

        // Click on the "Submit" button
        WebElement password = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", password);
        executor.executeScript("arguments[0].value = arguments[1];", password, faker.internet().password());
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_Specialty_nav() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/specialties']"))).click();*/
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/specialties");
    }

    @Override
    public void e_Specialty() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/specialties");
    }

    @Override
    public void v_AssignAppointment() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Assign Doctor"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Assign Doctor"));
    }

    @Override
    public void e_Doctors() {
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/doctors");
    }

    @Override
    public void v_ViewCustomer() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - View Customer"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - View Customer"));
    }

    @Override
    public void e_Customers_nav() {
        /*waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/users']"))).click();*/
        driver.get("http://localhost:8080/doctors_appointment_portal-1.0-SNAPSHOT/users");
    }

    @Override
    public void v_AddNewSpecialty() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Add New Specialty"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Add New Specialty"));
    }

    @Override
    public void e_EditDoctorFailed() {
        threads();
        WebElement button = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        WebElement lastName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        WebElement otherName = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("otherName")));
        WebElement email = waiter.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value = '';", lastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", lastName, faker.name().lastName());
        executor.executeScript("arguments[0].value = '';", otherName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", otherName, faker.name().username());
        executor.executeScript("arguments[0].value = '';", email);
        executor.executeScript("arguments[0].click();", button);
    }

    @Override
    public void e_EditDoctor() {
        threads();
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
        e_AdminHomepage();
    }

    @Override
    public void v_EditCustomer() {
        waiter.until(ExpectedConditions.titleContains("DPA Portal - Edit Customer"));
        String message = driver.getTitle();
        collector.checkThat(message, CoreMatchers.equalTo("DPA Portal - Edit Customer"));
    }

    /*@Override
    public void e_DeleteSpecialtySuccess() {
        // Wait for all links to be present
        List<WebElement> allLinks = waiter.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("a")));

        // Filter links containing "View" in their text
        List<WebElement> viewLinks = allLinks.stream()
                .filter(link -> link.getText().contains("Delete"))
                .collect(Collectors.toList());

        int numLinks = viewLinks.size();
        if (numLinks > 0) {
            // Generate a random index and click on the link
            int randomIndex = (int) (Math.random() * numLinks);
            WebElement randomViewLink = viewLinks.get(randomIndex);

            // Scroll the link into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomViewLink);

            // Wait for the link to be clickable and then click it
            waiter.until(ExpectedConditions.elementToBeClickable(randomViewLink)).click();
        } else {
            // Handle case when no matching links are found
            System.out.println("No 'Delete' links found.");
        }
    }*/

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

    public void threads(){
        try {
            Thread.sleep(1000); // Sleep for 1 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
