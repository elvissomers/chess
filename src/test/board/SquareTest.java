package test.board;

import main.classes.board.Square;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void testToString() {
        Square square  = new Square(0,0);

        assertEquals("a1", square.toString());
    }
}