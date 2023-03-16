package model;

/**
 * Player class.
 *
 * @author Arshdeep Saran
 */

public class Player {

    /** Stores the player's cash. */
    private int myCurrentCash;

    /** Stores the player's bet. */
    private int myCurrentBet;

    /** Stores the player's roll. */
    private int myCurrentRoll;

    /** Stores the player's win status. */
    private boolean myWin;

    /** Stores the player's lose status. */
    private boolean myLose;

    /** Stores the player's point value. */
    private int myPoint;

    /**
     * Constructs the player.
     */
    public Player() {
        myCurrentCash = 0;
        myCurrentBet = 0;
        myCurrentRoll = 0;
        myWin = false;
    }

    /**
     * Resets the player to default values.
     */
    public void reset() {
        myCurrentCash = 0;
        myCurrentBet = 0;
        myCurrentRoll = 0;
        myWin = false;
    }

    /** Returns the players current cash */
    public int getCurrentCash() {
        return myCurrentCash;
    }

    /**
     * Sets the players cash to desired amount.
     * @param theCurrentCash int
     */
    public void setCurrentCash(int theCurrentCash) {
        myCurrentCash = theCurrentCash;
    }

    /**
     * Returns the players bet.
     * @return int
     */
    public int getCurrentBet() {
        return myCurrentBet;
    }

    /**
     * Sets the player's bet to desired value.
     * @param theCurrentBet int
     */
    public void setCurrentBet(int theCurrentBet) {
        myCurrentBet = theCurrentBet;
    }

    /**
     * Returns the players point.
     * @return int
     */
    public int getMyPoint() {
        return myPoint;
    }

    /**
     * Sets the players point to desired point.
     * @param myPoint int
     */
    public void setMyPoint(int myPoint) {
        this.myPoint = myPoint;
    }

    /** Returns the players current roll */
    public int getCurrentRoll() {
        return myCurrentRoll;
    }

    /**
     * Sets the player's roll to desired value.
     * @param myCurrentRoll int
     */
    public void setCurrentRoll(int myCurrentRoll) {
        this.myCurrentRoll = myCurrentRoll;
    }

}
