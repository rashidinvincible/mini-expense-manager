# 🧠 Short Design Note – Mini Expense Manager

- The backend uses rule-based categorization with a static `vendor-to-category` mapping in the service layer.
- Category is auto-assigned based on vendor name during expense creation or CSV upload.
- Anomalies are detected by comparing new expense amounts to the average per category; anything over 3× the average is flagged.
- The data model is simple: a single `Expense` entity tracks date, amount, vendor, description, category, and anomaly status.
- CSV file uploads are parsed server-side, and each row is treated like a new expense entry with full processing.
- The application is designed for extendability — future upgrades could include user authentication, frontend UI, and a dynamic rule engine.
