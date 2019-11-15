package com.obrttestbot;

import java.util.Objects;

public class Firm {
    private String firmName;
    private String firmType;
    private long firmEDRPOU;
    private Bank firmBank;
    private String firmBankAccount;
    private String firmHead;

    @Override
    public String toString() {
        return firmType + " " + firmName + "ЄДРПОУ " + firmEDRPOU + "\n" +
                "Р/р: " + firmBankAccount + " " + firmBank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firm firm = (Firm) o;
        return firmEDRPOU == firm.firmEDRPOU &&
                Objects.equals(firmName, firm.firmName) &&
                Objects.equals(firmType, firm.firmType) &&
                Objects.equals(firmBank, firm.firmBank) &&
                Objects.equals(firmBankAccount, firm.firmBankAccount) &&
                Objects.equals(firmHead, firm.firmHead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmName, firmType, firmEDRPOU, firmBank, firmBankAccount, firmHead);
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getFirmType() {
        return firmType;
    }

    public void setFirmType(String firmType) {
        this.firmType = firmType;
    }

    public long getFirmEDRPOU() {
        return firmEDRPOU;
    }

    public void setFirmEDRPOU(long firmEDRPOU) {
        this.firmEDRPOU = firmEDRPOU;
    }

    public Bank getFirmBank() {
        return firmBank;
    }

    public void setFirmBank(Bank firmBank) {
        this.firmBank = firmBank;
    }

    public String getFirmBankAccount() {
        return firmBankAccount;
    }

    public void setFirmBankAccount(String firmBankAccount) {
        this.firmBankAccount = firmBankAccount;
    }

    public String getFirmHead() {
        return firmHead;
    }

    public void setFirmHead(String firmHead) {
        this.firmHead = firmHead;
    }
}
