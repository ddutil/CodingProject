package blackjack;

public class BlackjackProblemTestSuite {

    /*

    ***GO TO CHECKERS SUITE FIRST***

    do not have experience with automating api testing, but have been looking into some options online:
        - postman: I have used postman a little for manual API testing, looks like it could be used to write JavaScript
                scripts for automation
        - RestAssured - library to use to validate REST services in Java, looks like it uses the
                given, when, then language

    Notes:
    - I probably would've tried to use RestAssured to keep it all Java
    - was looking at Network tab of Chrome Dev Tools while messing around with API calls to review responses
    - would call "https://deckofcardsapi.com/api/deck/new/shuffle/" to get one deck of cards, grab the deck id
    - would call "https://deckofcardsapi.com/api/deck/<<deck_id>>/shuffle/" with same deck id to shuffle the existing deck
    - call "https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/?count=3"
    - call "https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/add/?cards=<<cards_drawn>>" and name pile "player1"
    - repeat previous two steps and assign to a new "player2" pile
    - call "https://deckofcardsapi.com/api/deck/<<deck_id>>/pile/<<pile_name>>/list/" to list cards in a specific pile for player1 or player2
    - calculate scores:
        - 2 = 2 points
        - 3 = 3 points
        - 4 = 4 points
        - 5 = 5 points
        - 6 = 6 points
        - 7 = 7 points
        - 8 = 8 points
        - 9 = 9 points
        - 10, J, Q, K = 10 points
        - A = 1 OR 11 points
    - add up all possible scores (there can be multiple with Aces) and see if any equal 21
    - if any equal 21, print the pile name to the console
    - for all calls along the way, check for correct status of 200 returned with success value of true
    - for a negative test, could try creating a new deck then calling "https://deckofcardsapi.com/api/deck/1j1wwh82qio6/draw/?count=53"
        - then check for success value of false

   strange behavior noticed:
   - able to shuffle a deck of 0 cards and get success value of true
   - if you try to draw more cards than the deck currently has, it draws all remaining cards but shows success value of false and shows error value saying there aren't enough cards to draw that many
        - this is misleading error


     */

}
