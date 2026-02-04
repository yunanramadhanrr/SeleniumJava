package utils;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

public class ScreenshotWatcher implements TestWatcher {

    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotWatcher(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    private void capture(String testName, String status) {
        try {
            WebDriver driver = driverSupplier.get();
            if (driver == null) {
                System.out.println("❌ Driver NULL, screenshot skipped");
                return;
            }

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            Path dest = Paths.get(
                    "screenshots",
                    status,
                    testName.replace("()", "") + "_" + timestamp + ".png"
            );

            Files.createDirectories(dest.getParent());
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📸 Screenshot saved -> " + dest.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        capture(context.getDisplayName(), "success");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        capture(context.getDisplayName(), "failed");
    }
}
