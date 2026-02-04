package utils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SSonFailure implements TestWatcher {

    private final WebDriver driver;

    public SSonFailure(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String testName = context.getDisplayName()
                    .replace("()", "");

            Path targetPath = Path.of(
                    "screenshots",
                    testName + "_" + timestamp + ".png"
            );

            Files.createDirectories(targetPath.getParent());
            Files.copy(screenshot.toPath(), targetPath);

            System.out.println("📸 Screenshot saved: " + targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
