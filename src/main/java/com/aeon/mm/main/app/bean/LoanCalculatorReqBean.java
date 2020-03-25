package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class LoanCalculatorReqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3478756008625904331L;
	private Double financeAmount;
	private Integer loanTerm;
	private boolean motorCycleLoanFlag;

	public double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(double financeAmount) {
		this.financeAmount = financeAmount;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}

	public boolean getMotorCycleLoanFlag() {
		return motorCycleLoanFlag;
	}

	public void setMotorCycleLoanFlag(boolean motorCycleLoanFlag) {
		this.motorCycleLoanFlag = motorCycleLoanFlag;
	}
}
