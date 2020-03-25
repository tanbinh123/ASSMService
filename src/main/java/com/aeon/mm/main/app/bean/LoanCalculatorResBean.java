package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class LoanCalculatorResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8616362673015474531L;
	private double processingFees;
	private double totalRepayment;
	private double firstPayment;
	private double monthlyPayment;
	private double lastPayment;
	private double conSaving;
	private double totalConSaving;

	public double getProcessingFees() {
		return processingFees;
	}

	public void setProcessingFees(double processingFees) {
		this.processingFees = processingFees;
	}

	public double getTotalRepayment() {
		return totalRepayment;
	}

	public void setTotalRepayment(double totalRepayment) {
		this.totalRepayment = totalRepayment;
	}

	public double getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(double firstPayment) {
		this.firstPayment = firstPayment;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public double getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(double lastPayment) {
		this.lastPayment = lastPayment;
	}

	public double getConSaving() {
		return conSaving;
	}

	public void setConSaving(double conSaving) {
		this.conSaving = conSaving;
	}

	public double getTotalConSaving() {
		return totalConSaving;
	}

	public void setTotalConSaving(double totalConSaving) {
		this.totalConSaving = totalConSaving;
	}

}
