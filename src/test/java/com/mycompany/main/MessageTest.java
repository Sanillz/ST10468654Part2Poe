package com.mycompany.main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLength_Success() {
        String shortMessage = "Hi Mike, can you join us for dinner tonight";
        assertTrue(shortMessage.length() <= 250);
    }

    @Test
    public void testMessageLength_Failure() {
        String longMessage = "";
        for (int i = 0; i < 260; i++) {
            longMessage = longMessage + "A";
        }
        assertTrue(longMessage.length() > 250);
    }

    @Test
    public void testRecipientCell_Success() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientCell_Failure() {
        Message msg = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");
        String result = msg.checkRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHash_TestData1() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains(":0:"));
        assertTrue(hash.endsWith("HITONIGHT"));
    }

    @Test
    public void testMessageID_Created() {
        Message msg = new Message(1, "+27718693002", "Test message");
        String id = msg.getMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testReturnTotalMessages() {
        int total = Message.returnTotalMessages();
        assertTrue(total >= 0);
    }
}