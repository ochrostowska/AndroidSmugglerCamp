package com.dotnet.smugglercamp.database;

public class ItemsDownloadedEvent {
    private String message;

    public ItemsDownloadedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

