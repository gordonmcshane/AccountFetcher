package com.gordon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountIdServiceTest {

    @Test
    void getAllAccountsReturnsExactlyOneHundredAccounts() {
        AccountIdService service = new AccountIdService();
        assertEquals(100, service.getAllAccountIds().size());
    }
}