package checkers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckersPageObject {

    private WebDriver driver;

    // By locators (xpath, id, etc.)
    private By checkersHeader = By.xpath("//h1[text()='Checkers']");
    private By restartLink = By.xpath("//a[text()='Restart...']");
    private By messageText = By.id("message");
    private By orangePieceUnselected = By.xpath("//img[@src='you1.gif']");
    private By orangePieceSelected = By.xpath("//img[@src='you2.gif']");

    // king piece has src of "you1k.gif" when unselected, "you2k.gif" when selected

    private By bluePieceUnselected = By.xpath("//img[@src='me1.gif']");
    private By bluePieceSelected = By.xpath("//img[@src='me2.gif']");
    private By emptySquares = By.xpath("img[contains(@src, 'gray.gif')]");


    /*
    NOTE:
    if i spent more time on this, i'd probably make a variable that is an array list of array lists to keep track of the pieces and their location on the board
     */

    // constructor
    public CheckersPageObject(WebDriver driver) {
        this.driver = driver;
    }

    // method to wait a certain amount of seconds until expected condition is met
    public <T> void waitForSeconds(int seconds, ExpectedCondition<T> expectedCondition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(expectedCondition);
    }

    // method to wait for page to load initially, returns true if page loaded successfully
    public boolean waitForPageLoad() {
        try {
            // wait for checkersHeader to be visible
            waitForSeconds(10, ExpectedConditions.visibilityOf(driver.findElement(checkersHeader)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // method to select an orange piece
    public boolean selectPiece() {
        try {
            //select certain piece, return true if successfully selected
            // for now i just select the first piece matching xpath by default
            driver.findElement(orangePieceUnselected).click();
            // wait for piece to be selected
            waitForSeconds(5, ExpectedConditions.visibilityOf(driver.findElement(orangePieceSelected)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean unselectPiece() {
        // unselect selected piece
        return false;
    }

    // method to move orange piece up and to right if possible
    public boolean moveSelectedPieceUpRight() {
        try {
            // get name attribute and grab the board location int from it
            String nameNumber = driver.findElement(orangePieceSelected).getAttribute("name").replaceAll("[^\\d.]", "");
            int boardLocation = Integer.parseInt(nameNumber);

            // moving up and to right reduces space int by 9
            // NOTE: would need to deal with situation where number is 0-10 to add leading 0 in front of int string
            boardLocation -= 9;
            String newName = "space" + boardLocation;

            // find the up right square
            WebElement upRightSquare = driver.findElement(By.name(newName));

            // get the src attribute for this element
            String srcAttribute = upRightSquare.getAttribute("src");

            // if the space is empty, move there
            if (srcAttribute.contains("gray.gif")) {
                upRightSquare.click();
                // click a second time to reset the message text
                upRightSquare.click();
                return true;
            }

            // if the space has a blue piece, see if we can jump it
            if (srcAttribute.contains("me1.gif")) {
                // NOTE: would need to deal with situation where number is 0-10 to add leading 0 in front of int string
                boardLocation -= 9;
                String jumpName = "space" + boardLocation;

                WebElement upRightJumpSquare = driver.findElement(By.name(jumpName));
                String srcAttributeJump = upRightJumpSquare.getAttribute("src");

                if (srcAttributeJump.contains("gray.gif")) {
                    upRightJumpSquare.click();
                    // click a second time to reset the message text
                    upRightJumpSquare.click();
                    return true;
                }
            }

        } catch (Exception e) {
            // if exception caught, move failed
            return false;
        }
        // if we can't move, return false
        return false;
    }

    public boolean moveSelectedPieceUpLeft() {
        try {
            // get name attribute and grab the board location int from it
            String nameNumber = driver.findElement(orangePieceSelected).getAttribute("name").replaceAll("[^\\d.]", "");
            int boardLocation = Integer.parseInt(nameNumber);

            // moving up and to left increases space int by 11
            // NOTE: would need to deal with situation where number is 0-10 to add leading 0 in front of int string
            boardLocation += 11;
            String newName = "space" + boardLocation;

            // find the up left square
            WebElement upLeftSquare = driver.findElement(By.name(newName));

            // get the src attribute for this element
            String srcAttribute = upLeftSquare.getAttribute("src");

            // if the space is empty, move there
            if (srcAttribute.contains("gray.gif")) {
                upLeftSquare.click();
                // click a second time to reset the message text
                upLeftSquare.click();
                // wait for message text to change
                waitForSeconds(5, ExpectedConditions.textToBe(messageText, "Please wait."));
                return true;
            }

            // if the space has a blue piece, see if we can jump it
            if (srcAttribute.contains("me1.gif")) {
                // NOTE: would need to deal with situation where number is 0-10 to add leading 0 in front of int string
                boardLocation += 11;
                String jumpName = "space" + boardLocation;

                WebElement upLeftJumpSquare = driver.findElement(By.name(jumpName));
                String srcAttributeJump = upLeftJumpSquare.getAttribute("src");

                if (srcAttributeJump.contains("gray.gif")) {
                    upLeftJumpSquare.click();
                    // click a second time to reset the message text
                    upLeftJumpSquare.click();
                    // wait for message text to change
                    waitForSeconds(5, ExpectedConditions.textToBe(messageText, "Please wait."));
                    return true;
                }
            }

        } catch (Exception e) {
            // if exception caught, move failed
            return false;
        }
        // if we can't move, return false
        return false;
    }

    public boolean attemptMoveSelectedPieceUpRightThenUpLeft() {
        // move piece right if possible, if not then move piece left, if can't be moved then unselect
        return false;
    }

    // method to wait for your turn
    // going to assume we double-clicked elsewhere to reset the message
    public boolean waitForYourTurn() {
        try {
            waitForSeconds(10, ExpectedConditions.textToBe(messageText, "Make a move."));
            // doesn't seem to be ready to click new piece quite yet, adding static sleep for now
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickRestart() {
        try {
            driver.findElement(restartLink).click();

            // something here to wait for board to reset
            // wait for 12 of each piece? only in certain rows or spaces
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
