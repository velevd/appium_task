package pages;

import core.BasePage;
import core.Wait;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By carouselItem = AppiumBy.accessibilityId("Carousel item");
    private final By menu = AppiumBy.accessibilityId("\uF19C");
    private final By notificationsPopUp = AppiumBy.id("com.android.permissioncontroller:id/permission_deny_button");

    public void OpenSideBar(){
        DenyNotifications();
        Wait.waitForElementDisplayed(carouselItem);
        findElement(menu).click();
    }

    private void DenyNotifications(){
        Wait.waitForElementDisplayed(notificationsPopUp);
        findElement(notificationsPopUp).click();
    }
}
