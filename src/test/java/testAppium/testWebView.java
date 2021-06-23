package testAppium;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Set;

/**本例测试页面：conbow APP登录页面上的"服务协议"，是个H5页面
 * 本例可以运行成功，但是需要 java-client 版本为：5.0.3，别的版本（如7.0.0，6.1.0）会报错： known package com.android.chrome does not accept activity/process
 * 如果用5.0.3版本，影响的方法有：action.press()参数需是两个数字
 */
public class testWebView {
    AndroidDriver driver;

    @BeforeClass
    public void setUp() throws Exception{
        DesiredCapabilities capabilities=new DesiredCapabilities();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("androidProcess", "com.ijourney.conbow");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability("chromedriverExecutableDir","/Users/helena.liu/Desktop/chromedriver");//需要Appium 1.8.0以上才支持
        capabilities.setCapability("deviceName","123");
        capabilities.setCapability("appPackage","com.ijourney.conbow");
        capabilities.setCapability("appActivity","com.ijourney.conbow.MainActivity");
        capabilities.setCapability("noReset","true");//不停止程序，不清数据，不卸载包
        capabilities.setCapability("platformName","android");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(remoteUrl, capabilities);
    }

    @Test
    public void testmain() throws InterruptedException {
        Thread.sleep(5000);

        TouchAction action=new TouchAction(driver);
        //点击登录页上的"服务协议"链接，跳转后的页面是个H5页
//        action.press(PointOption.point(700,1010)).release().perform();//小华为H60
//        action.press(PointOption.point(680,1058)).release().perform();//三星手机
        action.press(PointOption.point(680,1020)).release().perform();//华为MT7手机

//        action.press(680,1020).release().perform();//华为MT7手机
//        action.press(700,1010).release().perform();//小华为H60
//        action.press(550,978).release().perform();//模拟器1080X1920
        Thread.sleep(5000);

        //查找元素
        //方式一：不切换context，可以通过AccessibilityId查找，如果有AccessibilityId的话
        WebElement e3= driver.findElementByAccessibilityId("慷宝管家机器人软件许可使用协议");
        System.out.println("333:"+e3.toString());
        WebElement e= driver.findElementByXPath("//android.view.View[@content-desc='慷宝管家机器人软件许可使用协议']");

        //方式二：切换context，用css查找
        Set contexts = driver.getContextHandles();
        for(Object context : contexts){
            System.out.println("可用context： "+context);
            if(context.toString().equals("WEBVIEW_com.ijourney.conbow")){
                System.out.println("---现在去切换context");
                driver.context("WEBVIEW_com.ijourney.conbow");
                System.out.println("---切换context成功！！！！");
                System.out.println("现在使用的context：--------"+driver.getContext());
                System.out.println("pagesource是html：  "+driver.getPageSource());
            }
        }
        WebElement e2= driver.findElementByCssSelector("div");
        System.out.println("222:"+e2.getAttribute("id"));
        WebElement e4= driver.findElementByCssSelector("#main > div.header > h1");
        System.out.println("444:"+e4.getText());
        WebElement e5= driver.findElementByCssSelector("div.header > h1");
        System.out.println("555:"+e5.getText());
        WebElement e6= driver.findElementByCssSelector("div.header:first-child");
        System.out.println("666:"+e6.getText());
        Thread.sleep(2000);

        driver.context("NATIVE_APP");
        Thread.sleep(3000);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
