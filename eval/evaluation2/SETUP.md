# Setup Guide - Projet Management System

## Les corrections apportées

### 1. Named Queries
- **Problème**: Utilisation incorrecte de `@NamedQuery` (dupliqué au lieu de `@NamedQueries`)
- **Solution**: Consolidation en `@NamedQueries` avec annotations multiples

### 2. Format de date
- **Problème**: Format de date français (Janvier) non configuré
- **Solution**: Ajout de `java.util.Locale.FRENCH` pour l'affichage des mois en français

### 3. Formatage des sorties
- **Problème**: Alignement incorrect des colonnes dans les sorties
- **Solution**: Ajustement des espaces et tabulations pour un meilleur alignement

### 4. Gestion des erreurs
- **Problème**: Pas de gestion appropriée des exceptions
- **Solution**: Ajout d'un bloc `finally` pour fermer proprement HibernateUtil

### 5. Validation des données
- **Problème**: Vérifications nulles manquantes
- **Solution**: Ajout de vérifications `null` dans les méthodes de service

## Installation et configuration

### Étape 1: Télécharger les dépendances

```bash
mvn clean install
```

Cela va télécharger:
- Hibernate Core 5.6.15
- MySQL Connector 8.0.33
- JPA API 2.2

### Étape 2: Configurer MySQL

1. Créer la base de données:
```sql
CREATE DATABASE projet_management;
```

2. Modifier `src/main/resources/hibernate.cfg.xml`:
```xml
<property name="connection.username">VOTRE_USER</property>
<property name="connection.password">VOTRE_PASSWORD</property>
```

### Étape 3: Exécuter le programme

```bash
mvn exec:java -Dexec.mainClass="ma.projet.test.TestProjet"
```

Ou sur Windows:
```cmd
run-test.bat
```

## Résolution des problèmes courants

### Erreur: "package javax.persistence does not exist"
**Solution**: Les dépendances Maven n'ont pas été téléchargées. Exécutez `mvn clean install`.

### Erreur: "Initial SessionFactory creation failed"
**Solutions**:
1. Vérifiez que MySQL est démarré
2. Vérifiez que la base de données `projet_management` existe
3. Vérifiez les credentials dans `hibernate.cfg.xml`

### Erreur: "Could not connect to database"
**Solutions**:
1. Vérifiez que le port MySQL est 3306
2. Vérifiez le firewall
3. Vérifiez les credentials MySQL

## Structure des fichiers

```
src/main/java/ma/projet/
├── classes/           # Entités JPA
│   ├── Projet.java
│   ├── Tache.java
│   ├── Employe.java
│   └── EmployeTache.java
├── dao/              # Interface DAO générique
│   └── IDao.java
├── service/           # Services métier
│   ├── ProjetService.java      # Gestion des projets
│   ├── TacheService.java       # Gestion des tâches
│   ├── EmployeService.java     # Gestion des employés
│   └── EmployeTacheService.java
├── util/             # Utilitaire Hibernate
│   └── HibernateUtil.java
└── test/             # Programme de test
    └── TestProjet.java
```

## Fonctionnalités implémentées

### EmployeService
✅ `afficherTachesRealisees(int employeId)` - Affiche toutes les tâches réalisées par un employé
✅ `afficherProjetsGeres(int employeId)` - Affiche tous les projets gérés par un employé

### ProjetService  
✅ `afficherTachesPlanifiees(int projetId)` - Affiche les tâches planifiées d'un projet
✅ `afficherTachesRealisees(int projetId)` - Affiche les tâches réalisées avec dates réelles

### TacheService
✅ `findByPrixSuperieur(double prix)` - Trouve les tâches dont le prix > montant (requête nommée)
✅ `findByDates(Date dateDebut, Date dateFin)` - Trouve les tâches entre deux dates (requête nommée)

## Exemple de sortie attendue

```
Projet : 4    Nom : Gestion de stock     Date début : 14 Janvier 2013
Liste des tâches:
Num Nom            Date Début Réelle   Date Fin Réelle
12  Analyse        10/02/2013          20/02/2013
13  Conception      10/03/2013          15/03/2013
14  Développement   10/04/2013          25/04/2013
```

## Notes importantes

1. **Premier lancement**: Hibernate créera automatiquement les tables dans la base de données
2. **Configuration**: Modifiez `hibernate.cfg.xml` selon votre configuration MySQL
3. **Test**: Le programme de test crée des données de test pour démonstration

## Support

En cas de problème, vérifiez:
1. Les logs dans la console
2. Que MySQL est en cours d'exécution
3. Que les credentials sont corrects
4. Que les ports ne sont pas bloqués par le firewall
