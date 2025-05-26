package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public class Wait {

    public static void waitForElementDisplayed(By locator){
        WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(), Duration.ofSeconds(60));
        wait.until(d -> d.findElement(locator).isDisplayed());
    }

    public static void waitForElementDisplayed(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(), Duration.ofSeconds(60));
        wait.until(d -> webElement.isDisplayed());
    }

    public static void waitForElementEnabled(By locator){
        WebDriverWait wait = new WebDriverWait(Driver.getAndroidDriver(), Duration.ofSeconds(60));
        wait.until(d -> d.findElement(locator).isEnabled());
    }

    public static <T> T retry(Supplier<T> operation, int maxRetries, long delayMillis) {
        int attempts = 0;
        while (true) {
            try {
                return operation.get();
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw e;
                }
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }
    }
}
