package com.onwelo.emails.model;

public class Email {

    private String message;

    private String subject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Email{" +
                "message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
