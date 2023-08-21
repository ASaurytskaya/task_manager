package by.it_academy.task.core;

import java.util.UUID;

public class UserRef {
    private UUID userID;

    public UserRef(UUID userID) {
        this.userID = userID;
    }

    public UUID getUserID() {
        return userID;
    }

    public UserRef setUserID(UUID userID) {
        this.userID = userID;
        return this;
    }
}
