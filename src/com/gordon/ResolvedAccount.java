package com.gordon;

public class ResolvedAccount {

    private long id;
    private String email;
    private String phone;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public ResolvedAccount(long id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ResolvedAccount{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
