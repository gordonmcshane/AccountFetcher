package com.gordon;

import java.util.Optional;

class AccountPhoneService extends DelayedService {

    private static String generatePhoneForId(long id) {
        String idString = Long.toString(id);
        return "1-555-1" + idString.substring(0, Math.min(idString.length(), 4));
    }

    public Optional<String> getPhoneForUserId(long id) {

        incurOverhead();
        if (id % 2 == 0) {
            return Optional.of(generatePhoneForId(id));
        }

        return Optional.empty();
    }
}
