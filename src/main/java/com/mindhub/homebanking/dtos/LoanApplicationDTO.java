package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private Long loanId;
    private double amount;
    private int payments;
    private String numAccount;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long loanId , double amount, int payments, String numAccount) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.numAccount = numAccount;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }
}
