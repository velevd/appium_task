package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasePage {

    protected WebElement findElement(By locator){
        return Driver.getAndroidDriver().findElement(locator);
    }

    protected List<WebElement> findElements(By locator){
        return Driver.getAndroidDriver().findElements(locator);
    }
}
