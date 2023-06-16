package com.paint23.projektpaint.email;

/**
 * Interface for sending emails
 */
public interface EmailSender {
    void send(String to, String email);
}
