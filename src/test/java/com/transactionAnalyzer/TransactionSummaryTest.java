package com.transactionAnalyzer;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;

import com.transactionAnalyzer.Transaction;
import com.transactionAnalyzer.TransactionSummary;

import junit.framework.TestCase;

public class TransactionSummaryTest extends TestCase{
private TransactionSummary transactionSummary;
	
	@Before
	public void setUp(){
		transactionSummary = new TransactionSummary();
	}
	
	public void testGenerateTransactionList(){
		String accountId = "ACC334455";
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
	    assertEquals(11, transactionList.size());
		
	}
	
	public void testGenerateTransactionListNoMatchingRecord(){
		String accountId = "ACC334456";
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
	    assertEquals(0, transactionList.size());
		
	}
	
	public void testGenerateTransactionListNull(){
		String accountId = "";
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
	    assertEquals(0, transactionList.size());
		
	}
	
	public void testFilterReversalTransaction() {
		String accountId ="ACC334455"; 
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime fromDateInput = formatter.parseDateTime("20/10/2018 12:00:00");
		DateTime toDateInput = formatter.parseDateTime("20/10/2018 19:00:00");
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
		assertEquals(11, transactionList.size());
		assertEquals(6, transactionSummary.filterReversalTransaction(accountId, fromDateInput,  toDateInput,
				transactionList).size());
		
	}
	
	public void testFilterReversalTransactionNull() {
		String accountId ="ACC334456"; 
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime fromDateInput = formatter.parseDateTime("20/10/2018 12:00:00");
		DateTime toDateInput = formatter.parseDateTime("20/10/2018 19:00:00");
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
		assertEquals(0, transactionSummary.filterReversalTransaction(accountId, fromDateInput,  toDateInput,
				transactionList).size());
		
	}
	
	public void testAggregateTransaction() {
		String accountId = "ACC334455";
		Double amount = 0.0D;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime fromDateInput = formatter.parseDateTime("20/10/2018 12:00:00");
		DateTime toDateInput = formatter.parseDateTime("20/10/2018 19:00:00");
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
		List<Transaction> filteredTransactionList = transactionSummary.filterReversalTransaction(accountId, fromDateInput,  toDateInput,
				transactionList);
		assertEquals(-0.75, transactionSummary.aggregateTransaction(accountId, amount, filteredTransactionList));
	}
	
	public void testAggregateTransactionNoResult() {
		String accountId = "ACC334456";
		Double amount = 0.0D;
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime fromDateInput = formatter.parseDateTime("20/10/2018 12:00:00");
		DateTime toDateInput = formatter.parseDateTime("20/10/2018 19:00:00");
		List<Transaction> transactionList = null;
		transactionList = transactionSummary.generateTransactionList(accountId, transactionList);
		List<Transaction> filteredTransactionList = transactionSummary.filterReversalTransaction(accountId, fromDateInput,  toDateInput,
				transactionList);
		assertEquals(0.00, transactionSummary.aggregateTransaction(accountId, amount, filteredTransactionList));
	}
	
	public void testGenerateTransactionSummary() {
		String accountId = "ACC334455";
		String fromDate = "20/10/2018 12:00:00";
		String toDate = "20/10/2018 19:00:00";
		String outputSummary = transactionSummary.generateTransactionSummary(accountId, fromDate, toDate);
		String resultArr[] = outputSummary.split(",");
		assertEquals("-0.75", resultArr[0]);
		assertEquals("6", resultArr[1]);

	}
	
	public void testGenerateTransactionSummaryNoOutput() {
		String accountId = "ACC334456";
		String fromDate = "20/10/2018 12:00:00";
		String toDate = "20/10/2018 19:00:00";
		String outputSummary = transactionSummary.generateTransactionSummary(accountId, fromDate, toDate);
		String resultArr[] = outputSummary.split(",");
		assertEquals("0.0", resultArr[0]);
		assertEquals("0", resultArr[1]);

	}

}
