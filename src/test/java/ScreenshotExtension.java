package utils;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotExtension(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

        WebDriver driver = driverSupplier.get();
        if (driver == null) return;

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        boolean failed =
                context.getExecutionException().isPresent();

        String status = failed ? "FAILED" : "SUCCESS";

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());

        String testName =
                context.getRequiredTestMethod().getName();

        File dest = new File(
                "screenshots/" + status + "/" +
                        testName + "_" + timestamp + ".png"
        );

        dest.getParentFile().mkdirs();
        FileHandler.copy(src, dest);

        System.out.println(
                "📸 Screenshot saved -> " +
                        dest.getAbsolutePath()
        );
    }
}
