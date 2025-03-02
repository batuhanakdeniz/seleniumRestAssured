package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage {
    private static String PAGE_URL = "https://useinsider.com/";
    private WebDriver driver;

    //Locators
    @FindBy(xpath = "//a[normalize-space(text())='Company' and @id='navbarDropdownMenuLink']")
    private WebElement companyHeader;

    @FindBy(css = "a.dropdown-sub[href=\"https://useinsider.com/careers/\"]")
    private WebElement careersOption;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
        By acceptCookiesButtonLocator = By.cssSelector("a[id=\"wt-cli-accept-all-btn\"]");
        if (driver.findElements(acceptCookiesButtonLocator).size() > 0) {
            driver.findElement(acceptCookiesButtonLocator).click();
        }
    }

    public void isHomePageCorrectlyOpened() {
        Assert.assertEquals(driver.getCurrentUrl(),PAGE_URL,"Home page is not opened current URL:" + driver.getCurrentUrl()+"\0"+ "PAGE_URL:"+PAGE_URL);
    }

    public void clickCompanyHeader() {
        companyHeader.click();
    }

    public CareersPage clickCareersOption() {
        careersOption.click();
        return new CareersPage(driver);
    }

}