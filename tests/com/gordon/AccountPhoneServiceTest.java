package com.gordon;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class AccountPhoneServiceTest {

    @Test
    void getPhoneForUserIdReturnsValidPhonesForHalfAccoundIds() {
        AccountPhoneService service = new AccountPhoneService();
        assertEquals(50,
                LongStream
                        .range(100, 200)
                        .boxed()
                        .map(service::getPhoneForUserId)
                        .filter(Optional::isPresent)
                        .count());
    }
}