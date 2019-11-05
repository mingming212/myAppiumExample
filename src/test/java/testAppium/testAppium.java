package testAppium;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by whs on 2019/3/11.
 */
public class testAppium {
    static AndroidDriver driver;

    @BeforeAll
    public static void setUp() throws IOException, InterruptedException {
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability("platform","android");
        capabilities.setCapability("deviceName","P4M7N15425007120");
        //雪球APP
        capabilities.setCapability("appPackage","com.xueqiu.android");
        capabilities.setCapability("appActivity","com.xueqiu.android.view.WelcomeActivityAlias");
        //api demo apk
//        capabilities.setCapability("appPackage","io.appium.android.apis");
//        capabilities.setCapability("appActivity",".ApiDemos");
        capabilities.setCapability("automationName","uiautomator2");
        capabilities.setCapability("noReset","true");
        URL remoteUrl = new URL("http://localhost:4723/wd/hub");
        driver=new AndroidDriver(remoteUrl,capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.out.println("是否黑屏："+isScreenLock() );
        if(isScreenLock()){//如果黑屏，去唤醒屏幕
            Runtime.getRuntime().exec("adb shell input keyevent 26");//模拟power键
            System.out.println("已唤醒屏幕" );
        }
//        System.out.println("-----"+driver.getPageSource());
//        System.out.println("---------"+driver.getPageSource().contains("com.android.keyguard:id/pinEntry"));
        if(driver.getPageSource().contains("com.android.keyguard:id/pinEntry")){//当前页面是唤醒后的背景图页
//        if(driver.getPageSource().contains("com.android.keyguard:id/magazinelockscreen")){//当前页面是唤醒后的背景图页
            System.out.println("当前activity： "+driver.currentActivity());
            unlock_secret();//输入密码解锁
//            unlock_gesture();//手势密码解锁
        }
    }

    //判断是否锁屏
    public static boolean isScreenLock() throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process=runtime.exec("adb shell dumpsys power |grep 'Display Power'");
        BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
        Boolean flag=false;
        String line;
        String content = "";
        while((line=br.readLine())!=null){
            content=content+line;
        }
        if(content.contains("Display Power: state=OFF")){
            flag=true;
        }
        process.destroy();
        return flag;
    }

//    @Test
    //密码解锁
    public static void unlock_secret() throws IOException {
            System.out.println("屏幕已唤醒，现在去输入密码解锁" );
            TouchAction touch = new TouchAction(driver);
            //唤醒后，滑动页面，使进入输入密码页
            touch.press(PointOption.point(233, 555)).moveTo(PointOption.point(800, 555)).release().perform();
            //输入密码0000
            for(int i=0;i<4;i++) {
                driver.findElement(By.id("com.android.keyguard:id/key0")).click();
            }
            sleep(5000);
    }

    //手势密码解锁，以华为MT7为例，这里的手势密码是一个"了"字
    public static void unlock_gesture() throws InterruptedException {
        System.out.println("屏幕已唤醒，现在去手势密码解锁" );
        int width=driver.manage().window().getSize().width;
        int height=driver.manage().window().getSize().height;
        System.out.println("屏幕大小："+width+" X "+height );

        TouchAction touch = new TouchAction(driver);
        //唤醒后，滑动页面，使进入输入密码页
        touch.press(PointOption.point(233, 455)).moveTo(PointOption.point(800, 455)).release().perform();
        PointOption point1=PointOption.point(269,1071);//坐标绝对值，不能用，要用偏移量
        PointOption point2=PointOption.point(809,1071);//坐标绝对值，不能用，要用偏移量
        PointOption point3=PointOption.point(539,1341);//坐标绝对值，不能用，要用偏移量
        PointOption point4=PointOption.point(539,1611);//坐标绝对值，不能用，要用偏移量
        PointOption point5=PointOption.point(269,1341);//坐标绝对值，不能用，要用偏移量

        sleep(1000);
        touch.press(PointOption.point(269, 1071))
                .moveTo(PointOption.point(133*4, 0))
                .moveTo(PointOption.point(-133*2,133*2))
                .moveTo(PointOption.point(0,133*2))
                .moveTo(PointOption.point(-133*2,-133*2))
                .release().perform();
        sleep(4000);

    }

