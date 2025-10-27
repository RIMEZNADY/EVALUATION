package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

public class TestProjet {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Create services
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeService employeService = new EmployeService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            
            // Test data
            System.out.println("=== Creating Test Data ===\n");
            
            // Create Projets
            Date dateDebutProjet1 = sdf.parse("14/01/2013");
            Date dateFinProjet1 = sdf.parse("25/04/2013");
            Projet projet1 = new Projet("Gestion de stock", dateDebutProjet1, dateFinProjet1);
            projetService.create(projet1);
            
            Date dateDebutProjet2 = sdf.parse("01/01/2013");
            Date dateFinProjet2 = sdf.parse("30/06/2013");
            Projet projet2 = new Projet("Système RH", dateDebutProjet2, dateFinProjet2);
            projetService.create(projet2);
            
            // Create Employes
            Employe employe1 = new Employe("EL ALAMI", "Ahmed", "ahmed.elalami@example.com");
            employeService.create(employe1);
            
            Employe employe2 = new Employe("ALAOUI", "Fatima", "fatima.alaoui@example.com");
            employeService.create(employe2);
            
            // Create Taches for Projet 1 (Gestion de stock)
            Date dateDebutTache1 = sdf.parse("10/02/2013");
            Date dateFinTache1 = sdf.parse("20/02/2013");
            Tache tache1 = new Tache("Analyse", null, null, dateDebutTache1, dateFinTache1, 5000);
            tache1.setProjet(projet1);
            tacheService.create(tache1);
            
            Date dateDebutTache2 = sdf.parse("10/03/2013");
            Date dateFinTache2 = sdf.parse("15/03/2013");
            Tache tache2 = new Tache("Conception", null, null, dateDebutTache2, dateFinTache2, 3000);
            tache2.setProjet(projet1);
            tacheService.create(tache2);
            
            Date dateDebutTache3 = sdf.parse("10/04/2013");
            Date dateFinTache3 = sdf.parse("25/04/2013");
            Tache tache3 = new Tache("Développement", null, null, dateDebutTache3, dateFinTache3, 8000);
            tache3.setProjet(projet1);
            tacheService.create(tache3);
            
            // Create Taches for Projet 2
            Date dateDebutTache4 = sdf.parse("05/02/2013");
            Date dateFinTache4 = sdf.parse("25/02/2013");
            Tache tache4 = new Tache("Analyse RH", null, null, dateDebutTache4, dateFinTache4, 2000);
            tache4.setProjet(projet2);
            tacheService.create(tache4);
            
            Date dateDebutTache5 = sdf.parse("01/03/2013");
            Date dateFinTache5 = sdf.parse("30/03/2013");
            Tache tache5 = new Tache("Développement RH", null, null, dateDebutTache5, dateFinTache5, 12000);
            tache5.setProjet(projet2);
            tacheService.create(tache5);
            
            // Create EmployeTache associations
            EmployeTache et1 = new EmployeTache(dateDebutTache1, dateFinTache1, employe1, tache1);
            employeTacheService.create(et1);
            
            EmployeTache et2 = new EmployeTache(dateDebutTache2, dateFinTache2, employe1, tache2);
            employeTacheService.create(et2);
            
            EmployeTache et3 = new EmployeTache(dateDebutTache3, dateFinTache3, employe2, tache3);
            employeTacheService.create(et3);
            
            EmployeTache et4 = new EmployeTache(dateDebutTache4, dateFinTache4, employe1, tache4);
            employeTacheService.create(et4);
            
            EmployeTache et5 = new EmployeTache(dateDebutTache5, dateFinTache5, employe2, tache5);
            employeTacheService.create(et5);
            
            System.out.println("Test data created successfully!\n");
            
            // Test 1: Afficher les tâches réalisées pour un projet (exemple donné)
            System.out.println("=== TEST 1: Afficher les tâches réalisées pour un projet ===\n");
            projetService.afficherTachesRealisees(projet1.getId());
            
            // Test 2: Afficher la liste des tâches planifiées pour un projet
            System.out.println("\n=== TEST 2: Afficher les tâches planifiées pour un projet ===\n");
            projetService.afficherTachesPlanifiees(projet1.getId());
            
            // Test 3: Afficher les tâches réalisées par un employé
            System.out.println("\n=== TEST 3: Afficher les tâches réalisées par un employé ===\n");
            employeService.afficherTachesRealisees(employe1.getId());
            
            // Test 4: Afficher les projets gérés par un employé
            System.out.println("\n=== TEST 4: Afficher les projets gérés par un employé ===\n");
            employeService.afficherProjetsGeres(employe1.getId());
            
            // Test 5: Afficher les tâches dont le prix est supérieur à 1000
            System.out.println("\n=== TEST 5: Afficher les tâches dont le prix > 1000 DH ===\n");
            List<Tache> tachesPrixElev = tacheService.findByPrixSuperieur(1000);
            System.out.println("Num\tNom\t\t\tPrix");
            for (Tache t : tachesPrixElev) {
                System.out.println(t.getId() + "\t" + t.getNom() + "\t\t" + t.getPrix());
            }
            
            // Test 6: Afficher les tâches réalisées entre deux dates
            System.out.println("\n=== TEST 6: Afficher les tâches réalisées entre deux dates ===\n");
            Date debut = sdf.parse("01/03/2013");
            Date fin = sdf.parse("31/03/2013");
            List<Tache> tachesEntreDates = tacheService.findByDates(debut, fin);
            System.out.println("Num\tNom\t\t\tDate Début Réelle\tDate Fin Réelle");
            for (Tache t : tachesEntreDates) {
                System.out.println(t.getId() + "\t" + t.getNom() + "\t\t" + 
                    (t.getDateDebutReelle() != null ? sdf.format(t.getDateDebutReelle()) : "N/A") + "\t\t" + 
                    (t.getDateFinReelle() != null ? sdf.format(t.getDateFinReelle()) : "N/A"));
            }
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                HibernateUtil.close();
            } catch (Exception ex) {
                System.err.println("Error closing Hibernate: " + ex.getMessage());
            }
        }
    }
}
