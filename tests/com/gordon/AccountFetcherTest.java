package com.gordon;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountFetcherTest {

    @Test
    void fetchAllAccountsReturnsExactlyTwentyFiveResolvedAccounts() {
        AccountFetcher fetcher = new AccountFetcher(
                new AccountIdService(),
                new AccountPhoneService(),
                new AccountEmailService());

        List<ResolvedAccount> accounts = null;

        try {
            accounts = fetcher.fetchAllAccounts();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull(accounts);

        assertEquals(25,
                accounts.size());

    }
}