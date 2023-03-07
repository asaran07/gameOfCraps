package model;

import java.util.Random;

public class Die {

    public static Random rand = new Random();
    private static final int MIN_SIDE = 1;
    private static final int MAX_SIDE = 6;

    public void setSide(final int theSide) {
        if (theSide < 0 || theSide > 7) {
            throw new IllegalArgumentException("Invalid value for die side: " + theSide);
        }
        mySide = theSide;
    }

    private int mySide;

    public Die() {
        mySide = rollDice();
    }

    public int rollDice() {
        return mySide = rand.nextInt(MAX_SIDE) + MIN_SIDE;
    }

    public int getMySide() {
        return mySide;
    }

    public int diceTotal(final Die theOtherDie) {
        if (theOtherDie.getClass() == this.getClass()) {
            Die d = new Die();
            d = (Die) theOtherDie;
            return d.getMySide() + getMySide();
        }
        else {
            throw new IllegalArgumentException("Invalid object");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getMySide());
    }
}
