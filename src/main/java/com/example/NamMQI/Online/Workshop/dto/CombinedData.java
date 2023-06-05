package com.example.NamMQI.Online.Workshop.dto;

public class CombinedData {
    private UserDto user;
    private MessageDto message;

    // Getter va setter metodlari

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }
}
