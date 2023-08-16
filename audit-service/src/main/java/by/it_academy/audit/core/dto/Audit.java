package by.it_academy.audit.core.dto;

import by.it_academy.audit.core.EssenceType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.UUID;

public class Audit {

    private final UUID uuid;

    private final LocalDateTime dtCreate;

    private final User user;

    private final String text;

    private final EssenceType type;

    private final String id;

    @JsonCreator
    private Audit(AuditBuilder builder) {
        this.uuid = builder.uuid;
        this.dtCreate = builder.dtCreate;
        this.user = builder.user;
        this.text = builder.text;
        this.type = builder.type;
        this.id = builder.id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public EssenceType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @JsonDeserialize
    public static class AuditBuilder {
        private UUID uuid;

        private LocalDateTime dtCreate;

        private User user;

        private String text;

        private EssenceType type;

        private String id;

        public AuditBuilder() {
        }

        public UUID getUuid() {
            return uuid;
        }

        public AuditBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public LocalDateTime getDtCreate() {
            return dtCreate;
        }

        public AuditBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public User getUser() {
            return user;
        }

        public AuditBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public String getText() {
            return text;
        }

        public AuditBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public EssenceType getType() {
            return type;
        }

        public AuditBuilder setType(EssenceType type) {
            this.type = type;
            return this;
        }

        public String getId() {
            return id;
        }

        public AuditBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public Audit build() {
            return new Audit(this);
        }
    }
}
