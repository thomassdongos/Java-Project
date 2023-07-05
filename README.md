
# Step 2 – understand and comment

task 1:
overall correct. db design needs improvement as it will be shown later.
indexes on foreign keys are added automatically.

task 2: overall correct, missing additional db entries, added some entries through the UI.
task 3, 4 correct

task 5: missing button return to homepage. Created the button, that redirects to home page an added it.

task 6: missing button return to homepage. Created the button, that redirects to home page an added it.

task 7: missing button return to homepage. Created the button, that redirects to home page an added it.

other code optimisations:

- DI using constructor and not @Autowired annotation.

- Creation of interfaces for services for added abstraction and implementing the Dependency Inversion Principle.

- Added the @Transactional annotation on service classes and @EnableTransactionManagement on application class. The @Transactional annotation in Spring Boot provides several benefits when working with database transactions:

a)Simplified Transaction Management: By using @Transactional, you can simplify the management of database transactions. The annotation handles transactional behavior automatically, allowing you to focus on writing business logic without explicitly managing transaction boundaries.

b)Atomicity: Transactions ensure that a group of database operations either succeed entirely or fail altogether. If an exception occurs during a transaction, all changes made within that transaction are rolled back, ensuring data integrity. The @Transactional annotation helps enforce this behavior, making it easier to maintain data consistency.

c)Consistency: @Transactional ensures that the database remains in a consistent state before and after each transaction. It guarantees that data modifications adhere to defined constraints, rules, and relationships.

d)Rollback and Commit: By default, @Transactional rolls back a transaction if an unchecked exception occurs. This behavior helps ensure data consistency and avoids leaving the database in an inconsistent state. You can also explicitly specify when to commit a transaction using the readOnly attribute or by customizing the transactional behavior.

- The design of the existing schema is docoumented and can be seen in the erd diagram named "ERD-original".

- The database schema can be improved as shown in the erd diagram "ERD-improved". I have added an additional table called user_address with the independed fields for the address details. The table should have a relationship of one to one with the user table. The user_address field in user table is a FK to the user_address table. Creating an independent user address table can offer several advantages in terms of database design and normalization. Separating the address information into its own table can provide better flexibility, efficiency, and maintainability. Here are some benefits of having an independent user address table:

a)Data Normalization: By separating the address information into its own table, you avoid data duplication. Each user can have a single row in the user table, while their address details can be stored in a separate row in the address table. This helps eliminate redundant data and improves data integrity.

b)Flexibility and Extensibility: With an independent address table, you can easily add or modify address-related fields without affecting the user table or other parts of the system. This allows for future enhancements or changes to the address structure without requiring major modifications to the existing schema.

c)Improved Querying and Filtering: Storing address information in a separate table allows for more efficient querying and filtering based on address attributes. You can easily retrieve users based on specific address criteria such as city, state, or postal code, as well as perform other complex address-related queries.

d)Address Reusability: If multiple users share the same address, storing the address details in a separate table allows for address reuse. Instead of duplicating the same address data for each user, you can reference a single address record from multiple user records, optimizing storage and reducing redundancy.

e)Maintainability: An independent address table can simplify data maintenance and updates. If there are any changes or corrections to address information, you only need to update the address table rather than modifying each user record.

----------------------------------------------------------------------------------------

# Step 3 - enhancements

1)
a) One way to avoid double loans/double returns is by implementing blocking calls to our api, a synchronization mechanism to ensure that the API endpoints are not called concurrently. One approach is to use the synchronized keyword in Java. A shared lock object is used for synchronization. The synchronized block ensures that only one thread can execute the code within the block at a time. This prevents concurrent calls to the API endpoints and ensures that they are processed sequentially. The @Scope("singleton") annotation ensures that Spring creates and manages a single instance (singleton) of the Controller throughout the application context. By doing this, we can be confident that there will be only one instance of the LoanController, and the shared lock object will work correctly for synchronization across all requests.
Due to time restrictions, this is implemented in the Loan Controller only.

b)By implementing the @Transactional, gives us multiple benefits, as described above. In our case rollback has been applied only on a specific custom exception. This way is demonstrated the fact that, apart from ensuring that within a failure we will keep data integrity, moreover we can have detailed messaging for logging purposes and for the end user's convenience. In a complete implementation, multiple custom exceptions and detailed handling strategies, depending on the type of the exception would be implemented.
The UI has been modified accordingly and if an exception occurs an error page will be displayed with some details about the error. Due to time restrictions this has been impementend only for the createBook method.

2)
b & c) Since the step a) was skipped, due to time restrictions, the pdf and scheduling has been made on a report that could be exported using the existing schema. For this example we will use the scenario of a report of the books that where loaned last week. Scheduling is set for monday. The following additions where made to the existing implementation:

A)In the repository layer the addition of the findByLoanDateBetween method, that is responsible for quering the database and retrieving the data and returns a List<Loan>.
B)In the service layer, the addition of getLoansLastWeek that implements a basic business logic (calculation of dates, from - to).
C)Added the PdfReportGenerator class. The generateReport method is responsible for constructing the PDF file. The itextpdf dependency was added and used for providing the tools for PDF construction.
D)In the controller layer the generateLoanReport method is added. It is an endpoint handler for generating the loan report on-demand. The @Scheduled(cron = "0 0 12 * * MON") annotation is added, and as such we specify the PDF to be generated on Mondays at 12.
E)Added the @EnableScheduling annotation to the main application class to enable scheduling.
The PDF report named "loan_report.pdf" will be created in the project directory.


-------------------------------------------------------------------------------------
BONUS! : Some Unit tests implemented using Mockito and Junit for the following classes : BookController, LoanController and BookServiceImpl

 	 	


