package demo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
    }

    public void endTest() {
        System.out.println("End Tests: TestCases");
        driver.quit();
    }

    /** <STRONG>Test Case 01</STRONG>: Automate and fill a google form <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to google form link <p>
     *  3. Fill in your name in the first text box <p>
     *  4. Write down 'I want to be the best QA Engineer! <epochTime>' <p>
     *  5. Enter your Automation Testing experience in the next radio button <p>
     *  6. Select Java, Selenium and TestNG from the next check-box <p>
     *  7. Provide how you would like to be addressed in the next dropdown <p>
     *  8. Provided the current date minus 7 days in the next date field, it should be dynamically calculated and not hardcoded. <p>
     *  9. Provide the current time (Keeping in mind AM/PM) in the next field <p>
     *  10. Change the URL of the tab (amazon.in) and you will find the pop up as follows. Click on cancel. <p>
     *  11. Submit the form <p>
     *  12. You will see a success message on the website. Print the same message on the console upon successful completion <p>
     * @throws InterruptedException
     */
    public void testCase01() throws InterruptedException {
        System.out.println("TestCase01: START");

        // Launch chrome browser
        // Navigate to google form link
        String googleFormLink = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        driver.get(googleFormLink);
        Thread.sleep(2000);

        // Fill in your name in the first text box
        WebElement nameTextBox = driver.findElement(By.xpath("//span[contains(text(), 'Name')]/ancestor::div[contains(@role, 'listitem')]//input"));
        nameTextBox.sendKeys("Devendra Indapawar");

        // Write down 'I want to be the best QA Engineer! <epochTime>'
        long epoch = System.currentTimeMillis()/1000;
        WebElement practicingAutomationTextBox = driver.findElement(By.xpath("//span[contains(text(), 'practicing Automation')]/ancestor::div[contains(@role, 'listitem')]//textarea"));
        practicingAutomationTextBox.sendKeys("I want to be the best QA Engineer! " + epoch);

        // Enter your Automation Testing experience in the next radio button
        String automationTestingExperience = "0 - 2"; // 0 - 2, 3 - 5, 6 - 10, > 10
        WebElement experienceRadioButton = driver.findElement(By.xpath("(//span[contains(text(), '"+ automationTestingExperience +"')]/../../../div)[1]"));
        experienceRadioButton.click();

        // Select Java, Selenium and TestNG from the next check-box
        List<String> optionsToSelect = Arrays.asList("Java", "Selenium", "TestNG");
        for (String option : optionsToSelect) {
            driver.findElement(By.xpath("(//span[contains(text(), 'learned in Crio.Do')]/../../../..//span[contains(text(), '"+ option +"')]/../../../div)[1]")).click();
        }

        // Provide how you would like to be addressed in the next dropdown
        WebElement addressedDropdown = driver.findElement(By.xpath("//span[contains(text(), 'Choose')]"));
        addressedDropdown.click();
        Thread.sleep(1000);

        WebElement addressedDropdownToSelect = driver.findElement(By.xpath("//div[@role='option']//span[text()='Mr']/.."));
        addressedDropdownToSelect.click();

        // Provided the current date minus 7 days in the next date field, it should be dynamically calculated and not hardcoded.
        String formattedSevenDaysAgo = LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        WebElement calendar = driver.findElement(By.xpath("//input[contains(@type, 'date')]"));
        calendar.sendKeys(formattedSevenDaysAgo);

        // Provide the current time (Keeping in mind AM/PM) in the next field
        LocalTime currentTime = LocalTime.now();
        String hourTime = currentTime.format(java.time.format.DateTimeFormatter.ofPattern("HH"));
        String minuteTime = currentTime.format(java.time.format.DateTimeFormatter.ofPattern("mm"));
        driver.findElement(By.xpath("//input[contains(@aria-label, 'Hour')]")).sendKeys(hourTime);
        driver.findElement(By.xpath("//input[contains(@aria-label, 'Minute')]")).sendKeys(minuteTime);

        // Change the URL of the tab (amazon.in) and you will find the pop up as follows. Click on cancel.
        driver.navigate().to("https://www.amazon.com");
        driver.switchTo().alert().dismiss();

        // Submit the form
        driver.findElement(By.xpath("//span[contains(text(), 'Submit')]")).click();

        // You will see a success message on the website. Print the same message on the console upon successful completion
        WebElement successMessage = driver.findElement(By.xpath("(//div[contains(@role, 'heading')]/following-sibling::div)[1]"));
        System.out.println(successMessage.getText());

        System.out.println("TestCase01: END");
    }
}
