# MDD - Monde de Dév

## Description

MDD (Monde de Dév) est un réseau social dédié aux développeurs. Cette application permet aux développeurs de :
- S'abonner à des thèmes de programmation (JavaScript, Java, Python, Web3, etc.)
- Consulter un fil d'actualité personnalisé
- Créer et publier des articles
- Commenter les articles
- Gérer leur profil utilisateur

## Technologies utilisées

### Backend
- **Java 17**
- **Spring Boot** (Spring Security, Spring Data JPA)
- **MySQL** 8.0
- **JWT** pour l'authentification
- **Maven** pour la gestion des dépendances

### Frontend
- **Angular 14.1.3**
- **TypeScript**
- **Angular Material** pour l'interface utilisateur
- **Node.js** et **npm**

## Prérequis

Avant d'installer l'application, assurez-vous d'avoir :
- **Java 17** ou supérieur
- **Node.js 16** ou supérieur
- **MySQL 8.0** ou supérieur
- **Git**

## Installation

### 1. Cloner le repository
```bash
git clone https://github.com/AnhVaccari/Developpez-une-application-full-stack-complete.git
cd P6-Full-Stack-reseau-dev
```

### 2. Configuration de la base de données
Créer une base de données MySQL :
```sql
CREATE DATABASE mdd_db;
```

### 3. Installation du Backend
```bash
cd back
./mvnw clean install
```

### 4. Installation du Frontend
```bash
cd front
npm install
```

## Lancement de l'application

### 1. Démarrer le Backend
```bash
cd back
./mvnw spring-boot:run
```
Le backend sera accessible sur `http://localhost:8080`

### 2. Démarrer le Frontend
```bash
cd front
ng serve
```
Le frontend sera accessible sur `http://localhost:4200`

## Fonctionnalités

### Authentification
- Inscription avec email, nom d'utilisateur et mot de passe
- Connexion avec email et mot de passe
- Persistance de la session avec JWT

### Gestion des utilisateurs
- Consultation du profil utilisateur
- Modification du profil (email, nom d'utilisateur, mot de passe)

### Gestion des abonnements
- Consultation de tous les thèmes disponibles
- Abonnement/désabonnement aux thèmes

### Gestion des articles
- Fil d'actualité chronologique personnalisé
- Création d'articles avec choix du thème
- Consultation détaillée des articles
- Système de commentaires

## Architecture

L'application suit une architecture en couches :
- **Controller** : Gestion des endpoints REST
- **Service** : Logique métier
- **Repository** : Accès aux données

La sécurité est assurée par Spring Security avec JWT pour l'authentification stateless.

