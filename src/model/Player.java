package model;

public class Player {

    private int myCurrentCash;
    private int myCurrentBet;

    private int myCurrentRoll;
    private boolean myWin;
    private boolean myLose;
    private int myPoint;

    public Player() {
        myCurrentCash = 0;
        myCurrentBet = 0;
        myCurrentRoll = 0;
        myWin = false;
    }

    public int getCurrentCash() {
        return myCurrentCash;
    }

    public void setCurrentCash(int theCurrentCash) {
        myCurrentCash = theCurrentCash;
    }

    public int getCurrentBet() {
        return myCurrentBet;
    }

    public void setCurrentBet(int theCurrentBet) {
        myCurrentBet = theCurrentBet;
    }

    public boolean isWin() {
        return myWin;
    }

    public void setWin(boolean theWin) {
        myWin = theWin;
    }

    public boolean isLose() {
        return myLose;
    }

    public void setLose(boolean theLose) {
        myLose = theLose;
    }

    public int getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(int myPoint) {
        this.myPoint = myPoint;
    }

    public int getCurrentRoll() {
        return myCurrentRoll;
    }

    public void setCurrentRoll(int myCurrentRoll) {
        this.myCurrentRoll = myCurrentRoll;
    }

}
