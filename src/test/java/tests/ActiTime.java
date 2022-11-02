package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import services.HRMAssignment;
import services.Prop;
import services.SpreadsheetReader;

public class ActiTime extends HRMAssignment {

    @DataProvider()
    public Object[][] singleThread() {
        SpreadsheetReader spreadsheetReader = new SpreadsheetReader();
        return spreadsheetReader.getData("src/test/resources/userlogins.xlsx", "new_account");
    }

    @Test(dataProvider = "singleThread")
    public void actitime_login(String USERNAME, String PASSWORD) {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.navigate_to_login(Prop.elements().getProperty("ACTITIME"));
        loginPage.login_to_actitime(USERNAME, PASSWORD, true);
        homePage.verify_logout_link_is_visible();
    }

}
