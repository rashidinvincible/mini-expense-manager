# Mini Expense Manager (Backend)

A simple Spring Boot backend for managing daily expenses with support for:
- Manual entry
- CSV upload
- Rule-based categorization
- Anomaly detection
- Dashboard summaries

---

## 📦 Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## ⚙️ Setup Instructions

1. **Clone the repository**  
   ```bash
   git clone https://github.com/rashidinvincible/mini-expense-manager.git
   cd mini-expense-manager
Configure PostgreSQL

Ensure PostgreSQL is installed and running.

Create a database named expense_db.

Default username: postgres
Default password: yourpassword

Update application.properties (if needed)
Located at: src/main/resources/application.properties

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Build and run the app
You can run it from your IDE or with Maven:

bash
Copy code
mvn spring-boot:run
🔑 Features
✅ Add Expense (POST /api/expenses)
Fields: date, amount, vendorName, description

Automatically assigns category using vendor-to-category mapping.

✅ Get All Expenses (GET /api/expenses)
✅ Get Anomalies (GET /api/expenses/anomalies)
Flags expenses where amount > 3 × average for its category.

✅ Upload CSV (POST /api/expenses/upload-csv)
Upload multiple expenses from a CSV file.

📁 Sample CSV Format
date,amount,vendorName,description
2024-06-01,150,Swiggy,Lunch
2024-06-02,500,Amazon,Shopping
✅ Assumptions
Vendor-to-category mapping is hardcoded in ExpenseService.

CSV file is expected to have headers.

Category field is optional while adding expense — it is auto-set using mapping.

Only backend is implemented; frontend (React + TypeScript) can be added separately.

🙌 Author
Rashid Invincible

🧪 API Testing
Use Postman or any API client to test:

POST /api/expenses to add an expense

GET /api/expenses to retrieve all

GET /api/expenses/anomalies to fetch flagged entries

POST /api/expenses/upload-csv to upload a CSV file
