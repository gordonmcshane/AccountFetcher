package com.gordon;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

class AccountIdService extends DelayedService {

    final class Account {

        private long id;

        public long getId() {
            return id;
        }

        public Account(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "id=" + id +
                    '}';
        }
    }

    private List<Account> generateAccountIds(long startingId, long count) {

        return LongStream
                .range(startingId, count)
                .boxed()
                .map(Account::new)
                .collect(Collectors.toList());
    }

    public List<Account> getAllAccountIds() {
        incurOverhead();
        return generateAccountIds(100, 200);
    }
}
