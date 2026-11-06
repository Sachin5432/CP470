package com.example.androidassigments;

/**
 * Manages the message state for the TestToolbar Snackbar notification.
 */
public class ToolbarMessageManager {

    private String currentMessage;
    private final String defaultMessage;

    public ToolbarMessageManager(String defaultMessage) {
        this.defaultMessage = defaultMessage != null ? defaultMessage : "";
        this.currentMessage = this.defaultMessage;
    }

    public String getMessage() {
        return currentMessage;
    }

    public boolean updateMessage(String newMessage) {
        if (newMessage == null || newMessage.trim().isEmpty()) {
            return false;
        }
        this.currentMessage = newMessage.trim();
        return true;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public boolean isDefaultMessage() {
        return currentMessage.equals(defaultMessage);
    }
}