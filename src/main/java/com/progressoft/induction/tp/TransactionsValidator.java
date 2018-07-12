package com.progressoft.induction.tp;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ZERO;

public interface TransactionsValidator {
   

    List<Violation> validate();

    boolean isBalanced();

    class TransactionsValidatorImpl  implements TransactionsValidator {

        private static final String CREDIT = "C";
        private static final String DEBIT = "D";

        private List<Transaction> transactionsList = new ArrayList<>();

        public TransactionsValidatorImpl(List<Transaction> transactionList) {
            this.transactionsList = transactionList;
        }

        @Override
        public List<Violation> validate() {
            List<Violation> violationsList = new ArrayList<>();
            IntStream.range(0, transactionsList.size()).forEach(i -> violationsList.addAll(validation(i, transactionsList.get(i)).isValid()));
            return violationsList;
        }

        private Validation validation(int i, Transaction tr) {
            return new Validation(new TransactionForm(tr.getType(), tr.getAmount(), tr.getNarration(), i + 1));
        }

        @Override
        public boolean isBalanced() {
            return sumOf(DEBIT).equals(sumOf(CREDIT));
        }

        private BigDecimal sumOf(String type) {
            return transactionsList.stream().filter(tr -> tr.isOfType(type)).map(Transaction::readAmount).reduce(ZERO, BigDecimal::add);
        }
        

    }
}
