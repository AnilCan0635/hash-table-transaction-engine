# ⚡ Custom Hash Table & Transaction Indexing Engine

A high-performance, from-scratch **Hash Table** data structure implementation in **Java**, designed for indexing and managing large volumes of financial transaction records without relying on standard library utility collections (`java.util.HashMap`).

---

## 📌 Features

- **Custom Hash Table Architecture:** Implements low-level bucket management, hash hashing functions, and collision resolution strategies (open addressing / chaining).
- **Dynamic Resizing & Load Factor Management:** Monitors threshold capacity and triggers table re-hashing to maintain $O(1)$ average-case search/insertion complexity.
- **Transaction Management:** Ingests and processes financial record models (`Transaction`, `Records`) through a central processing layer (`Management`).
- **Zero Framework Overhead:** Pure algorithmic implementation built entirely on core Java primitives and arrays.

---

## 🏛️ Project Structure

```text
custom-hashtable-indexer/
├── src/
│   ├── HashTable.java        # Core hash table and collision resolution logic
│   ├── Management.java       # Record transaction processing controller
│   ├── Records.java          # Aggregated batch record entity
│   ├── Transaction.java      # Single transaction data model
│   └── Main.java             # Entry point and benchmark runner
├── Report.pdf                # Performance benchmark and complexity analysis
├── .gitignore
└── README.md
```

---

## 🚀 Getting Started

Compile and run via command line:

```bash
javac -d bin src/*.java
java -cp bin Main
```