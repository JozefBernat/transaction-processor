package com.progressoft.induction.tp;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


class Validation {

    private TransactionForm form;
    Validation(TransactionForm form) {
        this.form = form;
    }

    List<Violation> isValid() {

        String amt = form.getAmount().trim();
        String type = form.getType().trim();
        String narration = form.getNarration();
        List<Violation> errorsArray = new ArrayList<>();

        try {
            if (new BigDecimal(amt).compareTo(BigDecimal.ZERO) <= 0)
                errorsArray.add(new Violation(form.getIndex(), "amount"));
        } catch (NumberFormatException e) {
            errorsArray.add(new Violation(form.getIndex(), "amount"));
        }

        try
        {
            Transaction.TransactionType.valueOf(type);
        }
        catch(Exception e)
        {
            errorsArray.add(new Violation(form.getIndex(), "type"));
        }

        if (narration == null || narration.trim().length() == 0)
            errorsArray.add(new Violation(form.getIndex(), "narration"));
        return errorsArray;
    }
}
