package com.obrttestbot;

import java.util.Objects;

public class Bank {
    private String bankName;
    private int bankMFO;

    public Bank(String bankName, int bankMFO) {
        this.bankName = bankName;
        this.bankMFO = bankMFO;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBankMFO() {
        return bankMFO;
    }

    public void setBankMFO(int bankMFO) {
        this.bankMFO = bankMFO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return bankMFO == bank.bankMFO &&
                Objects.equals(bankName, bank.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, bankMFO);
    }

    @Override
    public String toString() {
        return bankName + " МФО: " + bankMFO;
    }
}
