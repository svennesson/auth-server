package se.svennesson.authserver.models;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AccessToken {

    private UUID token;
    private LocalDateTime distributedTime;
    private Long userId;

    public AccessToken(UUID token, LocalDateTime distributedTime, Long userId) {
        this.token = token;
        this.distributedTime = distributedTime;
        this.userId = userId;
    }

    public AccessToken() {}

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public LocalDateTime getDistributedTime() {
        return distributedTime;
    }

    public void setDistributedTime(LocalDateTime distributedTime) {
        this.distributedTime = distributedTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken token1 = (AccessToken) o;
        return Objects.equals(token, token1.token) &&
                Objects.equals(distributedTime, token1.distributedTime) &&
                Objects.equals(userId, token1.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, distributedTime, userId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("token", token)
                .add("distributedTime", distributedTime)
                .add("userId", userId)
                .toString();
    }
}