    @Test
    public void test_getAttribute() throws InterruptedException {
//        Thread.sleep(3000);
        try {
            String resourceId=driver.findElement(By.id("com.xueqiu.android:id/tab_name")).getAttribute("resourceId");
            System.out.println("1111111: "+resourceId);
        }catch (Exception e){
            System.out.println("未找到 3");
        }
        System.out.println("222222: ");
    }

    @Test
    public void test_swip(){//滑动
        sleep(2000);
        TouchAction touchAction=new TouchAction(driver);
        touchAction.press(PointOption.point(507, 1400)).moveTo(PointOption.point(507, -400)).release().perform();
        sleep(5000);

    }

    @Test
    public void test_longPress(){//长按
        WebElement e=  driver.findElement(By.id("portfolio_stockName"));
        TouchAction touch=new TouchAction(driver);
        touch.longPress(ElementOption.element(e));
        touch.release();
        touch.perform();

        //以下是设置长按保持时长的方式：
        touch.longPress(new LongPressOptions().withElement(ElementOption.element(e)).withDuration(Duration.ofSeconds(6))).perform();
        touch.longPress(new LongPressOptions().withPosition(PointOption.point(259,1071)).withDuration(Duration.ofSeconds(6))).perform();
    }

    @Test
    public void test_getLocation() throws InterruptedException {
        WebElement e=  driver.findElement(By.id("com.xueqiu.android:id/tv_search"));
        Point point=e.getLocation();
//        System.out.println("1----"+point.toString());
        e.click();
        driver.navigate().back();
        driver.navigate().back();

        TouchAction touch =new TouchAction(driver);
        touch.press(PointOption.point(353,105)).release().perform();//可点击
        driver.navigate().back();
        driver.navigate().back();

        touch.tap(PointOption.point(353,105)).perform();//可点击
        Thread.sleep(10000);
        driver.navigate().back();
        driver.navigate().back();
        Thread.sleep(10000);
    }

    @Test
    //点击clickable=false的元素-》结果：可以点击，并跳转页面
    public void test_ClickUnclickable() throws InterruptedException {
        WebElement e=  driver.findElement(By.xpath("//*[@text='沪深' and @resource-id='com.xueqiu.android:id/button_text']"));
        System.out.println("是否可被点击："+e.getAttribute("clickable"));
        e.click();
        Thread.sleep(10000);
    }

