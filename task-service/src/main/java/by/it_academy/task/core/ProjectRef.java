package by.it_academy.task.core;

import java.util.Objects;
import java.util.UUID;

public class ProjectRef {
    private UUID uuid;

    public ProjectRef() {
    }

    public ProjectRef(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ProjectRef setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectRef that)) return false;
        return getUuid().equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
