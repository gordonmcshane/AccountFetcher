package com.gordon;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class AccountEmailServiceTest {

    @Test
    void getEmailForUserIdReturnsResultForOneQuarterOfIds() {

        AccountEmailService service = new AccountEmailService();

        assertEquals(25,
                LongStream
                        .range(100, 200)
                        .boxed()
                        .map(service::getEmailForUserId)
                        .filter(Optional::isPresent)
                        .count());

    }
}