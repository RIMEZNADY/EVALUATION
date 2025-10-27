package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Commande commande) {
        try {
            entityManager.persist(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Commande commande) {
        try {
            entityManager.merge(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Commande commande) {
        try {
            entityManager.remove(entityManager.contains(commande) ?
                    commande : entityManager.merge(commande));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Commande findById(Long id) {
        return entityManager.find(Commande.class, id);
    }

    @Override
    public List<Commande> findAll() {
        return entityManager.createQuery("SELECT c FROM Commande c", Commande.class)
                .getResultList();
    }
}