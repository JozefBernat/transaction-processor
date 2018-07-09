package com.progressoft.induction.tp;

public class Violation {
    private int order;
    private String property;

    public Violation(int order, String property) {
        this.order = order;
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Violation violation = (Violation) o;

        if (order != violation.order) return false;
        return property != null ? property.equals(violation.property) : violation.property == null;
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + (property != null ? property.hashCode() : 0);
        return result;
    }
}
