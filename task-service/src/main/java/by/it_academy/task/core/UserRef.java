package by.it_academy.task.core;

import java.util.Objects;
import java.util.UUID;

public class UserRef {
    private UUID uuid;

    public UserRef() {
    }

    public UserRef(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRef userRef)) return false;
        return getUuid().equals(userRef.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
