package by.it_academy.user.util;

import java.time.LocalDateTime;
import java.util.UUID;

public class TBaseEssence {
    private UUID uuid;
    private LocalDateTime dt_create;
    private LocalDateTime dt_update;

    public TBaseEssence() {
    }

    public TBaseEssence(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }
}
