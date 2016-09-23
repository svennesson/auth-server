package se.svennesson.authserver.models;

import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class BasicCredentials {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public BasicCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public BasicCredentials() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicCredentials that = (BasicCredentials) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .toString();
    }
}
