package com.progressoft.induction.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.progressoft.induction.tp.Transaction.*;

public interface TransactionsValidator {
    List<Violation> validate();
    boolean isBalanced();

    class TransactionsValidatorImpl implements TransactionsValidator {

        private List<Transaction> list;

        public TransactionsValidatorImpl(List<Transaction> transactionList) {
            this.list = transactionList;
        }

        @Override
        public List<Violation> validate() {
            List<Violation> violationsList = new ArrayList<>();
            IntStream.range(0, list.size()).forEach(i -> violationsList.addAll(Transaction.toValidation(i, list.get(i)).isValid()));
            return violationsList;
        }

        @Override
        public boolean isBalanced() {
            return totalAmount(filter(list, IS_DEBIT)).equals(totalAmount(filter(list, IS_CREDIT)));
        }
    }
}
