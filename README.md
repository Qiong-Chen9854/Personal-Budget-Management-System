# Personal-Budget-Management-System

Create a RESTful API server and a web application to help users manage their personal finances by tracking income, expenses, setting budgets, and viewing financial reports. The app will use a PostgreSQL database, 
JWT for secure authentication, and a simple front-end interface.

## Use cases

User Registration and Authentication
[Complete] As a new user, I need to be able to register with a username and password.
[Complete] As a user, I need to be able to log in and receive an authentication token for secure, subsequent requests.
[Complete] Registered users have an initial balance (e.g., $0.00).
Account Management
[Incomplete] As a user, I need to be able to view my current account balance.
Income Management
[Incomplete] As a user, I need to be able to add new income entries, including details like date, amount, and source.
[Incomplete] As a user, I need to view a list of all my income entries.
Expense Management
[Incomplete] As a user, I need to be able to add expense entries with details like date, amount, and category.
[Incomplete] As a user, I need to view a list of all my expenses.
Budget Management
[Incomplete] As a user, I need to set monthly budgets.
[Incomplete] As a user, I want to compare my budget to my actual spending.
Notifications and Alerts
[Incomplete] As a user, I need to receive notifications when I am close to my budget limit.
[Incomplete] As a user, I need the ability to set up recurring income or expense entries.
Financial Reporting and Exporting
[Incomplete] As a user, I want to view summary reports (e.g., monthly income vs. expenses, spending by category).
[Incomplete] As a user, I need to export my financial data to a CSV file for offline access.

## Sample screens
Sample Screens
Use case 2: View Account Balance
Your current account balance is: $1000.00
Use case 3: Add Income Entry
makefile
Copy code
Date: [mm/dd/yyyy]
Amount: [$]
Source: [e.g., salary, freelance]

Use case 4: Add Expense Entry
makefile
Copy code
Date: [mm/dd/yyyy]
Amount: [$]
Category: [e.g., food, entertainment, bills]

Use case 5: Budget vs Actual Spending
markdown
Copy code
-------------------------------------------
Category         Budgeted         Actual
-------------------------------------------
Food               $200             $180
Entertainment      $100             $150
Utilities          $250             $200
-------------------------------------------

Use case 7: Summary Report
Income vs Expenses Summary for [month/year]
Spending by Category
