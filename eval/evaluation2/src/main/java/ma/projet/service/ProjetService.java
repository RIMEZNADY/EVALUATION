package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Projet o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Projet findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Projet projet = session.get(Projet.class, id);
        session.close();
        return projet;
    }

    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Projet> projets = session.createQuery("FROM Projet", Projet.class).getResultList();
        session.close();
        return projets;
    }

    /**
     * Afficher la liste des tâches planifiées pour un projet
     */
    public void afficherTachesPlanifiees(int projetId) {
        Projet projet = findById(projetId);
        if (projet != null) {
            System.out.println("\n=== Tâches planifiées pour le projet: " + projet.getNom() + " ===");
            System.out.println("Num\tNom\t\tDate Début Planifiée\tDate Fin Planifiée");
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            @SuppressWarnings("unchecked")
            List<Tache> taches = session.createQuery(
                "FROM Tache t WHERE t.projet.id = :projetId", Tache.class)
                .setParameter("projetId", projetId)
                .getResultList();
            session.close();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Tache t : taches) {
                String dateDebut = t.getDateDebutPlanifiee() != null ? 
                    sdf.format(t.getDateDebutPlanifiee()) : "N/A";
                String dateFin = t.getDateFinPlanifiee() != null ? 
                    sdf.format(t.getDateFinPlanifiee()) : "N/A";
                
                System.out.println(t.getId() + "\t" + t.getNom() + "\t" + 
                    dateDebut + "\t" + dateFin);
            }
        }
    }

    /**
     * Afficher la liste des tâches réalisées avec les dates réelles
     */
    public void afficherTachesRealisees(int projetId) {
        Projet projet = findById(projetId);
        if (projet != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfFull = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.FRENCH);
            
            System.out.println("\nProjet : " + projet.getId() + 
                "\tNom : " + projet.getNom() + 
                "\tDate début : " + (projet.getDateDebut() != null ? 
                    sdfFull.format(projet.getDateDebut()) : "N/A"));
            System.out.println("Liste des tâches:");
            System.out.println("Num Nom\t\t\tDate Début Réelle\tDate Fin Réelle");
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            @SuppressWarnings("unchecked")
            List<Tache> taches = session.createQuery(
                "FROM Tache t WHERE t.projet.id = :projetId AND t.dateDebutReelle IS NOT NULL", 
                Tache.class)
                .setParameter("projetId", projetId)
                .getResultList();
            session.close();
            
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
            for (Tache t : taches) {
                String dateDebutReelle = t.getDateDebutReelle() != null ? 
                    sdfOutput.format(t.getDateDebutReelle()) : "N/A";
                String dateFinReelle = t.getDateFinReelle() != null ? 
                    sdfOutput.format(t.getDateFinReelle()) : "N/A";
                
                System.out.println(t.getId() + "  " + t.getNom() + "\t\t" + 
                    dateDebutReelle + "\t\t" + dateFinReelle);
            }
        }
    }
}
