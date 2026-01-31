# 🎓 Online Exam Management System

A full-featured **Online Exam Management System** built with **Spring Boot**, **PostgreSQL**, and **Thymeleaf**, designed to manage courses, users, and online exams with **role-based access control**.

This system supports **Admins, Teachers, and Students**, each with clearly defined permissions, and enables secure exam creation, participation, and grading.

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot
- **Security:** Spring Security (Role-Based Access Control)
- **Database:** PostgreSQL
- **Frontend:** Thymeleaf, HTML, CSS, Bootstrap
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven

---

## 👥 User Roles & Permissions

### 🔑 Admin
- Approve or reject user registrations
- Manage users (edit details, change roles)
- Search & filter users (by role, name, etc.)
- Create and manage courses
- Assign **one teacher** to each course
- Enroll students into courses
- View course participants (teachers & students)

### 👨‍🏫 Teacher
- View assigned courses
- Create, edit, and delete exams
- Add questions to exams:
  - Multiple-choice questions
  - Descriptive (essay) questions
- Use and manage a **Question Bank**
- Assign default scores per question (per exam)
- Automatically grade multiple-choice questions
- Manually grade descriptive questions
- View student exam results

### 👨‍🎓 Student
- Register and wait for admin approval
- View enrolled courses
- Participate in available exams
- Submit exams within the defined time limit
- View exam status and results

---

## ✨ Features

### 🔐 Authentication & Authorization
- User registration for **Students** and **Teachers**
- Admin approval required after registration
- Secure login with role-based access control

---

### 📚 Course Management
- Create and manage courses (Admin)
- Each course has:
  - Title
  - Unique identifier
  - Start & end dates
- Assign one teacher per course
- Enroll multiple students per course
- View course participants

---

### 📝 Exam Management
- Teachers can create exams per course
- Exam configuration includes:
  - Title
  - Description
  - Duration (in minutes)
- Exams can be edited or deleted before use

---

### ❓ Question Management & Question Bank
- Two question types:
  - **Multiple Choice**
    - Unlimited options
    - One correct answer
  - **Descriptive**
    - Free-text answers
- Each question has:
  - Mandatory short title
  - Question text
- All created questions are **automatically saved** in the teacher’s Question Bank
- Questions can be reused across exams in the same course

---

### 🎯 Scoring System
- Teachers assign **default scores per question (per exam)**
- Total exam score is calculated automatically
- Same question can have **different scores in different exams**

---

### ⏱️ Exam Execution
- Countdown timer during exams
- Students can:
  - Navigate between questions
  - Change answers before submission
- Exam auto-submits when time expires
- No negative marking
- Unanswered questions receive zero score
- Students **cannot reattempt** an exam

---

### 🔄 Connection Recovery
- Student answers are saved **temporarily during the exam**
- If connection drops:
  - Student can resume the exam within the remaining time
- If exam time expires:
  - Exam is automatically finalized
  - Further access is denied

---

### 📊 Grading & Results
- Automatic grading for multiple-choice questions
- Manual grading for descriptive answers
- Teachers can:
  - View exam participants
  - Assign scores for descriptive answers
- Validation prevents assigning scores higher than the question’s maximum

---

## 🗄️ Database Design
- PostgreSQL with relational mappings
- Core entities:
  - User
  - Role
  - Course
  - Exam
  - Question
  - ExamAttempt
  - Answer
  - QuestionBank

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Setup Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/online-exam-system.git
