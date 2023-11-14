package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

class TestDeathNote {

    private DeathNote morte;
    private String human;

    @BeforeEach
    public void start () {
        morte = new DeathNoteImpl();
        human = "IlGhini";
    } 

    @Test
    public void testRuleNumber () {
        try {
            morte.getRule(0);
            fail();
        } catch (IllegalArgumentException e) {
            final String mess = e.getMessage();
            assertFalse( mess.isBlank() || mess.isEmpty() || Objects.isNull(mess));
        }
        try {
            morte.getRule(-2);
            fail();
        } catch (IllegalArgumentException e) {
            final String mess = e.getMessage();
            assertNotNull(mess);
            assertFalse( mess.isBlank() || mess.isEmpty());

        }
    }

    @Test
    public void testTextRules() {
        for (final String s : DeathNote.RULES) {
            assertNotNull(s);
            assertFalse(s.isBlank() || s.isEmpty());
        }
    }

    @Test
    public void testWriteName() {
        assertFalse(morte.isNameWritten(human));
        morte.writeName(human);
        assertTrue(morte.isNameWritten(human));

    }

}