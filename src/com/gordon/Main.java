package com.gordon;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        AccountFetcher fetcher = new AccountFetcher(
                new AccountIdService(),
                new AccountPhoneService(),
                new AccountEmailService());

        List<ResolvedAccount> accounts;

        try {
            accounts = fetcher.fetchAllAccounts();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        accounts.forEach(System.out::println);

    }
}
