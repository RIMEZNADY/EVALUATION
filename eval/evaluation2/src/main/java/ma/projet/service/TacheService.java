package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Tache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Tache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tache tache = session.get(Tache.class, id);
        session.close();
        return tache;
    }

    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Tache> taches = session.createQuery("FROM Tache", Tache.class).getResultList();
        session.close();
        return taches;
    }

    /**
     * Afficher les tâches dont le prix est supérieur à un montant donné (requête nommée)
     */
    public List<Tache> findByPrixSuperieur(double prix) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Tache> taches = session.createNamedQuery("Tache.findByPrixSuperieur")
                .setParameter("prix", prix)
                .getResultList();
        session.close();
        return taches;
    }

    /**
     * Afficher les tâches réalisées entre deux dates
     */
    public List<Tache> findByDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Tache> taches = session.createNamedQuery("Tache.findBetweenDates")
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
        session.close();
        return taches;
    }
}
