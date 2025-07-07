package lab4.game;

import lab4.game.Col;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColTest {
    @Test
    public void testValidInputsForLeft() {
        assertEquals(Col.Left, Col.from("1"));
        assertEquals(Col.Left, Col.from("l"));
        assertEquals(Col.Left, Col.from("L"));
    }
    @Test
    public void testValidInputsForMiddle() {
        assertEquals(Col.Middle, Col.from("2"));
        assertEquals(Col.Middle, Col.from("m"));
        assertEquals(Col.Middle, Col.from("M"));
        assertEquals(Col.Middle, Col.from("c"));
        assertEquals(Col.Middle, Col.from("C"));
    }
    @Test
    public void testValidInputsForRight() {
        assertEquals(Col.Right, Col.from("3"));
        assertEquals(Col.Right, Col.from("r"));
        assertEquals(Col.Right, Col.from("R"));
    }
    @Test
    public void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> Col.from("x"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("left"));
        assertThrows(IllegalArgumentException.class, () -> Col.from("4"));
        assertThrows(IllegalArgumentException.class, () -> Col.from(""));
    }
}