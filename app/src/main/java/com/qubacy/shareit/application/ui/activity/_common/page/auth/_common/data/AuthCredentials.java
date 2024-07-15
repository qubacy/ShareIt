package com.qubacy.shareit.application.ui.activity._common.page.auth._common.data;

import java.util.Objects;

public class AuthCredentials {
    public final String email;
    public final String password;

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthCredentials that = (AuthCredentials) o;

        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
