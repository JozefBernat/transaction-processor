package com.progressoft.induction.tp;

class TransactionForm {
    private final String type;
    private final String amount;
    private final String narration;
    private final int index;

    TransactionForm(String type, String amount, String narration, int index) {
        this.type = type;
        this.amount = amount;
        this.narration = narration;
        this.index = index;
    }

    int getIndex() {
        return index;
    }

    String getType() {
        return type;
    }

    String getAmount() {
        return amount;
    }

    String getNarration() {
        return narration;
    }
}
