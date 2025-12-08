1. ✅ Brian MEDIUM: Inside the following file `src/main/java/com/example/application/database/PostgreSQLDatabaseControler.java`, Read every value in AnsweredSurveyEveningTableRow and convert it to answer<> and place it in the answers array.
Needed for submitting surveys without error.
While doing this, collumns need to be added to the AnsweredSurveyRow class.

2. ✅ (Kinda, CitizenRowToCitizen was just removed) MEDIUM: In the same file as above: `src/main/java/com/example/application/database/PostgreSQLDatabaseControler.java`
There is a function called CitizenRowToCitizen. Implement CitizenToCitizenRow.
This may be easier to do after 1.

3. Patrick EASY-MEDIUM: In `src/main/java/com/example/application/views/CitizenView.java` it interacts with the database in a very basic way. Fully integrate and use the database and remove all mock data!

4. EASY-MEDIUM: Like how `src/main/java/com/example/application/views/CitizenView.java` uses the database, please also add the database to the `/home/brian/proj/P3-project/P3-project/src/main/java/com/example/application/views/DashboardView.java` view.

5. Alaxander MEDIUM AND LOOONG: Test as many methods as possible, using vaadin's testing system.

6. MEDIUM: make testing implementations of the Model interface. This requires researching how you test via dependency injection in vaadin.

7. MEDIUM-HARD: Like how `src/main/java/com/example/application/views/CitizenView.java` uses the database, please also add the database to the survey view. This heavily relies on 1. and 2. being done.

8. Implement observer pattern in model.

9. Jonas EASY: Bedre UI for citizenview

10. BRIAN EASY "Hvor mange genstande har du ca. drukket i løbet af dagen?", "1", "2", "3"" is correct order, "when" first, then combobox. Some aren't, fix those.
Can embed @Embedable classes instead of replication.
drawUI needs to get listeners.
