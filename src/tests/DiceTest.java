package tests;

import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    Die die1 = new Die();
    Die die2 = new Die();

    @Test
    void testGet() {
        int side = die1.getMySide();
        assertEquals(side, die1.getMySide());
    }

    @Test
    void testRandomness() {
        boolean check = true;
        for(int i = 0; i < 100; i++) {
            die1.rollDice();
            System.out.println(die1.getMySide());
            if (die1.getMySide() < 1 || die1.getMySide() > 6) {
                check = false;
            }
        }
        assertTrue(check);
    }

    @Test
    void testBothDice() {
        boolean check = true;
        for(int i = 0; i < 100; i++) {
            die1.rollDice();
            die2.rollDice();
            if (die1.getMySide() + die2.getMySide() > 12 || die1.getMySide() + die2.getMySide() < 2) {
                check = false;
            }
        }
        assertTrue(check);
    }

    @Test
    void diceTotal() {

    }
}