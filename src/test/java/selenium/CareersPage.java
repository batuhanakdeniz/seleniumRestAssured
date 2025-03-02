package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CareersPage {
    private static String PAGE_URL = "https://useinsider.com/careers/";
    private WebDriver driver;

    //Locators

    @FindBy(id = "career-our-location")
    private WebElement careerOurLocation;

    @FindBy(css = "[data-id=\"a8e7b90\"]")
    private WebElement lifeAtInsider;

    @FindBy(css = "[data-id=\"efd8002\"]")
    private WebElement ourStory;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }

    public boolean isCareersPageCorrectlyOpened() {
            return driver.getCurrentUrl().equals(PAGE_URL);
    }

    public boolean checkCareerOurLocation() {
        return careerOurLocation.isDisplayed();
    }

    public boolean checkLifeAtInsider() {
        return lifeAtInsider.isDisplayed();
    }

    public boolean checkOurStory() {
        return ourStory.isDisplayed();
    }

    public QualityAssurancePage goToOpenPositionsPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        return new QualityAssurancePage(driver);
    }


}