package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.*;
//import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Comparator;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    static ChromeDriver driver;
    private static WebDriverWait wait;
    
    @BeforeSuite
    public void browserSetUp(){
         System.out.println("Constructor: TestCases");
        // WebDriverManager.chromedriver().timeout(30).setup();
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        //WebDriverManager.chromedriver().timeout(30).browserVersion("124.0.6367.208").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
    }
    
    @AfterSuite
    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01(){
        try{

            boolean status;
            status = wrapper.navigate(driver, "https://www.youtube.com/");
            // Assert the URL
            Assert.assertEquals(status, true);
            
            //Scroll to the bottom of the page
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            // WebElement footerElement = driver.findElement(By.xpath("(//div[@id='footer'])[1]"));
            // js.executeScript("arguments[0].scrollIntoView(true);", footerElement);

            //Locate the About element and click on it
            WebElement aboutElement = driver.findElement(By.linkText("About"));
            wrapper.scrollAndClick(aboutElement, driver);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer[@role='contentinfo']")));
            
            //Get the text of the About page and print it
            WebElement aboutContentElement = driver.findElement(By.className("ytabout__main"));
            String aboutContent = aboutContentElement.getText();
            System.out.println(aboutContent);
            

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCase02(){
        try{
            //Navigate to the Youtube
            wrapper.navigate(driver, "https://www.youtube.com/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h3[@class='style-scope ytd-guide-section-renderer'])[3]")));

            //Scroll to Explore section and click on Films tab
            WebElement filmsTab = driver.findElement(By.xpath("//a[@href='/feed/storefront?bp=ogUCKAU%3D']"));
            filmsTab.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Top selling')]")));

                        
            //Locate the right arrow button of Top Selling section and scroll to the extreme right
            WebElement rightArrowButton = driver.findElement(By.xpath("//button[@aria-label='Next']"));
            Thread.sleep(3000);
            Actions actions = new Actions(driver);
            int count = 0;
            //scroll to the extreme right using right arrow
            while(count < 3){
                actions.moveToElement(rightArrowButton).click().perform();
                Thread.sleep(2000);
                count++;
            }

            Thread.sleep(3000);

            SoftAssert softAssert = new SoftAssert();

            //Locate the movie type and checck whether it is of Comedy or Animation
            WebElement movieType = driver.findElement(By.xpath("(//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer'])[16]"));
            Thread.sleep(2000);
            String movieTypeText = movieType.getText();
            boolean isComedyOrAnimation = movieTypeText.contains("Comedy") || movieTypeText.contains("Animation");
            softAssert.assertTrue(isComedyOrAnimation, "The movie is neither 'Comedy' nor 'Animation'.");
            
            //Check whether the movie is 'A' tagged or not using softassert
            WebElement movieRating = driver.findElement(By.xpath("(//div[@aria-label='A'])[3]"));
            Thread.sleep(2000);
            String ratingText = movieRating.getText();
            softAssert.assertEquals(ratingText, "A", "The movie is not marked 'A' for Mature.");

            softAssert.assertAll();
            System.out.println("The movie is marked A and of comedy type");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCase03(){
        try{
            //Navigate to YouTube.com
            wrapper.navigate(driver, "https://www.youtube.com/");

            //Go to the 'Music' tab --(//a[@href='/channel/UC-9-kyTW8ZkZNDHQJ6FgpwQ'])[1]
            WebElement musicTab = driver.findElement(By.xpath("(//a[contains(@title, 'Music')])[1]"));
            musicTab.click();
            Thread.sleep(3000);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space(text())='Discover New Music']")));

            // //Scroll the page
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.scrollBy(0,350)", "");

            // Find all the items within the first section of Music
            WebElement rightArrowButton = driver.findElement(By.xpath("(//button[@aria-label='Next'])[1]"));
            Thread.sleep(3000);
            Actions actions = new Actions(driver);
            int count = 0;
            //scroll to the extreme right using right arrow
            while(count < 3){
            actions.moveToElement(rightArrowButton).click().perform();
            Thread.sleep(2000);
            count++;
            }
            Thread.sleep(3000);

            //Locate and print the name of the playlist
            WebElement playlistElement = driver.findElement(By.xpath("//h3[normalize-space(text())='Bollywood Dance Hitlist']"));
            System.out.println("The playlist name is : "+playlistElement.getText());

            //Soft Assert on whether the number of tracks listed is less than or equal to 50.
            WebElement tracksElement = driver.findElement(By.xpath("(//p[normalize-space(text())='50 tracks'])[4]"));
            String tracksText = tracksElement.getText();

            String[] tracks = tracksText.split(" ");
            int numberOfTracks = Integer.parseInt(tracks[0]);

            // Compare the number of tracks
            SoftAssert softAssert = new SoftAssert();
            boolean status = numberOfTracks <= 50;
            softAssert.assertTrue(status, "The number of tracks listed is more than 50.");

            softAssert.assertAll();
            System.out.println("The music title and number of tracks verified successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCase04(){
        try{
            //Navigate to YouTube.com
            wrapper.navigate(driver, "https://www.youtube.com/");

            // JavascriptExecutor js = (JavascriptExecutor)driver;
            // js.executeScript("window.scrollBy(0,350)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/channel/UCYfdidRxbB8Qhf0Nx7ioOYw']")));
            
            //Locate the newstab and click on it
            WebElement newsTab = driver.findElement(By.xpath("//a[@href='/channel/UCYfdidRxbB8Qhf0Nx7ioOYw']"));
            newsTab.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space(text())='Latest news posts']")));

            //Click on the show more button of the Latest news posts to fetch the 3 latest news
            WebElement moreButton = driver.findElement(By.xpath("(//button[@aria-label='Show more'])[2]"));
            moreButton.click();

            //Locate the first three Latest News
            List<WebElement> latestNews = driver.findElements(By.xpath("(//div[@id='contents'])[5]/ytd-rich-item-renderer")).subList(0, 3);
            int totalLikes = 0;
            for(WebElement news: latestNews){
                WebElement newsText = news.findElement(By.id("post-text"));
                Thread.sleep(2000);
                String newsBody = newsText.getText();
                System.out.println("The Title and body of the news is : "+newsBody);
                
                int likes = 0;
                try {
                    WebElement likesElement = news.findElement(By.id("vote-count-middle"));
                    String likesText = likesElement.getText();
                    if (!likesText.isEmpty()) {
                        likes = Integer.parseInt(likesText);
                    }
                } catch (Exception e) {
                    // Handle case where likes element is not found or parsing fails
                    System.out.println("Likes element not found or parsing failed, setting likes to 0.");
                }

                // Add to the total number of likes
                totalLikes += likes;

                // Print the number of likes for the news post
                System.out.println("Likes: " + likes);
                System.out.println("-------------------------------");

            }
            System.out.println("Total Likes: " + totalLikes);
            System.out.println("The 3 latest news printed successfully");
            Thread.sleep(4000);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    


}