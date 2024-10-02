package demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
    }

    public static void logStatus(String testStep, String testMessage) {
        System.out.println(String.format("%s | %s | %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), testStep, testMessage));
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
     */
    public void testCase01() {
        System.out.println("TestCase01: START");

        // Launch chrome browser
        // Navigate to google form link
        String googleFormLink = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
        driver.get(googleFormLink);

        // Fill in your name in the first text box
        customSendKeys(driver, By.xpath("//span[contains(text(), 'Name')]/ancestor::div[contains(@role, 'listitem')]//input"), "Devendra Indapawar");

        // Write down 'I want to be the best QA Engineer! <epochTime>'
        customSendKeys(driver, By.xpath("//span[contains(text(), 'practicing Automation')]/ancestor::div[contains(@role, 'listitem')]//textarea"), "I want to be the best QA Engineer! " + getEpochTime());

        // Enter your Automation Testing experience in the next radio button
        String experience = "0 - 2"; // 0 - 2, 3 - 5, 6 - 10, > 10
        selectOptionRadioButton(driver, experience);

        // Select Java, Selenium and TestNG from the next check-box
        List<String> optionsToSelect = Arrays.asList("Java", "Selenium", "TestNG");
        selectOptionsCheckBox(driver, optionsToSelect);

        // Provide how you would like to be addressed in the next dropdown
        String title = "Mr";
        selectOptionDropdown(driver, title);

        // Provided the current date minus 7 days in the next date field, it should be dynamically calculated and not hardcoded.
        customSendKeys(driver, By.xpath("//input[contains(@type, 'date')]"), getDateTime("dd-MM-yyy", "minusDays", 7));

        // Provide the current time (Keeping in mind AM/PM) in the next field
        customSendKeys(driver, By.xpath("//input[contains(@aria-label, 'Hour')]"), getDateTime("HH", "present", 0));
        customSendKeys(driver, By.xpath("//input[contains(@aria-label, 'Minute')]"), getDateTime("mm", "present", 0));

        // Change the URL of the tab (amazon.in) and you will find the pop up as follows. Click on cancel.
        driver.navigate().to("https://www.amazon.com");
        alertAction(driver, "dismiss");

        // Submit the form
        clickOnButton(driver, By.xpath("//span[contains(text(), 'Submit')]"), "Submit");

        // You will see a success message on the website. Print the same message on the console upon successful completion
        getTextMessage(driver, By.xpath("(//div[contains(@role, 'heading')]/following-sibling::div)[1]"), "Success message");

        System.out.println("TestCase01: END");
    }

    /**
     * Sends the specified text to a web element identified by the given selector using WebDriver.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param selector          The selector used to locate the web element.
     * @param text              The text to be sent to the web element.
     */
    private static void customSendKeys(WebDriver driver, By selector, String text) {
        logStatus("sendKeys", text);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
            WebElement element = driver.findElement(selector);
            element.clear();
            element.sendKeys(text);
            logStatus("sendKeys: SUCCESS", text);
        } catch (Exception e) {
            logStatus("sendKeys: FAILURE", text +"\n"+ e.getMessage());
        }
    }

    /**
     * Get the current epoch time in seconds.
     *
     * @return                  The current epoch time in seconds.
     */
    private static long getEpochTime() {
        long epoch = System.currentTimeMillis()/1000;
        return epoch;
    }

    /**
     * Get the formatted date and time based on the given pattern and offset option.
     *
     * @param formatPattern     The pattern for formatting the date and time ("dd-MM-yyyy", "HH", "mm").
     * @param option            The option for offset calculation (e.g.: "minusDays", "plusDays").
     * @param offSetNumber      The number to offset the current date and time.
     * @return                  The formatted date and time string.
     */
    private static String getDateTime(String formatPattern, String option, long offSetNumber) {
        LocalDateTime now = LocalDateTime.now();
        switch (option) {
            case "minusDays":
                return now.minusDays(offSetNumber).format(DateTimeFormatter.ofPattern(formatPattern));
            default:
                return now.format(DateTimeFormatter.ofPattern(formatPattern));
        }
    }

    /**
     * Selects the radio button option corresponding to the given experience range.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param experience        A String representing the experience range to be selected. Possible values include "0 - 2", "3 - 5", "6 - 10", or "> 10".
    */
    private static void selectOptionRadioButton(WebDriver driver, String experience) {
        logStatus("selectOptionRadioButton", experience);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(text(), '"+ experience +"')]/../../../div)[1]")));
            WebElement element = driver.findElement(By.xpath("(//span[contains(text(), '"+ experience +"')]/../../../div)[1]"));
            element.click();
            logStatus("selectOptionRadioButton: SUCCESS", experience);
        } catch (Exception e) {
            logStatus("selectOptionRadioButton: FAILURE", experience +"\n"+ e.getMessage());
        }
    }

    /**
     * Selects the specified options in a checkbox list.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param optionsToSelect   A list of strings representing the options to be selected. Each string should correspond to the text content of an option.
     */
    private static void selectOptionsCheckBox(WebDriver driver, List<String> optionsToSelect) {
        logStatus("selectOptionsCheckBox", optionsToSelect.toString());
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            for (String option : optionsToSelect) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[contains(text(), 'learned in Crio.Do')]/../../../..//span[contains(text(), '"+ option +"')]/../../../div)[1]")));
                driver.findElement(By.xpath("(//span[contains(text(), 'learned in Crio.Do')]/../../../..//span[contains(text(), '"+ option +"')]/../../../div)[1]")).click();
            }
            logStatus("selectOptionsCheckBox: SUCCESS", optionsToSelect.toString());
        } catch (Exception e) {
            logStatus("selectOptionsCheckBox: FAILURE", optionsToSelect.toString() +"\n"+ e.getMessage());
        }
    }

    /**
     * Selects an option from a dropdown menu.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param title             The title of the option to select from the dropdown menu.
     */
    private static void selectOptionDropdown(WebDriver driver, String title) {
        logStatus("selectOptionDropdown", title);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Choose')]")));
            driver.findElement(By.xpath("//span[contains(text(), 'Choose')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='option']//span[text()='"+ title +"']/..")));
            driver.findElement(By.xpath("//div[@role='option']//span[text()='"+ title +"']/..")).click();
            logStatus("selectOptionDropdown: SUCCESS", title);
        } catch (Exception e) {
            logStatus("selectOptionDropdown: FAILURE", title +"\n"+ e.getMessage());
        }
    }

    /**
     * Performs an action on an alert dialog, such as accepting or dismissing it.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param action            The action to perform on the alert dialog. Supported actions are "accept" to accept the alert and "dismiss" to dismiss it.
     */
    private static void alertAction(WebDriver driver, String action) {
        logStatus("alertAction", action);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            switch (action) {
                case "accept":
                    alert.accept();
                default:
                    alert.dismiss();
            }
            logStatus("alertAction: SUCCESS", action);
        } catch (Exception e) {
            logStatus("alertAction: FAILURE", action +"\n"+ e.getMessage());
        }
    }

    /**
     * Clicks on a button identified by the given selector.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param selector          The selector used to locate the button element.
     * @param type              The type or purpose of the button action, for logging purposes.
     */
    private static void clickOnButton(WebDriver driver, By selector, String type) {
        logStatus("clickOnButton", type);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
            driver.findElement(selector).click();
            logStatus("clickOnButton: SUCCESS", type);
        } catch (Exception e) {
            logStatus("clickOnButton: FAILURE", type +"\n"+ e.getMessage());
        }
    }

    /**
     * Retrieves the text message from a web element identified by the given selector.
     *
     * @param driver            The instance of WebDriver used to interact with the web browser.
     * @param selector          The selector used to locate the button element.
     * @param message           The description or identifier of the text message, for logging purposes.
     */
    private static void getTextMessage(WebDriver driver, By selector, String message) {
        logStatus("getTextMessage", message);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
            WebElement successMessage = driver.findElement(selector);
            logStatus("getTextMessage: SUCCESS", message +": "+ successMessage.getText());
        } catch (Exception e) {
            logStatus("getTextMessage: FAILURE", message +"\n"+ e.getMessage());
        }
    }
}
