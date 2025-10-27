package ma.projet.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;

public class HommeService implements IDao<Homme> {
    
    @Override
    public void create(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void update(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void delete(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public Homme findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Homme homme = null;
        try {
            tx = session.beginTransaction();
            homme = session.get(Homme.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return homme;
    }
    
    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Homme> hommes = null;
        try {
            hommes = session.createQuery("FROM Homme", Homme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hommes;
    }
    
    // Méthode pour afficher les épouses d'un homme entre deux dates
    public List<Mariage> getEpousesEntreDeuxDates(Long hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> mariages = null;
        try {
            mariages = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId " +
                "AND m.dateDebut BETWEEN :dateDebut AND :dateFin",
                Mariage.class)
                .setParameter("hommeId", hommeId)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mariages;
    }
    
    // Méthode pour afficher les mariages d'un homme avec tous les détails
    public void afficherMariagesDetail(Long hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme introuvable!");
                return;
            }
            
            System.out.println("\nNom : " + homme.getNomComplet());
            
            // Récupérer tous les mariages
            List<Mariage> mariages = session.createQuery(
                "FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut",
                Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();
            
            // Séparer les mariages en cours et les mariages échoués
            List<Mariage> enCours = mariages.stream()
                    .filter(m -> m.isEnCours())
                    .collect(Collectors.toList());

            List<Mariage> echoues = mariages.stream()
                    .filter(m -> !m.isEnCours())
                    .collect(Collectors.toList());
            
            // Afficher les mariages en cours
            if (!enCours.isEmpty()) {
                System.out.println("\nMariages En Cours :");
                int index = 1;
                for (Mariage m : enCours) {
                    System.out.println(index + ". Femme : " + m.getFemme().getNomComplet() +
                        "   Date Début : " + String.format("%02d/%02d/%04d", 
                            m.getDateDebut().getDate(),
                            m.getDateDebut().getMonth() + 1,
                            m.getDateDebut().getYear() + 1900) +
                        "    Nbr Enfants : " + m.getNombreEnfants());
                    index++;
                }
            }
            
            // Afficher les mariages échoués
            if (!echoues.isEmpty()) {
                System.out.println("\nMariages échoués :");
                int index = 1;
                for (Mariage m : echoues) {
                    System.out.println(index + ". Femme : " + m.getFemme().getNomComplet() +
                        "   Date Début : " + String.format("%02d/%02d/%04d", 
                            m.getDateDebut().getDate(),
                            m.getDateDebut().getMonth() + 1,
                            m.getDateDebut().getYear() + 1900) +
                        "    Date Fin : " + String.format("%02d/%02d/%04d", 
                            m.getDateFin().getDate(),
                            m.getDateFin().getMonth() + 1,
                            m.getDateFin().getYear() + 1900) +
                        "    Nbr Enfants : " + m.getNombreEnfants());
                    index++;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

