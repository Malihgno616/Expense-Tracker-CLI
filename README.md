# 💰 Expense Tracker

A command-line **Expense Tracker** application developed in **Java**, based on the [Expense Tracker project from roadmap.sh](<https://roadmap.sh/projects/expense-tracker>).

The application allows users to create, view, update, and delete expenses, as well as generate expense summaries by year or month.

Expense data is automatically persisted in an `expenses.json` file using **Jackson**.

---

## 🎯 Project Challenge

This project was developed based on the **Expense Tracker** challenge from roadmap.sh:

👉 [roadmap.sh — Expense Tracker](<https://roadmap.sh/projects/expense-tracker>)

The challenge focuses on building a simple command-line application for managing personal expenses and practicing:

- Command-line interaction
- CRUD operations
- Data persistence
- File handling
- Input validation
- Data filtering and aggregation

---

## 📋 Features

- ➕ Add a new expense
- ✏️ Update an existing expense
- 🗑️ Delete an expense
- 📋 View all expenses
- 📊 View a summary of all expenses
- 📅 Search expenses by year
- 📅 Search expenses by month
- 🆔 Automatically generate expense IDs
- 📆 Automatically assign the current date to new expenses
- 💾 Automatically persist data to JSON
- 🔄 Automatically load saved expenses when the application starts
- ✅ Confirmation before updating or deleting an expense

---

# 🖥️ Terminal Interface

When the application starts, the user is presented with the main menu:

```
-----------------
Expense Tracker - WELCOME
1.Add an expense
2.Update an expense
3.Delete an expense
4.View all expenses
5.View summary of all expenses
6.View summary of an expense for a specific month (of year)
7.Quit...
Choose an option:
```

---

# 🛠️ Technologies

This project was developed using:

- **Java**
- **Jackson Databind**
- **JSON**
- **Java Collections**
- **Java Streams**
- **Java Scanner**
- **Java Time API**
- **File I/O**
- **Maven/Gradle**

---

## 📦 Jackson

Jackson is used to serialize and deserialize `Expense` objects to and from JSON.

Main Jackson classes used by the project:

```
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
```

The `ObjectMapper` is responsible for converting Java objects into JSON and JSON data back into Java objects.

---

# 📁 Project Structure

```
expense-tracker/
│
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   ├── Main.java
│                   └── Expense.java
│
│
├── expenses.json
├── pom.xml
└── README.md
```

---

# 🚀 Getting Started

## Prerequisites

Before running the application, make sure you have:

- **Java JDK 8 or later**
- **Maven**, if the project uses Maven
- **Jackson Databind**

Check your Java installation:

```
java -version
```

Example:

```
java version "17.0.x"
Java(TM) SE Runtime Environment
Java HotSpot(TM) 64-Bit Server VM
```

---

# 📦 Maven Dependency

If you are using Maven, add Jackson Databind to your `pom.xml`:

```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
```

The version can be adjusted according to your project configuration.

---

# ▶️ Running the Application

Once the project is configured, run the `Main` class:

```
org.example.Main
```

Or, using Maven:

```
mvn compile
mvn exec:java
```

The application will start directly in the terminal.

---

# ➕ Adding an Expense

Select option `1` from the main menu:

```
Choose an option: 1

--description: Lunch
--amount: 35.90
```

A new `Expense` object is created.

The application automatically generates the ID and current date:

```
Expense successfully created!!!
ID: 1
Date: 2026-09-25
Description: Lunch
Amount: $35.90
```

The expense is automatically saved to:

```
expenses.json
```

---

# 🆔 Automatic Expense IDs

Each new expense receives an automatically generated ID.

The `Expense` class uses a static `nextId` field:

```
private static int nextId = 1;
```

When a new expense is created:

```
public Expense(String description, double amount) {
    this.id = nextId++;
    this.date = LocalDate.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    this.description = description;
    this.amount = amount;
}
```

This means the first expense receives ID `1`, the next receives ID `2`, and so on.

When existing expenses are loaded from JSON, the `@JsonCreator` constructor updates `nextId` to ensure that newly created expenses continue from the highest existing ID.

```
if (id >= nextId) {
    nextId = id + 1;
}
```

This prevents newly created expenses from reusing an existing ID after the application is restarted.

---

# 📆 Expense Dates

The date of a new expense is automatically generated using Java's `LocalDate`:

```
LocalDate.now()
```

The date is formatted using:

```
DateTimeFormatter.ofPattern("yyyy-MM-dd")
```

Therefore, dates are stored in the following format:

```
yyyy-MM-dd
```

Example:

```
2026-09-25
```

This format also makes it possible to easily search expenses by year and month using the date string.

---

# 📋 Viewing Expenses

Select option `4`:

```
Choose an option: 4

View all expenses:
--- Expenses ---

ID:2 Date: 2026-09-25 Description: Transport Amount:$20.00

ID:1 Date: 2026-09-25 Description: Lunch Amount:$35.90
```

Expenses are sorted by ID in descending order, with the most recently created IDs displayed first.

---

# ✏️ Updating an Expense

Select option `2`:

```
Choose an option: 2

Update an expense:

--- Expenses ---

ID:2 Date: 2026-09-25 Description: Transport Amount:$20.00

ID:1 Date: 2026-09-25 Description: Lunch Amount:$35.90

Select an expense to Update (id): 1
```

The application displays the selected expense:

```
Selected expense:
ID: 1 Date: 2026-09-25 Description: Lunch Amount: $35.90
```

The user must confirm the operation:

```
Are you sure you want to update expense #1? (y/n): y
```

The new values can then be entered:

```
Enter new values (or press enter/0 to keep current):

--description [Lunch]: Restaurant
--amount [$35.90]: 42.50
```

Result:

```
Expense #1 updated successfully!

New values:
Date: 2026-09-25
Description: Restaurant
Amount: $42.50
```

The original date and ID remain unchanged when an expense is updated.

---

# 🗑️ Deleting an Expense

Select option `3`:

```
Choose an option: 3

Delete an expense:

--- Expenses ---

ID:2 Date: 2026-09-25 Description: Transport Amount:$20.00

ID:1 Date: 2026-09-25 Description: Restaurant Amount:$42.50

Select an expense ID to delete: 2
```

The application asks for confirmation:

```
Are you sure you want to delete expense #2? (y/n): y

Expense with ID 2 deleted successfully!
```

After confirmation, the selected expense is removed from the list and the updated data is saved to `expenses.json`.

---

# 📊 Expense Summary

Select option `5` to display all expenses and calculate the total:

```
Choose an option: 5

View summary of all expenses:

--- Summary of all Expenses ---

ID:1 Date: 2026-09-25 Description: Restaurant Amount:$42.50

ID:2 Date: 2026-09-25 Description: Transport Amount:$20.00

-----------------
Total expenses: $62.50
```

The total is calculated by adding the amount of every expense:

```
double totalExpenses = 0.00;

for (Expense expense : expenses) {
    totalExpenses += expense.getAmount();
}
```

---

# 📅 Period Summary

Option `6` allows users to search for expenses by year or by year and month.

## Search by Year

Enter:

```
2026
```

The application searches for expenses whose date starts with:

```
2026
```

This returns all expenses created during that year.

---

## Search by Month

Enter:

```
2026-09
```

The application searches for expenses whose date starts with:

```
2026-09
```

Example:

```
--- Type year (xxxx) or with month (xxxx-xx): 2026-09

Summary typed: 2026-09

ID:1 Date: 2026-09-25 Description: Restaurant Amount:$42.50

ID:2 Date: 2026-09-25 Description: Transport Amount:$20.00

-------
Period total: (2026-09): $62.50
-------
```

The search is performed using:

```
expense.getDate().startsWith(searchFilter)
```

Because dates use the `yyyy-MM-dd` format, this allows the same method to support both year and month searches.

---

# 💾 Data Persistence

The application stores expenses in:

```
expenses.json
```

Example:

```
[
  {
    "id": 1,
    "date": "2026-09-25",
    "description": "Restaurant",
    "amount": 42.5
  },
  {
    "id": 2,
    "date": "2026-09-25",
    "description": "Transport",
    "amount": 20.0
  }
]
```

---

## Loading Data

When the application starts, the `loadExpenses()` method checks whether `expenses.json` exists.

If the file exists:

```
Loaded 2 expenses from file.
File: /path/to/project/expenses.json
```

The JSON data is converted back into a Java `List<Expense>` using Jackson:

```
expenses = mapper.readValue(
    file,
    new TypeReference<List<Expense>>() {}
);
```

---

## Creating the File

If `expenses.json` does not exist, the application starts with an empty list:

```
📝 No saved data found. Starting fresh!
📁 New file will be created at: /path/to/project/expenses.json
```

The file is created automatically when the first expense is saved.

---

# 🧩 Main Methods

The `Main` class contains the main application operations.

| Method | Responsibility |
| --- | --- |
| `main()` | Initializes and controls the application flow |
| `expenseMenu()` | Displays the main menu |
| `addExpense()` | Adds a new expense |
| `viewAllExpenses()` | Displays all expenses |
| `updateExpense()` | Updates an existing expense |
| `deleteExpense()` | Removes an expense |
| `summaryExpenses()` | Calculates the total of all expenses |
| `summaryMonthOrYear()` | Calculates the total for a specific period |
| `saveExpenses()` | Saves expenses to JSON |
| `loadExpenses()` | Loads expenses from JSON |

---

# 🏗️ Data Model

The application uses an `Expense` class to represent each expense.

## Expense.java

```
package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {

    private int id;
    private static int nextId = 1;
    private String date;
    private String description;
    private double amount;

    public Expense() {}

    @JsonCreator
    public Expense(
            @JsonProperty("id") int id,
            @JsonProperty("date") String date,
            @JsonProperty("description") String description,
            @JsonProperty("amount") double amount
    ) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;

        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public Expense(String description, double amount) {
        this.id = nextId++;
        this.date = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
```

---

## Expense Properties

Each expense contains four main properties:

| Property | Type | Description |
| --- | --- | --- |
| `id` | `int` | Unique expense identifier |
| `date` | `String` | Date when the expense was created |
| `description` | `String` | Description of the expense |
| `amount` | `double` | Monetary value of the expense |

---

## Jackson Integration

The class uses Jackson annotations to control JSON deserialization:

```
@JsonCreator
public Expense(
    @JsonProperty("id") int id,
    @JsonProperty("date") String date,
    @JsonProperty("description") String description,
    @JsonProperty("amount") double amount
)
```

`@JsonCreator` tells Jackson which constructor should be used when creating an `Expense` object from JSON.

`@JsonProperty` maps each JSON property to the corresponding constructor parameter.

For example:

```
{
  "id": 1,
  "date": "2026-09-25",
  "description": "Restaurant",
  "amount": 42.5
}
```

is converted into an `Expense` object with:

```
id          → 1
date        → 2026-09-25
description → Restaurant
amount      → 42.5
```

---

# 🔄 Application Flow

```
             ┌─────────────────────┐
             │   Start Application  │
             └──────────┬──────────┘
                        │
                        ▼
             ┌─────────────────────┐
             │    Load JSON Data   │
             └──────────┬──────────┘
                        │
                        ▼
             ┌─────────────────────┐
             │     Main Menu       │
             └──────────┬──────────┘
                        │
          ┌─────────────┼─────────────┐
          │             │             │
          ▼             ▼             ▼
        Add          Update         Delete
       Expense       Expense        Expense
          │             │             │
          └─────────────┼─────────────┘
                        │
                        ▼
                ┌──────────────┐
                │   Save JSON  │
                └──────────────┘
                        │
                        ▼
                ┌──────────────┐
                │   Main Menu  │
                └──────┬───────┘
                       │
                       ▼
                     Exit
```

---

# 🧪 Complete Usage Example

### 1\. Add an expense

```
Expense Tracker - WELCOME
1.Add an expense
2.Update an expense
3.Delete an expense
4.View all expenses
5.View summary of all expenses
6.View summary of an expense for a specific month (of year)
7.Quit...
Choose an option: 1

--description: Grocery shopping
--amount: 150.75

Expense successfully created!!!
ID: 1
Date: 2026-09-25
Description: Grocery shopping
Amount: $150.75
```

### 2\. View expenses

```
Choose an option: 4

View all expenses:

--- Expenses ---

ID:1 Date: 2026-09-25 Description: Grocery shopping Amount:$150.75
```

### 3\. View the total

```
Choose an option: 5

--- Summary of all Expenses ---

ID:1 Date: 2026-09-25 Description: Grocery shopping Amount:$150.75

-----------------
Total expenses: $150.75
```

---

# 🖼️ Screenshots

Screenshots should be stored inside:

```
docs/images/
```

Recommended screenshots:

```
docs/images/
├── menu.png
├── add-expense.png
├── list-expenses.png
├── update-expense.png
├── delete-expense.png
└── summary.png
```

Recommended sections of the application to capture:

- Main menu
- Adding an expense
- Listing expenses
- Updating an expense
- Deleting an expense
- Viewing the expense summary
- Searching by month/year

Example:

```
![Main menu](docs/images/menu.png)
```

> **Tip:** For more professional documentation, use the same terminal width, font, and visual theme for all screenshots.

---

# ⚠️ Notes

- `expenses.json` is created automatically when data is saved.
- Expenses are loaded from the JSON file when the application starts.
- New expenses automatically receive an ID.
- The ID counter is restored based on existing expenses loaded from JSON.
- New expenses automatically receive the current date.
- Dates use the `yyyy-MM-dd` format.
- The application currently uses `double` for monetary values.
- Period searches use `String.startsWith()`.
- Updating an expense does not change its ID or original date.
- Deleting an expense removes it from the persisted list.

---

# 🔮 Possible Improvements

Future versions could include:

- [ ] More robust input validation
- [ ] Use `BigDecimal` instead of `double` for monetary values
- [ ] Expense categories
- [ ] Category filtering
- [ ] CSV export
- [ ] Monthly budgets
- [ ] Budget warnings
- [ ] Monthly reports
- [ ] Expense charts
- [ ] Graphical user interface
- [ ] Unit tests
- [ ] Integration tests
- [ ] Separate service and repository layers
- [ ] Use `LocalDate` directly in the `Expense` model
- [ ] Improved exception handling
- [ ] Internationalization
- [ ] Better terminal formatting

---

# 📚 Roadmap.sh Challenge

This project was developed based on the **Expense Tracker** challenge from roadmap.sh.

### Challenge

[https://roadmap.sh/projects/expense-tracker](<https://roadmap.sh/projects/expense-tracker>)

The challenge focuses on creating a command-line application for managing personal expenses and practicing programming fundamentals, CRUD operations, and filesystem persistence.

---

# 📄 License

This project is intended for educational, learning, and portfolio purposes.

---

## 👨‍💻 Author

**Expense Tracker**

A Java command-line application for managing personal expenses with JSON-based data persistence.
