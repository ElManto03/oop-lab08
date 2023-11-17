package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.*;

class TestDeathNote {

    private DeathNote morte;
    private String human;
    private String human2;

    @BeforeEach
    public void start() {
        morte = new DeathNoteImpl();
        human = "IlGhini";
        human2 = "Poly";
    }

    private void checkMessage(final Exception e) {
        final String mess = e.getMessage();
        assertNotNull(mess);
        assertFalse(mess.isBlank());
    }

    @Test
    public void testRuleNumber() {
        try {
            morte.getRule(0);
            fail();
        } catch (final IllegalArgumentException e) {
            checkMessage(e);
        }
        try {
            morte.getRule(-2);
            fail();
        } catch (final IllegalArgumentException e) {
            checkMessage(e);
        }
        try {
            morte.getRule(DeathNote.RULES.size() + 1);
            fail();
        } catch (final IllegalArgumentException e) {
            checkMessage(e);
        }
    }

    @Test
    public void testTextRules() {
        for (int i = 1; i < DeathNote.RULES.size(); i++) {
            final String rule = morte.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void testWriteName() {
        assertFalse(morte.isNameWritten(human));
        morte.writeName(human);
        assertTrue(morte.isNameWritten(human));
        assertFalse(morte.isNameWritten(human2));
        assertFalse(morte.isNameWritten(""));
    }

    @Test
    public void testDeath() throws InterruptedException {
        try {
            morte.writeDeathCause(human);
            fail();
        } catch (final IllegalStateException e) {
            checkMessage(e);
            morte.writeName(human);
            assertEquals("heart attack", morte.getDeathCause(human));
            morte.writeName(human2);
            assertTrue(morte.writeDeathCause("karting accident"));
            assertEquals("karting accident", morte.getDeathCause(human2));
            Thread.sleep(100);
            assertFalse(morte.writeDeathCause("Killed by Matrix"));
            assertEquals("karting accident", morte.getDeathCause(human2));
        }
    }

    @Test
    public void testDeathDetails() throws InterruptedException {
        try {
            morte.writeDeathCause(human);
            fail();
        } catch (final IllegalStateException e) {
            checkMessage(e);
            morte.writeName(human);
            assertTrue(morte.getDeathDetails(human).isEmpty());
            assertTrue(morte.writeDetails("ran for too long"));
            assertEquals("ran for too long", morte.getDeathDetails(human));
            morte.writeName(human2);
            Thread.sleep(6100);
            assertFalse(morte.writeDetails("Ansia per la PIL"));
            assertTrue(morte.getDeathDetails(human2).isEmpty());
        }
    }

}