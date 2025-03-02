package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QualityAssurancePage {
    private static String PAGE_URL = "https://useinsider.com/careers/quality-assurance/";
    private WebDriver driver;

    //Locators

    @FindBy(css = "a[href='https://useinsider.com/careers/open-positions/?department=qualityassurance']")
    private WebElement selectAllQAJobs;

    public QualityAssurancePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }

    public boolean isQualityAssurancePageCorrectlyOpened() {
        return driver.getCurrentUrl().equals(PAGE_URL);
    }

    public OpenPositionsPage clickSelectAllQAJobs() {
        selectAllQAJobs.click();
        return new OpenPositionsPage(driver);
    }


/*
    public void waitUntilAcceptAllForCookieIsDisplayed() {
        AcceptAllForCookie.click();
    }

    public void clickAcceptAllForCookie() {
        AcceptAllForCookie.click();
    }

    public String getPositionToTime(){
        Actions actions = new Actions(driver);
        actions.moveToElement(bitcoinPriceTable,50,50).perform();
        String date = bitcoinPriceTableTooltipDate.getText();
        String hour = bitcoinPriceTableTooltipHour.getText();
        System.out.println(date);
        return date;
    }

    public void clickToOneDayTable(){
        bitcoinPriceTableOneDay.click();
    }

    public void setCurrency(String currencyText){
        currency.click();
        currencySearchInput.sendKeys(currencyText);
        Assert.assertTrue(TRYcurrency.isDisplayed());
        // currencyText genişletilebilir ihtiyaç olmadığı için sadece TRY ile gönderdim.
        TRYcurrency.click();
    }

    public void isTableDisplayed(){
        Assert.assertTrue(bitcoinPriceTable.isDisplayed());
    }

    public String getTextTableCurrencyFormat(){
        return TableCurrencyFormat.getText();
    }
*/

}