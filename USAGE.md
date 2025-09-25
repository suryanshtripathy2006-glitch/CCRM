# Usage

## Sample Commands
- Run the app and follow menu.
- Import: Choose 4, then 1 (imports from test-data/*.csv).
- Export: Choose 4, then 2 (exports to data/*.csv).
- Add student: Choose 1, enter details (ID, regNo, name, email, status)—saves to data/students.csv.
- Add course: Choose 2, enter details (code, title, credits, semester, department)—saves to data/courses.csv.
- Enroll/grade: Choose 3, enter student ID, course code, marks—saves to data/enrollments.csv.

## Data Files
- students.csv: id,regNo,fullName,email,status,registrationDate
- courses.csv: code,title,credits,semester,department
- enrollments.csv: studentId,courseCode,marks,enrollmentDate

Data persists in 'data' folder across runs (loads on startup if exists, else from test-data).