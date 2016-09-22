package se.svennesson.izettle.models;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import java.util.Objects;
import java.util.UUID;

public class AccessToken {

    private UUID token;
    private DateTime distributedTime;
    private Long userId;

    public AccessToken(UUID token, DateTime distributedTime, Long userId) {
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

    public DateTime getDistributedTime() {
        return distributedTime;
    }

    public void setDistributedTime(DateTime distributedTime) {
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
