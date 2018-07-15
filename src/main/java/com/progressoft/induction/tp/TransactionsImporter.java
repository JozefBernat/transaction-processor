package com.progressoft.induction.tp;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public interface TransactionsImporter {
    void importTransactions(InputStream is);

    List<Transaction> getImportedTransactions();

    static TransactionsImporter.TransactionsImportImpl createCSVTransactionProcessor() {
        return new TransactionsImporter.TransactionsImportImpl("fileMappingCSV.xml");
    }

    static TransactionsImporter.TransactionsImportImpl createXmlTransactionProcessor() {
        return new TransactionsImporter.TransactionsImportImpl("fileMappingXML.xml");
    }

    class TransactionsImportImpl implements TransactionsImporter {

        public final List<Transaction> transactionsList = new ArrayList<>();
        private final String resourceName;

        private TransactionsImportImpl(String resourceName) {
            this.resourceName = resourceName;
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

        @Override
        public List<Transaction> getImportedTransactions() {
            return transactionsList;
        }
    }
}


