# Inventory Management API

## 🇧🇷 Descrição
API REST para gerenciamento de estoque desenvolvida com Spring Boot.

O sistema permite cadastro de usuários, autenticação com JWT e controle de acesso por níveis (ADMIN e USER), além de operações CRUD para produtos e registro de transações de entrada e saída.

## 🇺🇸 Description
REST API for inventory management built with Spring Boot.

The system supports user registration, JWT authentication, role-based access control (ADMIN and USER), as well as CRUD operations for products and transaction tracking.

## 🚀 Tecnologias | Technologies
- Java
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- H2 Database

## 🔐 Funcionalidades | Features
- Autenticação e registro de usuários
- Segurança com JWT
- Controle de acesso por roles
- CRUD de produtos
- Registro de transações (entrada/saída)

## 📌 Endpoints principais

POST /auth/login  
POST /auth/registrar  

GET /api/produtos  
POST /api/produtos  
PUT /api/produtos/{id}  
DELETE /api/produtos/{id}  

GET /api/transacoes  
POST /api/transacoes  
DELETE /api/transacoes/{id}  