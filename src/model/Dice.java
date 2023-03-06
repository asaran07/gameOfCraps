package model;

import java.util.Random;

public class Dice {

    public static Random rand = new Random();
    private static final int MIN_SIDE = 1;
    private static final int MAX_SIDE = 6;
    private int mySide;

    public Dice() {
        mySide = rollDice();
    }

    public int rollDice() {
        return mySide = rand.nextInt(MAX_SIDE) + MIN_SIDE;
    }

    public int getMySide() {
        return mySide;
    }

    @Override
    public String toString() {
        return String.valueOf(getMySide());
    }
}
