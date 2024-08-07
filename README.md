Problem Statement :

Description:
Create a CRUD application (Spring Boot API and Angular 15 UI)
1. We have Employees and Departments. We can CRUD both.
2. Any Employee can belong to 0-N Departments
3. All Employees must belong to at least a read-only Department called "Organisation"
4. Hardcode nothing about these rules and relationships.

Tech stack:
Spring boot 3.latest
JDK17
Angular 15 + Bootstrap CSS
Maven
H2 in-mem DB with Employees and Departments etc

Day 1:
DB design. 
Analyse reqs and produce the schema.sql file. Schema should have ONLY tables. NO triggers, functions, constraint checks etc. You are capturing business rules, you are capturing attributes for your data only. You will not be implementing functionality in the DB.

Days 2-4:
API implementation.
Production level code, including code quality, testing, best practices, etc.

Day 5:
UI implementation.
Familiarisation with Angular 15, and creation of basic CRUD UI.
