package com.mycompany.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// Creates the Message class
public class Message {

    // Stores the unique Message ID (10 digits)
    private String messageID;

    // Stores the message number (1, 2, 3...)
    private int messageNumber;

    // Stores the recipient cell number
    private String recipient;

    // Stores the actual message text
    private String messageText;

    // Stores the Message Hash
    private String messageHash;

    // Stores whether the message was Sent, Stored or Disregarded
    private String status;

    // Keeps a list of all messages created while the program runs
    private static ArrayList<Message> allMessages = new ArrayList<>();

    // Counts how many messages were actually sent
    private static int totalMessagesSent = 0;

    // Creates the Message constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.status = "";
    }

    // Boolean: checkMessageID()
    // This method ensures that the message ID is not more than ten characters
    public boolean checkMessageID() {
        if (messageID != null && messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    // String: checkRecipientCell()
    // This method ensures that the recipient cell number is no more than ten characters long
    // and starts with a code
    public String checkRecipientCell() {
        // Number must start with + and contain only digits after that
        // and the whole number must not be too long
        if (recipient != null
                && recipient.startsWith("+")
                && recipient.length() <= 13
                && recipient.substring(1).matches("[0-9]+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // String: createMessageHash()
    // This method creates and returns the Message Hash
    // Format: first two digits of MessageID : messageNumber : FIRSTLAST (all caps)
    public String createMessageHash() {
        // Get the first two characters of the Message ID
        String firstTwo = messageID.substring(0, 2);

        // Split the message into words
        String[] words = messageText.trim().split("\\s+");

        // Get the first word and the last word
        String firstWord = "";
        String lastWord = "";

        if (words.length > 0) {
            firstWord = words[0];
        }
        if (words.length > 1) {
            lastWord = words[words.length - 1];
        } else {
            lastWord = firstWord;
        }

        // Remove any non-letter characters so the hash is clean
        firstWord = firstWord.replaceAll("[^a-zA-Z]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        // Build the hash and make it uppercase
        messageHash = (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    // String: SentMessage()
    // This method should allow the user to choose if they want to send, store, or disregard the message
    public String SentMessage() {
        System.out.println("\nChoose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Enter choice (1-3): ");

        String choice = Main.input.nextLine().trim();

        if (choice.equals("1")) {
            status = "Sent";
            totalMessagesSent = totalMessagesSent + 1;
            allMessages.add(this);
            return "Message successfully sent.";
        } else if (choice.equals("2")) {
            status = "Disregarded";
            return "Press 0 to delete the message.";
        } else if (choice.equals("3")) {
            status = "Stored";
            allMessages.add(this);
            storeMessage();
            return "Message successfully stored.";
        } else {
            status = "Disregarded";
            return "Invalid option – message disregarded.";
        }
    }

    // String: printMessages()
    // This method returns all the messages sent while the program is running
    public static String printMessages() {
        if (allMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        String result = "";
        for (int i = 0; i < allMessages.size(); i++) {
            Message m = allMessages.get(i);
            result = result + "Message ID: " + m.messageID + "\n";
            result = result + "Message Hash: " + m.messageHash + "\n";
            result = result + "Recipient: " + m.recipient + "\n";
            result = result + "Message: " + m.messageText + "\n";
            result = result + "Status: " + m.status + "\n";
            result = result + "------------------------\n";
        }
        return result;
    }

    // Int: returnTotalMessages()
    // This method returns the total number of messages sent
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Your own defined storeMessage() method
    // Research how to create a method to store the messages in JSON
    public void storeMessage() {
        try {
            FileWriter file = new FileWriter("messages.json");

            // Build a simple JSON array as a string
            String json = "[\n";
            for (int i = 0; i < allMessages.size(); i++) {
                Message m = allMessages.get(i);
                json = json + "  {\n";
                json = json + "    \"messageID\": \"" + m.messageID + "\",\n";
                json = json + "    \"messageNumber\": " + m.messageNumber + ",\n";
                json = json + "    \"recipient\": \"" + m.recipient + "\",\n";
                json = json + "    \"message\": \"" + m.messageText.replace("\"", "\\\"") + "\",\n";
                json = json + "    \"messageHash\": \"" + m.messageHash + "\",\n";
                json = json + "    \"status\": \"" + m.status + "\"\n";
                json = json + "  }";
                if (i < allMessages.size() - 1) {
                    json = json + ",";
                }
                json = json + "\n";
            }
            json = json + "]";

            file.write(json);
            file.close();
            System.out.println("Messages successfully stored in messages.json");
        } catch (IOException e) {
            System.out.println("Error writing to JSON file: " + e.getMessage());
        }
    }

    // Helper method to generate a random 10-digit Message ID
    private String generateMessageID() {
        Random rnd = new Random();
        long number = 1000000000L + (long) (rnd.nextDouble() * 9000000000L);
        return String.valueOf(number);
    }

    // Getters so we can display the details
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getStatus() {
        return status;
    }
}