    @Test
    public void test_Rotate(){
        driver.rotate(ScreenOrientation.LANDSCAPE);//横屏
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='App']")).click();
        driver.navigate().back();
        driver.rotate(ScreenOrientation.PORTRAIT);//竖屏
        sleep(2000);
        driver.openNotifications();//手机的消息下拉框
        sleep(3000);
    }

    @Test
    public void test_logs(){
        System.out.println(driver.manage().logs().getAvailableLogTypes());//当前driver支持的log类型，一般会打印[logcat, client]
        for(Object o:driver.manage().logs().get("logcat").getAll().toArray()){ //打印logcat日志
            System.out.println(o);
        }
    }

    @Test
    public void test_performance() throws Exception {
        System.out.println(driver.getSupportedPerformanceDataTypes());
        System.out.println(driver.getPerformanceData("com.xueqiu.android","memoryinfo",10));
        System.out.println(driver.getPerformanceData("com.xueqiu.android","cpuinfo",10));
        System.out.println(driver.getPerformanceData("com.xueqiu.android","batteryinfo",10));
        System.out.println(driver.getPerformanceData("com.xueqiu.android","networkinfo",10));

    }

    @Test
    public void test_findElementByAccessibilityId(){
        System.out.println("----"+driver.findElement(By.xpath("//*")));
        driver.findElement(By.id("android:id/text1")).click();//resource-id
        sleep(1000);
        driver.navigate().back();
        driver.findElementByAccessibilityId("App").click();//content-desc
        sleep(1000);
        driver.navigate().back();
//        driver.findElement(By.name("App")).click();//不支持，会报错
        sleep(3000);
    }

    @Test
    public void test_Xpath(){
        sleep(5000);
        List<WebElement> list=driver.findElements(By.xpath("//*"));
        System.out.println("总个数 :"+list.size());
        for(WebElement e:list){
            System.out.println(e.toString());
            e.getText();//可以获取页面上所有text
        }
    }

    @Test
    public void test_Toast(){//API Demos中，弹出toast步骤： Views - Popup Menu - MAKE A POUUP - Search
        driver.findElement(By.xpath("//*[@text='Views']")).click();
        //用UiScrollable的方式滚动查找
        driver.findElementByAndroidUIAutomator("" +
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" +
                "new UiSelector().text(\"Popup Menu\").instance(0));").click();
        driver.findElement(By.xpath("//*[contains(@text,'Make')]")).click();
        driver.findElement(By.xpath("//*[@text='Search']")).click();
        WebElement e=driver.findElement(By.xpath("//*[@class='android.widget.Toast']"));//查找toast：class方式
        System.out.println(e.getText());
        System.out.println(driver.getPageSource());//pageSource里包含toast元素，版本：appium-uiautomator2-driver@1.33.1
//        WebElement e3=driver.findElement(By.className("android.widget.Toast"));
//        System.out.println("3333:"+e3.getText());

        WebElement e2=driver.findElement(By.xpath("//*[contains(@text,'Clicked popup menu item Search')]"));//查找toast：toast文本的方式
        System.out.println(e2.getText());

    }

    @Test
    public void test_UiSelector(){
        //用UiSelector查找元素
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Views\")").click();//必须用\"双引号，用单引号会报错
        //用UiScrollable的方式滚动查找
        driver.findElementByAndroidUIAutomator("" +
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
                ".scrollIntoView(new UiSelector().text(\"Popup Menu\").instance(0));").click();
        sleep(5000);
    }

    @Test
    public void test_WebView(){
        System.out.println(driver.getPageSource());

//        driver.findElement(By.xpath("//*[@text='沪深' and @resource-id='com.xueqiu.android:id/button_text']")).click();
        driver.findElement(By.xpath("//*[@text='港美' and @resource-id='com.xueqiu.android:id/button_text']")).click();
//        driver.findElementByAccessibilityId("立即开户").click();
        driver.getContextHandles().toString();
        driver.getContext();
//        System.out.println("111111 "+driver.getContextHandles().toString());
        for(Object s:driver.getContextHandles()){
            System.out.println("2 "+s.toString());
        }
        sleep(4000);
        for(Object s:driver.getContextHandles()){
            System.out.println("------"+driver.getContextHandles().size());
            System.out.println("3 "+s.toString());
        }

        driver.context("WEBVIEW_com.xueqiu.android");
        System.out.println(driver.getPageSource());


    }

    @Test
    public void test_Voice(){
        System.out.println("调节音量~");
        driver.pressKeyCode(25);//音量减小，在MT7上，安卓6.0系统，减小的是媒体音量
        driver.pressKeyCode(25);
        sleep(3000);
    }

    @Test
    //跳转到其他应用，如微信
    public void test_JumpAnotherAPP(){
        driver.startActivity(new Activity("com.tencent.mm","com.tencent.mm.ui.LauncherUI"));//跳转到微信
        driver.findElement(By.xpath("//*[@text='王浩硕']")).click();//在聊天列表中，查找页面上叫"王浩硕"的聊天
        driver.findElementById("com.tencent.mm:id/ami").sendKeys("123");//聊天页，文本框中输入
        sleep(2000);

    }

    @Test
    public void test_ExecShell() throws IOException {
        sleep(3000);
        String cmd="adb shell am start com.tencent.mm/.ui.LauncherUI";
        Process process = Runtime.getRuntime().exec(cmd);

//        driver.execute("adb shell am start com.tencent.mm/.ui.LauncherUI");//这种方式会报错，启动server时设置 --relaxed-security参数也不行
        sleep(3000);
    }




















    @Test
    //显示等待
    public void testXianshiWait(){
        By zixuanTab=By.xpath("//*[contains(@text,'自选') and contains(@resource-id, 'tab_name')]");
        WebDriverWait wait=new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(zixuanTab)).click();
        sleep(3000);

    }

    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    public static void tearDown(){
//        driver.stopRecordingScreen();
        driver.quit();
    }

}
