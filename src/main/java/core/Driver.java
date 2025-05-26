package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Driver {
    private static AndroidDriver androidDriver;
    private static AppiumDriverLocalService appiumDriverLocalService;

    public static void startDriverAndService(){
        appiumDriverLocalService = new AppiumServiceBuilder().usingAnyFreePort().build();
        appiumDriverLocalService.start();
        androidDriver = new AndroidDriver(appiumDriverLocalService, setOptions());
    }

    private static UiAutomator2Options setOptions(){
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAvd("");
        options.setAvdArgs("-no-boot-anim -no-snapshot");
        options.fullReset();
        options.setApp("src/main/resources/sportempire.apk");
        return options;
    }

    public static void closeDriverAndService(){
        androidDriver.quit();
        appiumDriverLocalService.stop();
    }

    public static AndroidDriver getAndroidDriver(){
        return androidDriver;
    }
}
