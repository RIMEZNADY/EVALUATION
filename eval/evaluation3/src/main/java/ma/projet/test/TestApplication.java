package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.FemmeService;
import ma.projet.dao.HommeService;
import ma.projet.dao.MariageService;
import ma.projet.util.HibernateUtil;

public class TestApplication {
    
    public static void main(String[] args) {
        try {
            // Initialize services
            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();
            
            // SimpleDateFormat pour les dates
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // Créer les hommes
            System.out.println("========== CRÉATION DES HOMMES ==========\n");
            List<Homme> hommes = new ArrayList<>();
            
            hommes.add(createHomme("SAFI", "SAID", "15/03/1965", "Casablanca", hommeService));
            hommes.add(createHomme("ALAOUI", "MOHAMED", "20/05/1970", "Rabat", hommeService));
            hommes.add(createHomme("AMRANI", "ALI", "10/08/1968", "Fès", hommeService));
            hommes.add(createHomme("BENANI", "HASSAN", "05/11/1972", "Tanger", hommeService));
            hommes.add(createHomme("ELAKRIM", "KARIM", "25/12/1967", "Marrakech", hommeService));
            
            // Créer les femmes
            System.out.println("\n========== CRÉATION DES FEMMES ==========\n");
            List<Femme> femmes = new ArrayList<>();
            
            femmes.add(createFemme("RAMI", "SALIMA", "22/01/1968", "Casablanca", femmeService));
            femmes.add(createFemme("ALI", "AMAL", "18/04/1972", "Rabat", femmeService));
            femmes.add(createFemme("ALAOUI", "WAFA", "12/07/1975", "Fès", femmeService));
            femmes.add(createFemme("ALAMI", "KARIMA", "30/09/1966", "Tanger", femmeService));
            femmes.add(createFemme("BENANI", "FATIMA", "15/02/1970", "Marrakech", femmeService));
            femmes.add(createFemme("ELHAJ", "NOURA", "08/06/1973", "Casablanca", femmeService));
            femmes.add(createFemme("IDRISSI", "SAMIRA", "25/10/1969", "Rabat", femmeService));
            femmes.add(createFemme("MADANI", "RACHIDA", "14/03/1971", "Fès", femmeService));
            femmes.add(createFemme("SOUSSI", "ZINEB", "05/08/1974", "Tanger", femmeService));
            femmes.add(createFemme("TAHIRI", "NADIA", "20/11/1970", "Marrakech", femmeService));
            
            // Créer les mariages
            System.out.println("\n========== CRÉATION DES MARIAGES ==========\n");
            createMariages(hommes, femmes, mariageService);
            
            System.out.println("\n\n========== TESTS DES FONCTIONNALITÉS ==========\n");
            
            // Test 1: Afficher la liste des femmes
            System.out.println("--- Test 1: Liste des femmes ---");
            List<Femme> toutesFemmes = femmeService.findAll();
            for (Femme f : toutesFemmes) {
                System.out.println("ID: " + f.getId() + " - " + f.getNomComplet() + 
                    " - Né le: " + sdf.format(f.getDateNaissance()));
            }
            
            // Test 2: Afficher la femme la plus âgée
            System.out.println("\n--- Test 2: Femme la plus âgée ---");
            Femme femmeAged = femmeService.getFemmeLaPlusAged();
            if (femmeAged != null) {
                System.out.println(femmeAged.getNomComplet() + " - Né le: " + 
                    sdf.format(femmeAged.getDateNaissance()));
            }
            
            // Test 3: Afficher les épouses d'un homme donné
            System.out.println("\n--- Test 3: Épouses d'un homme ---");
            Long hommeId = hommes.get(0).getId();
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/2000");
            List<Mariage> epouses = hommeService.getEpousesEntreDeuxDates(hommeId, dateDebut, dateFin);
            System.out.println("Épouses de " + hommes.get(0).getNomComplet() + 
                " entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            for (Mariage m : epouses) {
                System.out.println("- " + m.getFemme().getNomComplet() + " (marié le " + 
                    sdf.format(m.getDateDebut()) + ")");
            }
            
            // Test 4: Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("\n--- Test 4: Nombre d'enfants d'une femme ---");
            Long femmeId = femmes.get(0).getId();
            Long nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(femmeId, dateDebut, dateFin);
            System.out.println("Nombre d'enfants de " + femmes.get(0).getNomComplet() + 
                " entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ": " + nbEnfants);
            
            // Test 5: Afficher les femmes mariées deux fois ou plus
            System.out.println("\n--- Test 5: Femmes mariées deux fois ou plus ---");
            List<Femme> femmesMultiMariees = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            System.out.println("Nombre de femmes mariées au moins deux fois: " + femmesMultiMariees.size());
            for (Femme f : femmesMultiMariees) {
                System.out.println("- " + f.getNomComplet());
            }
            
            // Test 6: Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n--- Test 6: Hommes mariés à 4 femmes ---");
            Long nbHommes = femmeService.getNombreHommesMarieesQuatreFemmesEntreDeuxDates(dateDebut, dateFin);
            System.out.println("Nombre d'hommes mariés à 4 femmes entre " + 
                sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ": " + nbHommes);
            
            // Test 7: Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n--- Test 7: Détails des mariages d'un homme ---");
            hommeService.afficherMariagesDetail(hommeId);
            
            System.out.println("\n\n========== TESTS TERMINÉS ==========");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
    
    private static Homme createHomme(String nom, String prenom, String dateNaissance, 
                                    String ville, HommeService service) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Homme homme = new Homme(nom, prenom, sdf.parse(dateNaissance), ville);
        service.create(homme);
        System.out.println("Homme créé: " + homme.getNomComplet());
        return homme;
    }
    
    private static Femme createFemme(String nom, String prenom, String dateNaissance, 
                                    String ville, FemmeService service) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Femme femme = new Femme(nom, prenom, sdf.parse(dateNaissance), ville);
        service.create(femme);
        System.out.println("Femme créée: " + femme.getNomComplet());
        return femme;
    }
    
    private static void createMariages(List<Homme> hommes, List<Femme> femmes, 
                                       MariageService service) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        // Homme 1 (SAFI SAID) - 4 mariages
        service.create(new Mariage(hommes.get(0), femmes.get(3), sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0));
        service.create(new Mariage(hommes.get(0), femmes.get(0), sdf.parse("03/09/1990"), null, 4));
        service.create(new Mariage(hommes.get(0), femmes.get(1), sdf.parse("03/09/1995"), null, 2));
        service.create(new Mariage(hommes.get(0), femmes.get(2), sdf.parse("04/11/2000"), null, 3));
        
        // Homme 2 (ALAOUI MOHAMED) - 4 mariages
        service.create(new Mariage(hommes.get(1), femmes.get(4), sdf.parse("01/01/1995"), null, 1));
        service.create(new Mariage(hommes.get(1), femmes.get(5), sdf.parse("15/06/1998"), null, 2));
        service.create(new Mariage(hommes.get(1), femmes.get(6), sdf.parse("20/03/2000"), null, 3));
        service.create(new Mariage(hommes.get(1), femmes.get(7), sdf.parse("10/08/2002"), null, 1));
        
        // Homme 3 (AMRANI ALI) - 2 mariages
        service.create(new Mariage(hommes.get(2), femmes.get(8), sdf.parse("12/05/1992"), null, 5));
        service.create(new Mariage(hommes.get(2), femmes.get(9), sdf.parse("25/12/1999"), null, 2));
        
        // Homme 4 (BENANI HASSAN) - 3 mariages
        service.create(new Mariage(hommes.get(3), femmes.get(0), sdf.parse("15/03/1993"), sdf.parse("10/05/1995"), 1));
        service.create(new Mariage(hommes.get(3), femmes.get(1), sdf.parse("01/06/1996"), null, 4));
        service.create(new Mariage(hommes.get(3), femmes.get(2), sdf.parse("18/09/2001"), null, 2));
        
        // Homme 5 (ELAKRIM KARIM) - 1 mariage
        service.create(new Mariage(hommes.get(4), femmes.get(4), sdf.parse("07/04/1991"), null, 3));
        
        System.out.println("Mariages créés avec succès!");
    }
}

