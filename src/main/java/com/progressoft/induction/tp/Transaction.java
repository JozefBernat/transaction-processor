package com.progressoft.induction.tp;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

public class Transaction {
    private String type;
    private String amount;
    private String narration;

    static final Predicate<Transaction> IS_DEBIT = tr -> tr.getType().equals(Type.D.name());
    static final Predicate<Transaction> IS_CREDIT = tr -> tr.getType().equals(Type.C.name());

    public enum Type {
        C, D
    }

    public Transaction() {
    }

    public Transaction(String type, BigDecimal amount, String narration) {
        this.type = type;
        this.amount = amount.toString();
        this.narration = narration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    private BigDecimal readAmount() {
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            return ZERO;
        }
    }

    static List<Transaction> filter(List<Transaction> list, Predicate<Transaction> predicate){
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    static BigDecimal totalAmount(List<Transaction> list) {
        return list.stream().map(Transaction::readAmount).reduce(ZERO, BigDecimal::add);
    }

    static Validation toValidation(int i, Transaction tr) {
        return new Validation(new TransactionForm(tr.getType(), tr.getAmount(), tr.getNarration(), i + 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return narration != null ? narration.equals(that.narration) : that.narration == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (narration != null ? narration.hashCode() : 0);
        return result;
    }
}
