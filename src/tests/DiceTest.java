package tests;

import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    Dice dice1 = new Dice();
    Dice dice2 = new Dice();

    @Test
    void testGet() {
        int side = dice1.getMySide();
        assertEquals(side, dice1.getMySide());
        System.out.println(side);
    }

    @Test
    void testRandomness() {
        boolean check = true;
        for(int i = 0; i < 100; i++) {
            dice1.rollDice();
            System.out.println(dice1.getMySide());
            if (dice1.getMySide() < 1 || dice1.getMySide() > 6) {
                check = false;
            }
        }
        assertTrue(check);
    }

    @Test
    void testBothDice() {
        boolean check = true;
        for(int i = 0; i < 100; i++) {
            dice1.rollDice();
            dice2.rollDice();
            if (dice1.getMySide() + dice2.getMySide() > 12 || dice1.getMySide() + dice2.getMySide() < 2) {
                check = false;
            }
        }
        assertTrue(check);
    }
}