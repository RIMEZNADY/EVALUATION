# Projet de Gestion du Temps - Système de Suivi des Projets

## Description
Ce projet permet à un bureau d'études de suivre le temps passé sur les projets et de calculer les coûts globaux.

## Structure du Projet

```
src/main/java/ma/projet/
├── classes/          # Entités JPA
│   ├── Projet.java
│   ├── Tache.java
│   ├── Employe.java
│   └── EmployeTache.java
├── dao/              # Interface DAO
│   └── IDao.java
├── service/           # Services métier
│   ├── ProjetService.java
│   ├── TacheService.java
│   ├── EmployeService.java
│   └── EmployeTacheService.java
├── util/            # Utilitaires
│   └── HibernateUtil.java
└── test/            # Programmes de test
    └── TestProjet.java
```

## Configuration

### 1. Base de données
Créez une base de données MySQL nommée `projet_management` :
```sql
CREATE DATABASE projet_management;
```

### 2. Fichier de configuration
Configurez les paramètres de connexion dans `src/main/resources/hibernate.cfg.xml` :
- URL de connexion
- Username
- Password

## Installation et Exécution

### Prérequis
- Java JDK 1.8+
- Maven
- MySQL Server

### Installation
```bash
mvn clean install
```

### Exécution du test
```bash
mvn exec:java -Dexec.mainClass="ma.projet.test.TestProjet"
```

## Fonctionnalités

### Services implémentés

#### EmployeService
- ✅ Afficher la liste des tâches réalisées par un employé
- ✅ Afficher la liste des projets gérés par un employé

#### ProjetService
- ✅ Afficher la liste des tâches planifiées pour un projet
- ✅ Afficher la liste des tâches réalisées avec les dates réelles

#### TacheService
- ✅ Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
- ✅ Afficher les tâches réalisées entre deux dates (requête nommée)

## Exemple de sortie attendue

```
Projet : 4    Nom : Gestion de stock     Date début : 14 Janvier 2013
Liste des tâches:
Num Nom            Date Début Réelle   Date Fin Réelle
12  Analyse        10/02/2013          20/02/2013
13  Conception     10/03/2013          15/03/2013
14  Développement  10/04/2013          25/04/2013
```
