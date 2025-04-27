package com.example.demo2;

public class FineCalculator {
    private FineCalculation fineCalc;

    public FineCalculator(FineCalculation fineCalc) {
        this.fineCalc = fineCalc;
    }

    public double calculateFine(int daysOverdue) {
        return fineCalc.calculateFine(daysOverdue);
    }

    public void setFineCalc(FineCalculation fineCalc) {
        this.fineCalc = fineCalc;
    }

}
