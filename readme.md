# Employee Management REST API

This repository contains a RESTful API for managing employees. The API allows creating, updating, retrieving, and deleting employee records.

## Endpoints

### Create Employee
- **POST** /api/employee
   - Creates a new employee record.
   - Request body should contain employee details in JSON format.
   - Returns a 201 Created response upon successful creation.

### Find Employee by ID
- **GET** /api/employee/{id}
   - Retrieves an employee record by ID.
   - Returns a 200 OK response with the employee details if found, or a 404 Not Found response if not found.

### Find Active Employees (Paged)
- **GET** /api/employee/active
   - Retrieves a list of active employees with pagination support.
   - Supports optional query parameters for pagination and sorting.
   - Returns a list of active employee records in JSON format.

### Find Active Employees (Pageable)
- **GET** /api/employee
   - Retrieves a list of active employees with pageable support.
   - Supports default page size and sorting configuration.
   - Returns a list of active employee records in JSON format.

### Update Employee
- **PUT** /api/employee/{id}
   - Updates an existing employee record by ID.
   - Request body should contain updated employee details in JSON format.
   - Returns a 200 OK response upon successful update, or a 304 Not Modified response if update fails.

### Delete Employee
- **DELETE** /api/employee/{id}
   - Deletes an employee record by ID.
   - Returns a 200 OK response upon successful deletion, or a 404 Not Found response if the employee is not found.

## Technologies Used
- Java 17
- Spring Framework 3
- Lombok
- Maven