package com.example.androidassigments;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure Java manager class for handling chat messages and message list state.
 * Decoupled from Android UI framework to allow high coverage JVM unit testing.
 */
public class ChatMessageManager {

    private final List<String> messages;

    public ChatMessageManager() {
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a chat message if it is non-null and not blank after trimming.
     *
     * @param message Message content to add
     * @return true if message was added, false if ignored due to being null/blank
     */
    public boolean addMessage(String message) {
        if (message == null) {
            return false;
        }
        String trimmed = message.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        return messages.add(trimmed);
    }

    /**
     * Returns total message count.
     */
    public int getMessageCount() {
        return messages.size();
    }

    /**
     * Gets message text at specified position or empty string if index out of bounds.
     */
    public String getMessage(int position) {
        if (position < 0 || position >= messages.size()) {
            return "";
        }
        return messages.get(position);
    }

    /**
     * Determines whether message at position is an incoming message (even index).
     */
    public boolean isIncoming(int position) {
        return position >= 0 && position % 2 == 0;
    }

    /**
     * Determines whether message at position is an outgoing message (odd index).
     */
    public boolean isOutgoing(int position) {
        return position >= 0 && position % 2 != 0;
    }

    /**
     * Returns a copy of all current messages.
     */
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Clears all stored messages.
     */
    public void clear() {
        messages.clear();
    }
}
