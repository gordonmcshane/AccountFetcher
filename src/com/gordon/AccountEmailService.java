package com.gordon;

import java.util.Optional;

class AccountEmailService extends DelayedService {

    private static String generateEmailForAccountId(long id) {
        return String.format("email%d@example.com", id);
    }

    public Optional<String> getEmailForUserId(long id) {

        incurOverhead();
        if (id % 4 == 0) {
            return Optional.of(generateEmailForAccountId(id));
        }

        return Optional.empty();
    }
}
