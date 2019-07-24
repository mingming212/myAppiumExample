package justTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

//import junit.framework.TestCase;
/*import org.junit.After;
import org.junit.Before;
import org.junit.Test;*/
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.touch.offset.PointOption;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
//import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import static org.hamcrest.*;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertEquals;

//import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by helena.liu on 2019/1/28.
 */
public class mainTest {

        private AndroidDriver driver;

        @BeforeClass
        public void setUp() throws MalformedURLException {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("deviceName", "emulator-5554");
//            desiredCapabilities.setCapability("appPackage", "com.ijourney.conbow");
//            desiredCapabilities.setCapability("appActivity", "com.ijourney.conbow.MainActivity");
//            desiredCapabilities.setCapability("app","/Users/helena.liu/Downloads/robothousekeeper_v1.2.0.2041_mtest_201901141453.apk");
            desiredCapabilities.setCapability("appPackage", "com.xueqiu.android");
            desiredCapabilities.setCapability("appActivity", "com.xueqiu.android.view.WelcomeActivityAlias");
//            desiredCapabilities.setCapability("app","/Users/helena.liu/Downloads/com.xueqiu.android_11.15_200.apk");
            desiredCapabilities.setCapability("platformName", "android");
            desiredCapabilities.setCapability("noReset","true");
//            desiredCapabilities.setCapability("fullReset","true");
            desiredCapabilities.setCapability("autoGrantPermissions","true");


            URL remoteUrl = new URL("http://localhost:4723/wd/hub");
            driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        }

        @Test
        public void sampleTest() throws IOException {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MobileElement el2 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.EditText\n");
            el2.sendKeys("18001367612");
            FileUtil.copyFile(driver.getScreenshotAs(OutputType.FILE),new File("1.png"));
            System.out.println("-------"+driver.getPageSource());

        }

        @Test
        public void testTouchAction() throws InterruptedException {
            Thread.sleep(5000);
            TouchAction action=new TouchAction(driver);
            action.press(PointOption.point(561, 1533)).moveTo(PointOption.point(561, 314)).release().perform();
//            action.release();
//            action.perform();
        }

        @Test
        public void testXueqiu() throws InterruptedException {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElementByXPath("//*[@text='行情' and @resource-id='com.xueqiu.android:id/tab_name']").click();
            driver.findElementByXPath("//*[@text='板块' and @instance=3]").click();
            driver.findElementByXPath("//*[@resource-id='com.xueqiu.android:id/row_1_column_1' and @instance=14]").click();//行业板块中的第一个
            TouchAction action =new TouchAction(driver);
//            Thread.sleep(5000);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
int i=0;
            while(true){
                System.out.println(""+i++);

                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                System.out.println(""+(int)(width*1/2)+","+(int)(height*3/4)+"  "+(int)(width*1/2)+","+(int)(height*1/4));
                String beforPage=driver.getPageSource();
//                action.press(PointOption.point((int)(width*1/2),(int)(height*3/4))).moveTo(PointOption.point((int)(width*1/2),(int)(height*1/4))).release().perform();
                action.press(PointOption.point(540,1365)).moveTo(PointOption.point(540,455)).release().perform();
                try{
                    String afterPage=driver.getPageSource();
                    if(beforPage.equals(afterPage)) {
                        driver.findElementByXPath("//*[@resource-id='com.xueqiu.android:id/table_item_first_column_text1' and @text='西王食品']");
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            driver.findElementByXPath("(//*[@resource-id='com.xueqiu.android:id/table_item_first_column_text1'])[last()]").click();
            Thread.sleep(3000);

//            assertEquals("*ST康达",driver.findElementById("com.xueqiu.android:id/action_bar_stock_name").getText());
//            assertEquals("19.05",driver.findElementById("com.xueqiu.android:id/stock_current_price").getText());
//            assertThat(1,equalTo(2));


        }

        @AfterClass
        public void tearDown() {
            driver.quit();
        }

}
