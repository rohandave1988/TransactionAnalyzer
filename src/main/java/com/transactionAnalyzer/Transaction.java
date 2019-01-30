package com.transactionAnalyzer;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;

public class Transaction {
	
	private String transactionId;
	private String fromAccountId;
	private String toAccountId;
	private DateTime transferDate;
	private BigDecimal amount;
	private String transactionType;
	private String relatedTransaction;
	
	public Transaction(String transactionId, String fromAccountId, String toAccountId, DateTime transferDate, BigDecimal amount, String transactionType,
			String relatedTransactionType) {
		super();
		this.transactionId = transactionId;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.transferDate = transferDate;
		this.amount = amount;
		this.transactionType = transactionType;
		this.relatedTransaction = relatedTransactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public String getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	public DateTime getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(DateTime transferDate) {
		this.transferDate = transferDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getRelatedTransaction() {
		return relatedTransaction;
	}
	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}


}
