package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class wrapper {
    public static boolean search(WebElement inputbox, String keys){
        try{
            inputbox.clear();
            inputbox.sendKeys(keys);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean scrollAndClick(WebElement element, WebDriver driver) throws InterruptedException{
       
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            if (element.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                JavascriptExecutor js = ((JavascriptExecutor)driver);
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                Thread.sleep(2000);
                return true;
            } else {
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean navigate(WebDriver driver, String url){
        try {
            if (!driver.getCurrentUrl().equals(url)) {
                driver.navigate().to(url);
            }
            return driver.getCurrentUrl().equals(url);
        } catch (Exception e) {
                return false;
        }
    }

    

    
}
