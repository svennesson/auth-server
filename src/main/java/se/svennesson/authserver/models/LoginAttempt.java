package se.svennesson.authserver.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class LoginAttempt {

    private Long id;
    private Long userId;
    private boolean success;
    private LocalDateTime timestamp;

    public LoginAttempt(Long id, Long userId, boolean success, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.success = success;
        this.timestamp = timestamp;
    }

    public LoginAttempt() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginAttempt that = (LoginAttempt) o;
        return success == that.success &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, success, timestamp);
    }
}
