package model;

import java.util.Random;

/**
 * The die class.
 *
 * @author Arshdeep Saran
 */

public class Die {

    /** Random object for rolling dice. */
    public static Random rand = new Random();

    /** The minimum die side value */
    private static final int MIN_SIDE = 1;

    /** The maximum die side value */
    private static final int MAX_SIDE = 6;

    /** Holds the side */
    private int mySide;


    /**
     * Sets the die's side to desired side.
     * @param theSide int
     */
    public void setSide(final int theSide) {
        if (theSide < 0 || theSide > 7) {
            throw new IllegalArgumentException("Invalid value for die side: " + theSide);
        }
        mySide = theSide;
    }

    /** Sets side to rolled dice */
    public Die() {
        mySide = rollDice();
    }

    /** Rolls the dice */
    public int rollDice() {
        return mySide = rand.nextInt(MAX_SIDE) + MIN_SIDE;
    }

    /** Returns the current side */
    public int getMySide() {
        return mySide;
    }

    /**
     * Returns the total die amount with two dice side values.
     * @param theOtherDie Die
     * @return int
     */
    public int diceTotal(final Die theOtherDie) {
        if (theOtherDie.getClass() == this.getClass()) {
            Die d;
            d = theOtherDie;
            return d.getMySide() + getMySide();
        }
        else {
            throw new IllegalArgumentException("Invalid object");
        }
    }

    /**
     * Returns a string representation of die's side.
     * @return String
     */
    @Override
    public String toString() {
        return String.valueOf(getMySide());
    }
}
