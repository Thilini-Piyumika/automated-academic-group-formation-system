# 🎓 Automated Academic Group Formation System
 
A full-stack intelligent academic grouping system powered by **Data Structures and Algorithms (DSA)** principles.
 
---
 
## 📌 Overview
 
The **Automated Academic Group Formation System** is a full-stack academic decision-support tool that intelligently generates student groups using:
 
- Weighted academic modeling  
- Attendance policy enforcement  
- Strategy-based grouping algorithms  
 
Unlike traditional random or manual grouping methods, this system ensures:
 
- ✅ Fairness  
- ✅ Transparency  
- ✅ Scalability  
- ✅ Institutional Policy Compliance  
 
It applies **core Data Structures and Algorithms concepts** in a real-world educational environment.
 
---
 
## 🎯 Core Problem Solved
 
Traditional group formation methods often involve:
 
- ❌ Random LMS allocation (no academic consideration)  
- ❌ Manual GPA grouping (time-consuming & biased)  
- ❌ Complex clustering models (low interpretability)  
 
This system provides a **structured, explainable, and flexible solution** for academic institutions.
 
---
 
## 🧠 Key Features
 
- ✔ Excel-based student data upload  
- ✔ Weighted Readiness Score calculation  
- ✔ Attendance threshold filtering  
- ✔ Binary Search Tree-based ranking  
- ✔ Three configurable grouping strategies  
- ✔ Automatic Review Group generation  
- ✔ Transparent Excel export with model details  
- ✔ Interactive React dashboard  
 
---
 
## 🧮 Readiness Score Model
 
### 1️⃣ Attendance Normalization
 

Attendance_GPA = (Attendance / 100) × 4
 
---
 
## 🧮 Weighted Readiness Score
 
### 📌 Formula
 
```math
ReadinessScore =
((Attendance_GPA × W1) +
(CurrentGPA × W2) +
(PreviousGPA × W3))
/
(W1 + W2 + W3)
```
Where:
 
- **Attendance_GPA** = (Attendance / 100) × 4  
- **W1** = Attendance Weight  
- **W2** = Current GPA Weight  
- **W3** = Previous GPA Weight  
 
This weighted average model ensures configurable academic prioritization based on institutional policy.
 
---
 
## 📊 Classification
 
Students are categorized using GPA-style thresholds:
 
- ⭐ **BEST** (≥ 3.5)  
- ⚖ **AVERAGE** (2.5 – 3.49)  
- 🔻 **NEEDS_SUPPORT** (< 2.5)  
 
This classification mirrors the **normal distribution (bell curve)** commonly observed in academic datasets.
 
---
 
## 🏗 System Architecture
 
### 🔹 Backend
 
- Java  
- Spring Boot (REST API)  
- Apache POI (Excel processing)  
- LinkedList (Dynamic group storage)  
- Binary Search Tree (Ordered ranking)  
 
### 🔹 Frontend
 
- React.js  
- JavaScript (Fetch API)  
- CSS  
 
### 🔹 API Communication
 
- RESTful architecture  
- Postman testing  
 
---
 
## ⚙ Algorithm Design
 
### 1️⃣ Queue-Based Attendance Filtering
 
Students are separated into:
 
- **Eligible Queue**
- **Review Queue**
 
FIFO processing ensures fairness and transparency.
 
**Time Complexity:** `O(n)`
 
---
 
### 2️⃣ BST-Based Ranking
 
- Students inserted into a **Binary Search Tree**
- Readiness Score used as key
- Reverse in-order traversal produces descending ranking
 
**Average Time Complexity:** `O(n log n)`
 
---
 
### 3️⃣ Strategy-Based Group Formation
 
#### 🔹 Strategy 1 – Best–Best (Homogeneous)
Sequential grouping from highest scores.
 
#### 🔹 Strategy 2 – Best–Average (Semi-Balanced)
BEST + AVG → BEST + NEEDS_SUPPORT → AVG + NEEDS_SUPPORT → Fill remaining.
 
#### 🔹 Strategy 3 – Fully Mixed (Balanced)
1 BEST + 1 AVERAGE + 1 NEEDS_SUPPORT per group.
 
**Time Complexity:** `O(n)`
 
---
 
## 📊 Computational Complexity
 
| Operation                | Complexity    |
|--------------------------|--------------|
| Score Calculation        | O(n)         |
| Attendance Filtering     | O(n)         |
| Sorting (BST / TimSort)  | O(n log n)   |
| Group Formation          | O(n)         |
 
### ✅ Overall System Complexity: **O(n log n)**
 
Efficient and scalable for large academic cohorts.
 
---
 
## 📂 Project Structure
 
academic-group-formation-tool/   → Spring Boot Backend  
grouping-extention/              → React Frontend  
HNDSE25_1F-sample-data.xlsx      → Sample Dataset  
start-backend.bat                → Quick Backend Start Script  
README.md                        → Project Documentation  
 
---
 
## 🚀 How to Run
 
### 🖥 Option 1 – Using BAT File (Windows)
 
Double-click:
 
```bash
start-backend.bat
```
 
Backend runs at:
 
```
http://localhost:8080
```
 
---
 
### 🧠 Option 2 – Manual Backend Run
 
1. Open `academic-group-formation-tool` in IntelliJ IDEA  
2. Run:
 
```bash
AcademicGroupFormationToolApplication
```
 
Backend will start on:
 
```
http://localhost:8080
```
 
---
 
### 🌐 Run Frontend
 
Open a new terminal and run:
 
```bash
cd grouping-extention
npm install
npm start
```
 
Frontend runs at:
 
```
http://localhost:3000
```
 
> ⚠ Make sure the backend is running before starting the frontend.
 
---
 
## 📊 Sample Dataset
 
Included file:
 
```
HNDSE25_1F-sample-data.xlsx
```
 
Use this dataset to test:
 
- Group size configuration  
- Attendance threshold filtering  
- Weight parameter adjustments (W1, W2, W3)  
- Strategy selection (Homogeneous / Semi-Balanced / Mixed)  
- Excel export functionality  


