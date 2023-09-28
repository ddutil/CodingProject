package checkers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;


public class CheckersProblemTestSuite {

    private WebDriver driver;
    private CheckersPageObject checkersPageObject;

    @Before
    public void initTest() {
        // chromedriver located in //src/main/resources
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "main" + File.separator + "resources" + File.separator +"chromedriver.exe");

        // create new chromedriver
        driver = new ChromeDriver();

        // navigate to url
        String url = "https://www.gamesforthebrain.com/game/checkers/";
        driver.navigate().to(url);

        // maximize window
        driver.manage().window().maximize();

        // create CheckersPage object
        checkersPageObject = new CheckersPageObject(driver);
    }

    @After
    public void tearDown() {
        // quit browser
        driver.quit();
    }

    /* goals of this test:
        Confirm that the site is up
        Make five legal moves as orange:
            a) Include taking a blue piece
            b) Use “Make a move” as confirmation that you can take the next step
            c) Restart the game after five moves
            d) Confirm that the restarting had been successful
     */
    @Test
    public void fiveMovesTest() throws InterruptedException {
        // confirm site is up
        Assert.assertTrue("Page did not load successfully.", checkersPageObject.waitForPageLoad());

        // first move: top left piece move up right
        checkersPageObject.selectPiece();
        Assert.assertTrue("Issue occurred when attempting to make first move.", checkersPageObject.moveSelectedPieceUpRight());

        // second move: same piece move up right, wait for your turn
        Assert.assertTrue("Issue occurred while waiting for your turn before SECOND move.", checkersPageObject.waitForYourTurn());
        checkersPageObject.selectPiece();
        Assert.assertTrue("Issue occurred when attempting to make SECOND move.", checkersPageObject.moveSelectedPieceUpRight());

        // third move: our piece should have been taken, we should be able to take a piece now with new top left-most piece moving up left
        // wait for your turn
        Assert.assertTrue("Issue occurred while waiting for your turn before THIRD move.", checkersPageObject.waitForYourTurn());
        checkersPageObject.selectPiece();
        Assert.assertTrue("Issue occurred when attempting to make THIRD move.", checkersPageObject.moveSelectedPieceUpLeft());

        // fourth move: new top left-most piece up right, wait for turn
        Assert.assertTrue("Issue occurred while waiting for your turn before FOURTH move.", checkersPageObject.waitForYourTurn());
        checkersPageObject.selectPiece();
        Assert.assertTrue("Issue occurred when attempting to make FOURTH move.", checkersPageObject.moveSelectedPieceUpRight());

        // fifth move: same piece left, wait for turn
        Assert.assertTrue("Issue occurred while waiting for your turn before FIFTH move.", checkersPageObject.waitForYourTurn());
        checkersPageObject.selectPiece();
        Assert.assertTrue("Issue occurred when attempting to make FIFTH move.", checkersPageObject.moveSelectedPieceUpLeft());

        // restart game
        // adding some sleeps to easily view results for now
        Thread.sleep(5000);
        Assert.assertTrue("Issue occurred when attempting to restart game.", checkersPageObject.clickRestart());
        Thread.sleep(5000);
    }
}