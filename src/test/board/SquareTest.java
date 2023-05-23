package test.board;

import main.classes.board.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testToStringA1() {
        Square square  = new Square(0,0);

        assertEquals("a1", square.toString());
    }

    @Test
    void testToStringB6() {
        Square square  = new Square(1,5);

        assertEquals("b6", square.toString());
    }
}