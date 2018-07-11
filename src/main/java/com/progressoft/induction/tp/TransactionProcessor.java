package com.progressoft.induction.tp;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ZERO;

public interface TransactionProcessor {
    void importTransactions(InputStream is) throws IOException;

    List<Transaction> getImportedTransactions();

    List<Violation> validate();

    boolean isBalanced();

    static TransactionProcessorImpl createCSVTransactionProcessor() {
        return new TransactionProcessorImpl("fileMappingCSV.xml");
    }

    static TransactionProcessorImpl createXmlTransactionProcessor() {
        return new TransactionProcessorImpl("fileMappingXML.xml");
    }

    class TransactionProcessorImpl implements TransactionProcessor {

        private static final String CREDIT = "C";
        private static final String DEBIT = "D";

        private final List<Transaction> transactionsList = new ArrayList<>();
        private final String resourceName;

        private TransactionProcessorImpl(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public List<Transaction> getImportedTransactions() {
            return transactionsList;
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
            return transactionsList.stream().filter(isOfType(type)).map(Transaction::readAmount).reduce(ZERO, BigDecimal::add);
        }

        private Predicate<Transaction> isOfType(String type) {
            return tr -> tr.getType().equals(type);
        }

        @Override
        public void importTransactions(InputStream is) {
            StreamFactory factory = StreamFactory.newInstance();
            factory.loadResource(resourceName);
            BeanReader in = factory.createReader("TransactionList", new InputStreamReader(is));
            Transaction transaction;
            while ((transaction = (Transaction) in.read()) != null) {
                transactionsList.add(transaction);
            }
            in.close();
        }
    }
}
