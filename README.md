# Sweet Shop Management System

## Project Overview

The Sweet Shop Management System is a full-stack application designed to streamline and manage the operations of a sweet shop. This project is developed using modern technologies and follows Test-Driven Development (TDD) principles. It challenges skills in API development, database management, frontend implementation, and secure authentication, incorporating modern workflows including selective AI tool usage.

---

## Objectives

Design, build, and test a robust Sweet Shop Management System with:

- Backend API development
- Persistent database integration
- Frontend single-page application
- Secure user authentication and role-based access
- Comprehensive CRUD operations and inventory management
- Modern development practices and testing methodologies

---

## Core Requirements

### 1. Backend API (RESTful)

- **Technology:** Java with Spring Boot
- **Database:** Persistent database required (e.g., PostgreSQL)
- **User Authentication:**  
  - User registration and login  
  - Token-based authentication (JWT) to protect endpoints

- **API Endpoints:**

  - **Auth:**
    - `POST /api/auth/register` — User registration
    - `POST /api/auth/login` — User login

  - **Sweets (Protected Endpoints):**
    - `POST /api/sweets` — Add a new sweet
    - `GET /api/sweets` — List all sweets
    - `GET /api/sweets/search` — Search sweets by name, category, or price range
    - `PUT /api/sweets/:id` — Update sweet details
    - `DELETE /api/sweets/:id` — Delete a sweet (Admin only)

  - **Inventory (Protected Endpoints):**
    - `POST /api/sweets/:id/purchase` — Purchase a sweet and reduce quantity
    - `POST /api/sweets/:id/restock` — Restock a sweet and increase quantity (Admin only)

- **Sweet Attributes:**  
  Unique ID, name, category, price, and quantity in stock

---

### 2. Frontend Application

- **Technology:** React (or any modern SPA framework)
- **Functionality:**
  - User registration and login forms
  - Dashboard/homepage displaying available sweets
  - Search and filter sweets
  - "Purchase" button for each sweet, disabled when quantity is zero
  - Admin-only UI for adding, updating, and deleting sweets
- **Design:**  
  Visually appealing, responsive, and user-friendly interface to enhance user experience

---

## Installation and Setup

1. Clone the repository:  
git clone https://github.com/KaustubhKolhe19/TDD-Kata-Sweet-Shop-Management-System.git

2. Backend setup:  
- Configure PostgreSQL database credentials in the application properties  
- Build and run the backend using Maven or preferred IDE  

3. Frontend setup:  
- Navigate to the frontend folder (e.g., `sweetshop-frontend`)  
- Install dependencies and run the React app  

---

## Testing

- Test-driven development (TDD) approach is employed  
- Unit and integration tests are provided for backend API and frontend components  

---

## My AI Usage

I utilized AI tools selectively for assistance in:

- Brainstorming API endpoint design  
- Drafting commit messages and documentation  
- Troubleshooting Git workflows and submodule handling  

The core design, coding, and testing were independently developed. AI served as a valuable support tool enhancing efficiency without replacing personal expertise.

---
 ![image alt](https://github.com/KaustubhKolhe19/TDD-Kata-Sweet-Shop-Management-System/blob/main/Login.jpg?raw=true)
