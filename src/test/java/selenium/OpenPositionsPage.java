package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class OpenPositionsPage {
    private static String PAGE_URL = "https://useinsider.com/careers/open-positions/?department=qualityassurance";
    private WebDriver driver;

    String jobDepartment = "Quality Assurance";
    String jobLocation = "Istanbul, Turkiye";
    //Locators
// + means next sibling
    @FindBy(css = "#filter-by-location + span span[role=\"combobox\"]")
    private WebElement filterByLocation;
//  *= means contains
    @FindBy(css = "li[data-select2-id*=\"Istanbul, Turkiye\"]")
    private WebElement listIstanbul;

    @FindBy(css = "#filter-by-department + span span[role=\"combobox\"]")
    private WebElement filterByDepartment;

    @FindBy(id = "jobs-list")
    private WebElement jobList;

    public OpenPositionsPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }
    public void isCareersPageCorrectlyOpened() {
        Assert.assertEquals(driver.getCurrentUrl(),PAGE_URL);
        checkVisibilityOfQualityAssuranceDepartment();
    }

    public void checkVisibilityOfQualityAssuranceDepartment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='select2-filter-by-department-container' and text() = 'Quality Assurance']")));
    }

    public void clickFilterByLocation() {
        filterByLocation.click();
    }

    public void clickListIstanbul() {
        Assert.assertTrue(listIstanbul.isDisplayed());
        listIstanbul.click();
    }

    public void clickFilterByDepartment() {
        filterByDepartment.click();
    }

    public void checkJobList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(jobList));
    }

    public void checkQualityAssuranceJobsList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(jobList));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", jobList);

        List<WebElement> qualityAssuranceJobsList = driver.findElements(By.cssSelector("div.position-list-item"));
        Assert.assertTrue(qualityAssuranceJobsList.size() > 0);

        for(int i = 0; i < qualityAssuranceJobsList.size(); i++) {
            WebElement jobItem = driver.findElements(By.xpath("//div[contains(@class,'position-list')]/div[contains(@class,'position-list-item-wrapper')]")).get(i);
            String jobDepartmentText = jobItem.findElement(By.cssSelector("span.position-department")).getText();
            String jobLocationText =jobItem.findElement(By.cssSelector("div.position-location")).getText();
            Assert.assertEquals(jobDepartmentText, jobDepartment);
            Assert.assertEquals(jobLocationText, jobLocation);
        }

    }

    public void clickViewRoleButton() {
        //Click on the first job
        WebElement selectedJob = driver.findElements(By.cssSelector("[data-team=\"qualityassurance\"]")).get(0);
        WebElement viewRoleButton = driver.findElement(By.cssSelector("a[class*=\"btn\"]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(viewRoleButton).perform();
        actions.click(viewRoleButton).perform();

    }
}