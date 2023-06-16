package com.paint23.projektpaint.registration;

/**
 * This class is a request with user data for registration.
 * @param name
 * @param userName
 * @param email
 * @param password
 */
public record RegistrationRequest(String name, String userName, String email, String password) { }
