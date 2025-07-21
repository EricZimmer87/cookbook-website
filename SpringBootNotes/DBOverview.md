# Overview

## Model - DB Tables:

Basically a framework for each table in the database.

## Service - Business Logic:

Handles all the logic for interacting with the database and additional back-end logic. Acts as an intermediary between the controller and the repository.

## Repository - Talks to the Database:

Focuses purely on interacting with the database. This is where the queries go.

## Controller - API Endpoints:

Handles HTTP requests and defines routes (or endpoints). It basically acts as the router.
