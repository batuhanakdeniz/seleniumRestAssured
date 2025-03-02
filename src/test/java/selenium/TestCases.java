package selenium;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestCases {

    private static WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private QualityAssurancePage qaPage;
    private OpenPositionsPage openPositionsPage;

    @BeforeClass
    public static void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test
    public void test() {
        //1. Visit https://useinsider.com/ and check Insider home page is opened or not
        homePage = new HomePage(driver);
        homePage.isHomePageCorrectlyOpened();

        //2. Select the “Company” menu in the navigation bar, select “Careers” and check Career
        //page, its Locations, Teams, and Life at Insider blocks are open or not
        homePage.clickCompanyHeader();
        careersPage = homePage.clickCareersOption();
        Assert.assertTrue(careersPage.isCareersPageCorrectlyOpened());
        Assert.assertTrue(careersPage.checkCareerOurLocation());
        Assert.assertTrue(careersPage.checkOurStory());
        Assert.assertTrue(careersPage.checkLifeAtInsider());

        //3. Go to https://useinsider.com/careers/quality-assurance/, click “See all QA jobs”,
        //filter jobs by Location: “Istanbul, Turkey”, and Department: “Quality Assurance”, check the presence of the job list
        qaPage = careersPage.goToOpenPositionsPage();
        Assert.assertTrue(qaPage.isQualityAssurancePageCorrectlyOpened());
        openPositionsPage = qaPage.clickSelectAllQAJobs();
        openPositionsPage.isCareersPageCorrectlyOpened();

        openPositionsPage.clickFilterByLocation();
        openPositionsPage.clickListIstanbul();
        openPositionsPage.checkVisibilityOfQualityAssuranceDepartment();
        openPositionsPage.checkJobList();


        //4. Check that all jobs’ Position contains “Quality Assurance”, Department contains
        //“Quality Assurance”, and Location contains “Istanbul, Turkey”
        openPositionsPage.checkQualityAssuranceJobsList();

        //5. Click the “View Role” button and check that this action redirects us to the Lever Application form page
        openPositionsPage.clickViewRoleButton();

    }

    @AfterMethod
    public void onFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot scrShot = ((TakesScreenshot)driver);
            File srcFile =  scrShot.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = "Error_" + timestamp + ".png";

            File destFile = new File("screenshots/" + screenshotName);

            try {
                FileUtils.copyFile(srcFile, destFile);
                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @AfterClass
    public static void tearDown() {

        DriverFactory.quitDriver();
    }

}
