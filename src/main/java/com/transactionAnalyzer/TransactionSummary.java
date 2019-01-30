package com.transactionAnalyzer;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TransactionSummary {

	private static Scanner sc;

	public static void main(String[] args) {
		TransactionSummary transactionSummary = new TransactionSummary();
		sc = new Scanner(System.in);
		System.out.print("AccountId: ");
		String accountId = sc.nextLine();
		System.out.print("\n" + "from: ");
		String fromDate = sc.nextLine();
		System.out.print("\n" + "to: ");
		String toDate = sc.nextLine();
		String result = transactionSummary.generateTransactionSummary(accountId, fromDate, toDate);
		String resultArr[] = result.split(",");
		System.out.println("Relative balance for the period is: " + resultArr[0]);
		System.out.println("Number of transactions included is: " + resultArr[1]);

	}

	public String generateTransactionSummary(String accountId, String fromDate, String toDate) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime fromDateInput = formatter.parseDateTime(fromDate);
		DateTime toDateInput = formatter.parseDateTime(toDate);
		List<Transaction> transactionList = new ArrayList<>();
		Double amount = 0.0d;

		transactionList = generateTransactionList(accountId, transactionList);

		List<Transaction> filteredTransactionList = filterReversalTransaction(accountId, fromDateInput, toDateInput,
				transactionList);

		amount = aggregateTransaction(accountId, amount, filteredTransactionList);
		return amount.toString()+","+filteredTransactionList.size();
		

	}

	public List<Transaction> generateTransactionList(String accountId, List<Transaction> transactionList) {
		try (Stream<String> stream = Files
				.lines(Paths.get(getClass().getClassLoader().getResource("Transaction.csv").toURI()))) {
			transactionList = stream.filter(line -> !line.isEmpty()).skip(1).map(convertToTransaction())
					.filter(txn -> txn.getToAccountId().equalsIgnoreCase(accountId)
							|| txn.getFromAccountId().equalsIgnoreCase(accountId))
					.collect(Collectors.toList());

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return transactionList;
	}

	private Function<String, Transaction> convertToTransaction() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		return csvLine -> {
			String[] fields = readCsvFields(csvLine);
			String transactionId = (fieldData(fields[0]));
			String fromAccountId = (fieldData(fields[1]));
			String toAccountId = (fieldData(fields[2]));
			DateTime transferDate = formatter.parseDateTime(fieldData(fields[3]));
			BigDecimal amount = new BigDecimal(fieldData(fields[4]));
			String transactionType = fieldData(fields[5]);
			String relatedTransactionType = fields.length == 7 ? fieldData(fields[6]) : "";
			return new Transaction(transactionId, fromAccountId, toAccountId, transferDate, amount, transactionType,
					relatedTransactionType);
		};
	}

	public List<Transaction> filterReversalTransaction(String accountId, DateTime fromDateInput, DateTime toDateInput,
			List<Transaction> transactionList) {
		List<Transaction> normalTransactionList = transactionList.stream()
				.filter(txn -> txn.getToAccountId().equalsIgnoreCase(accountId)
						|| txn.getFromAccountId().equalsIgnoreCase(accountId))
				.filter(txn -> fromDateInput.compareTo(txn.getTransferDate()) <= 0)
				.filter(txn -> toDateInput.compareTo(txn.getTransferDate()) > 0)
				.filter(txn -> !(txn.getTransactionType().equalsIgnoreCase(TransactionType.REVERSAL.toString())))
				.collect(Collectors.toList());

		final List<Transaction> reversalTransactionList = transactionList.stream()
				.filter(txn -> txn.getToAccountId().equalsIgnoreCase(accountId)
						|| txn.getFromAccountId().equalsIgnoreCase(accountId))
				.filter(txn -> txn.getTransactionType().equalsIgnoreCase(TransactionType.REVERSAL.toString()))
				.collect(Collectors.toList());

		List<Transaction> filteredTransactionList = normalTransactionList.stream()
				.filter(txn -> !reversalTransactionList.stream().map(rt -> rt.getRelatedTransaction())
						.collect(Collectors.toList()).contains(txn.getTransactionId()))
				.collect(Collectors.toList());
		return filteredTransactionList;
	}

	public Double aggregateTransaction(String accountId, Double amount, List<Transaction> filteredTransactionList) {
		for (Transaction t : filteredTransactionList) {
			if (t.getFromAccountId().equals(accountId)) {
				amount = amount - t.getAmount().doubleValue();
			} else if (t.getToAccountId().equals(accountId)) {
				amount = amount + t.getAmount().doubleValue();
			}
		}
		return amount;
	}

	private String[] readCsvFields(String csvLine) {
		String[] fields = csvLine.split(",");
		return fields;
	}

	private String fieldData(String field) {
		return field.trim();
	}

}
