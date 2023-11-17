package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private static final int WRITE_DEATH_LIMIT = 40;
    private static final int WRITE_DETAILS_LIMIT = 6040;

    private final Map<String, Death> deathnote;
    private String lastDeath;
    private long writtenTime;

    public DeathNoteImpl() {
        deathnote = new HashMap<>();
        lastDeath = "";
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber > 0 && ruleNumber < RULES.size()) {
            return RULES.get(ruleNumber);
        } else {
            throw new IllegalArgumentException("Rule number " + ruleNumber + " does not exist");
        }
    }

    @Override
    public void writeName(final String name) {
        if (Objects.nonNull(name)) {
            deathnote.put(name, new Death());
            writtenTime = System.currentTimeMillis();
            lastDeath = name;
        } else {
            throw new NullPointerException();
        }       
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (deathnote.isEmpty() || Objects.isNull(cause)) {
            throw new IllegalStateException("Death note empty or null string was passed");
        }
        final long now = System.currentTimeMillis();
        if (now - this.writtenTime <= WRITE_DEATH_LIMIT) {
            deathnote.get(lastDeath).setCause(cause);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if (deathnote.isEmpty() || Objects.isNull(details)) {
            throw new IllegalStateException("Death note empty or null string was passed");
        }
        final long now = System.currentTimeMillis();
        final long before = this.writtenTime;
        if (now - before <= WRITE_DETAILS_LIMIT) {
            deathnote.get(lastDeath).setDetails(details);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException(name + "is not present in the death note");
        }
        return deathnote.get(name).getCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException(name + "is not present in the death note");
        }
        final String details = deathnote.get(name).getDetails();
        if (details.isBlank()) {
            return "";
        } else {
            return details;
        }
    }

    @Override
    public boolean isNameWritten(final String name) {
        return deathnote.containsKey(name);
    }

    class Death {

        private static final String DEFAULT_CAUSE = "heart attack";
        private String cause;
        private String details;

        public Death() {
            this.cause = DEFAULT_CAUSE;
            this.details = "";
        }

        public String getCause() {
            return this.cause;
        }

        public String getDetails() {
            return this.details;
        }

        public void setCause(final String cause) {
            this.cause = cause;
        }

        public void setDetails(final String details) {
            this.details = details;
        }

    }

}
