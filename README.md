The Solution is implemented using Java 8 Streams and Lambdas.

After the values are input in the command prompt:-

The Transaction CSV file is read from the classpath and kept in the resources folder.

After that records are matched in the CSV are matched using Predicate expression in streams one the basis of Account ID which is compared with the records in CSV with fromAccount or toAccount and added to a list namely transactionList.

Now this transactionlist is segregated into list one with the normal transaction list which contains the transaction which have transaction type as not REVERSAL, and which falls in the to  and from date that was inputted.

The other list that is created contains only the reversal transaction for the specific account id.

Now the transactions from reversal are removed from the normal transaction list which creates a filtered transaction list.

Now this list is iterated and amount is deduced the basis of the from and to account. 


The Transaction.csv file is present in the resource folder and file contains some more transactions as it was there in the document for testing purpose.

Main ClassName: com.transactionAnalyzer.TransactionSummary

- Implemented and tested using Java 8

- Tests require JUnit

- Project dependencies and compiling managed by Maven

- Compile: `mvn compile`

- Test: `mvn test`

- Run: `mvn exec:java`

- Packaging: `mvn package`, compiled jar in *target/* folder