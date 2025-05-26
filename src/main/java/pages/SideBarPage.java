package pages;

import core.BasePage;
import core.Wait;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

public class SideBarPage extends BasePage {

    private final By liveTab = AppiumBy.accessibilityId("\uE90F Live");
    private final By sportsNavList = AppiumBy.xpath("//android.view.View[@resource-id='sidebarContent']//android.view.View[@resource-id='liveNavList']");

    public void OpenLiveTab(){
        Wait.waitForElementDisplayed(liveTab);
        findElement(liveTab).click();
    }

    public void OpenSport(String sport){
        Wait.waitForElementEnabled(sportsNavList);
        String sportXpath = "//android.view.View[contains(@content-desc,'%s')]/android.widget.TextView[1]";
        Wait.waitForElementEnabled(By.xpath(String.format(sportXpath, sport)));
        findElement(sportsNavList).findElement(By.xpath(String.format(sportXpath, sport))).click();
    }

    public Map<String, String> GetSports(Integer limit){
        Wait.waitForElementDisplayed(sportsNavList);
        var sports =
                findElement(sportsNavList).findElements(By.xpath("//android.view.View"));

        List<String> zz = new ArrayList<>();
        for (WebElement aa:sports) {
            var qq = aa.getAttribute("content-desc");
            zz.add(qq);
        }

        Map<String, String> sportNames = new HashMap<>();
        for (String match:zz) {
            if (match != null){
                String [] matches = match.split(" ");
                if (Integer.parseInt(matches[matches.length - 1]) > limit){
                    sportNames.put(Arrays.stream(matches).limit(matches.length-1).collect(Collectors.joining(" ")), matches[matches.length - 1]);
                }
            }
        }
        return sportNames;
    }
}
