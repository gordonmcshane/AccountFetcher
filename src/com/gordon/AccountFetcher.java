package com.gordon;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class AccountFetcher {

    private AccountIdService idService;
    private AccountPhoneService phoneService;
    private AccountEmailService emailService;
    private ExecutorService executor;

    public AccountFetcher(AccountIdService idService,
                          AccountPhoneService phoneService,
                          AccountEmailService emailService) {

        this.idService = idService;
        this.phoneService = phoneService;
        this.emailService = emailService;

        executor = Executors.newCachedThreadPool();
    }

    private Optional<ResolvedAccount> fetchAccountDetails(long id) {

        // We request email first because we know our email data is considerably more sparse
        Optional<String> emailResult = emailService.getEmailForUserId(id);

        if (emailResult.isPresent()) {
            Optional<String> phoneResult = phoneService.getPhoneForUserId(id);

            if (phoneResult.isPresent()) {
                return Optional.of(new ResolvedAccount(id, emailResult.get(), phoneResult.get()));
            }
        }

        return Optional.empty();
    }

    private CompletableFuture<Optional<ResolvedAccount>> getFetchFutureForId(long id) {
        return CompletableFuture.supplyAsync(() -> fetchAccountDetails(id), executor);
    }

    private static <TResult> CompletableFuture<Void> getCombinedFuture(List<CompletableFuture<TResult>> futures) {
        CompletableFuture[] futureArray = new CompletableFuture[futures.size()];
        return CompletableFuture.allOf(futures.toArray(futureArray));
    }

    public List<ResolvedAccount> fetchAllAccounts() throws ExecutionException, InterruptedException {
        List<AccountIdService.Account> accounts = idService.getAllAccountIds();

        List<CompletableFuture<Optional<ResolvedAccount>>> fetchFutures =
                accounts
                        .stream()
                        .map(u -> getFetchFutureForId(u.getId()))
                        .collect(Collectors.toList());


        // block until completion
        getCombinedFuture(fetchFutures).get();

        return fetchFutures
                .stream()
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
