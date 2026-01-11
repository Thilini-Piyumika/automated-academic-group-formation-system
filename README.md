
Where:
- `W1 + W2 + W3 + W4 = 1`
- All weights are configurable by the lecturer

---

## 🏗 Data Structures Used
- **Queue**  
  Used to process students sequentially based on attendance eligibility and to separate eligible and review students.

- **Binary Search Tree (BST)**  
  Used to rank students based on readiness scores and retrieve them in sorted order through traversal.

- **Linked List**  
  Used to dynamically construct student groups and store previous group histories to avoid repetitive group formation.

---

## 🔁 Group Repetition Avoidance
Previous group histories are stored using linked lists. During new group formation, each group is validated against historical records to ensure that repeated student combinations do not exceed a lecturer-defined threshold. If repetition is detected, the group is modified before finalization.

---

## 🛠 System Workflow
1. Lecturer selects batch and relevant module(s)
2. Attendance eligibility is checked against the threshold
3. Readiness scores are calculated for eligible students
4. Students are inserted into a Binary Search Tree
5. BST traversal produces a ranked student list
6. Students are categorized into fit levels
7. Groups are formed based on the selected strategy
8. Repetition checks are applied and groups finalized
9. Reports are generated and exported

---

## 📤 Outputs
- Final list of student groups
- Group composition summary
- Readiness scores (lecturer view)
- Excluded student list (attendance-based)
- Exportable reports (Excel / PDF)

---

## 🚀 Technologies
- **Language:** Java  
- **Paradigm:** Object-Oriented Programming  
- **Data Structures:** Queue, Binary Search Tree, Linked List  
- **Build Tool:** Maven  
- **Framework (Backend):** Spring Boot  

---


