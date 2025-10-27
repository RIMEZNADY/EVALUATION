package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Employe findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employe employe = session.get(Employe.class, id);
        session.close();
        return employe;
    }

    @Override
    public List<Employe> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employe> employes = session.createQuery("FROM Employe", Employe.class).getResultList();
        session.close();
        return employes;
    }

    /**
     * Afficher la liste des tâches réalisées par un employé
     */
    public void afficherTachesRealisees(int employeId) {
        Employe employe = findById(employeId);
        if (employe != null) {
            System.out.println("\n=== Tâches réalisées par: " + employe.getNom() + " " + employe.getPrenom() + " ===");
            System.out.println("Num\tNom\t\t\tProjet\t\t\tDate Début\tDate Fin");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            @SuppressWarnings("unchecked")
            List<EmployeTache> employeTaches = session.createQuery(
                "FROM EmployeTache et WHERE et.employe.id = :employeId", EmployeTache.class)
                .setParameter("employeId", employeId)
                .getResultList();
            session.close();
            
            for (EmployeTache et : employeTaches) {
                String dateDebut = et.getDateDebut() != null ? 
                    sdf.format(et.getDateDebut()) : "N/A";
                String dateFin = et.getDateFin() != null ? 
                    sdf.format(et.getDateFin()) : "N/A";
                String projetNom = et.getTache().getProjet() != null ? 
                    et.getTache().getProjet().getNom() : "N/A";
                
                System.out.println(et.getId() + "\t" + et.getTache().getNom() + "\t\t\t" + 
                    projetNom + "\t\t" + dateDebut + "\t" + dateFin);
            }
        }
    }

    /**
     * Afficher la liste des projets gérés par un employé
     */
    public void afficherProjetsGeres(int employeId) {
        Employe employe = findById(employeId);
        if (employe != null && employe.getNom() != null) {
            System.out.println("\n=== Projets gérés par: " + employe.getNom() + " " + employe.getPrenom() + " ===");
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            @SuppressWarnings("unchecked")
            List<Projet> projets = session.createQuery(
                "SELECT DISTINCT et.tache.projet FROM EmployeTache et WHERE et.employe.id = :employeId", 
                Projet.class)
                .setParameter("employeId", employeId)
                .getResultList();
            session.close();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (projets.isEmpty()) {
                System.out.println("Aucun projet géré par cet employé.");
            } else {
                for (Projet p : projets) {
                    String dateDebut = p.getDateDebut() != null ? 
                        sdf.format(p.getDateDebut()) : "N/A";
                    String dateFin = p.getDateFin() != null ? 
                        sdf.format(p.getDateFin()) : "N/A";
                    
                    System.out.println("ID: " + p.getId() + 
                        "\tNom: " + p.getNom() + 
                        "\tDate début: " + dateDebut + 
                        "\tDate fin: " + dateFin);
                }
            }
        }
    }
}
