package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.Objects;

public class UserRegistrationDefinition {
    WebDriver driver;
    WebDriverWait wait;

    @Given("I open the registration page")
    public void i_open_the_registration_page() {
        driver = new ChromeDriver();
        setupWait();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @Given("I open the registration page in {string}")
    public void i_open_the_registration_page_in_browser(String browser) {
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();
        }
        setupWait();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I enter {string} into the date of birth field")
    public void enter_dob_manually(String dobString) {
        WebElement dobField = driver.findElement(By.id("dp"));
        dobField.click();
        dobField.clear();
        dobField.sendKeys(dobString);
    }

    @Then("The DOB field should contain {string}")
    public void verify_dob_field_value(String expectedDob) {
        WebElement dobField = driver.findElement(By.id("dp"));
        String actualDob = Objects.requireNonNull(dobField.getDomProperty("value")).trim();
        Assert.assertEquals(expectedDob, actualDob);
    }


    private void setupWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @When("I enter correct user details")
    public void enter_valid_user_details() {
        driver.findElement(By.id("member_firstname")).sendKeys("Nifemi");
        driver.findElement(By.id("member_lastname")).sendKeys("Adebayo");
        driver.findElement(By.id("member_emailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Test@1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Test@1234");
    }

    @When("I enter details with missing last name")
    public void enter_details_missing_last_name() {
        driver.findElement(By.id("member_firstname")).sendKeys("Nifemi");
        driver.findElement(By.id("member_lastname")).sendKeys("");
        driver.findElement(By.id("member_emailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Test@1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Test@1234");
    }

    @When("I enter mismatched passwords")
    public void enter_mismatched_passwords() {
        driver.findElement(By.id("member_firstname")).sendKeys("Nifemi");
        driver.findElement(By.id("member_lastname")).sendKeys("Adebayo");
        driver.findElement(By.id("member_emailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("Nifemi@test.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Test@1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Missmatched@1234");
    }

    @When("I accept the account confirmation terms and conditions")
    public void accept_account_terms_and_conditions() {
        WebElement checkbox = driver.findElement(By.cssSelector("label[for='sign_up_25']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    @When("I accept the code of ethics terms and conditions")
    public void accept__ethics_terms_and_conditions() {
        WebElement checkbox = driver.findElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    @And("I accept age responsibility terms and conditions")
    public void accept__age_terms_and_conditions() {
        WebElement checkbox = driver.findElement(By.cssSelector("label[for='sign_up_26'] span[class='box']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    @When("I do not accept the terms and conditions")
    public void do_not_accept_terms_and_conditions() {
        WebElement checkbox = driver.findElement(By.cssSelector("label[for='sign_up_25']"));
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }

    @When("I submit the form")
    public void submit_form() {
        driver.findElement(By.cssSelector("input[value='CONFIRM AND JOIN']")).click();
    }

    @Then("The user should be registered successfully")
    public void verify_successful_registration() {
        waitForElement(By.className("confirmation-message"));
        Assert.assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Thank you"));
        driver.quit();
    }

    @Then("An error message for last name should be displayed")
    public void verify_last_name_error() {
        waitForElement(By.cssSelector(".field-validation-error.warning[data-valmsg-for='Surname']"));
        String error = driver.findElement(By.cssSelector("span[for='member_lastname']")).getText();
        Assert.assertTrue(error.contains("Last Name is required"));
        driver.quit();
    }

    @Then("An error message for password mismatch should be displayed")
    public void verify_password_mismatch_error() {
        waitForElement(By.cssSelector("span[for='signupunlicenced_confirmpassword']"));
        String error = driver.findElement(By.cssSelector("span[for='signupunlicenced_confirmpassword']")).getText();
        Assert.assertTrue(error.contains("Password did not match"));
        driver.quit();
    }

    @Then("An error message for terms and conditions should be displayed")
    public void verify_terms_error() {
        waitForElement(By.cssSelector(" span[for='TermsAccept']"));
        String error = driver.findElement(By.cssSelector("span[for='TermsAccept']")).getText();
        Assert.assertTrue(error.contains("You must confirm that you have read and accepted our Terms and Conditions"));
        driver.quit();
    }
}

