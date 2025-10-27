package ma.projet.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;

public class FemmeService implements IDao<Femme> {
    
    @Override
    public void create(Femme o) {
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
    public void update(Femme o) {
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
    public void delete(Femme o) {
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
    public Femme findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Femme femme = null;
        try {
            tx = session.beginTransaction();
            femme = session.get(Femme.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return femme;
    }
    
    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Femme> femmes = null;
        try {
            femmes = session.createQuery("FROM Femme", Femme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return femmes;
    }
    
    // Méthode pour exécuter une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
    public Long getNombreEnfantsEntreDeuxDates(Long femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long result = 0L;
        try {
            Query query = session.getNamedNativeQuery("nombreEnfantsEntreDeuxDates");
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            Object resultObj = query.getSingleResult();
            if (resultObj instanceof Number) {
                result = ((Number) resultObj).longValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    
    // Méthode pour exécuter une requête nommée retournant les femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Femme> femmes = null;
        try {
            Query<Femme> query = session.createNamedQuery("femme.marieeAuMoinsDeuxFois", Femme.class);
            femmes = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return femmes;
    }
    
    // Méthode utilisant l'API Criteria pour afficher les hommes mariés à quatre femmes entre deux dates
    public List<ma.projet.beans.Homme> getHommesMarieesQuatreFemmesEntreDeuxDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ma.projet.beans.Homme> hommes = null;
        try {
            // Créer une sous-requête pour trouver les IDs des hommes mariés à 4 femmes
            List<Long> hommeIds = session.createQuery(
                "SELECT m.homme.id FROM Mariage m " +
                "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                "GROUP BY m.homme.id " +
                "HAVING COUNT(DISTINCT m.femme.id) = 4",
                Long.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .list();
            
            if (!hommeIds.isEmpty()) {
                hommes = session.createQuery(
                    "FROM Homme h WHERE h.id IN (:ids)",
                    ma.projet.beans.Homme.class)
                    .setParameter("ids", hommeIds)
                    .list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return hommes;
    }
    
    // Méthode alternative plus simple pour obtenir les hommes mariés à 4 femmes
    public Long getNombreHommesMarieesQuatreFemmesEntreDeuxDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;
        try {
            Long result = session.createQuery(
                "SELECT COUNT(DISTINCT m.homme.id) FROM Mariage m " +
                "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                "GROUP BY m.homme.id " +
                "HAVING COUNT(DISTINCT m.femme.id) = 4",
                Long.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getSingleResult();
            
            if (result != null) {
                count = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }
    
    // Méthode pour trouver la femme la plus âgée
    public Femme getFemmeLaPlusAged() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Femme femme = null;
        try {
            femme = session.createQuery(
                "FROM Femme ORDER BY dateNaissance ASC",
                Femme.class)
                .setMaxResults(1)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return femme;
    }
}

