package ma.projet.service;

import ma.projet.classes.LigneCommande;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommande> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(LigneCommande ligneCommande) {
        try {
            entityManager.persist(ligneCommande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(LigneCommande ligneCommande) {
        try {
            entityManager.merge(ligneCommande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(LigneCommande ligneCommande) {
        try {
            entityManager.remove(entityManager.contains(ligneCommande) ?
                    ligneCommande : entityManager.merge(ligneCommande));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LigneCommande findById(Long id) {
        return entityManager.find(LigneCommande.class, id);
    }

    @Override
    public List<LigneCommande> findAll() {
        return entityManager.createQuery("SELECT lc FROM LigneCommande lc",
                        LigneCommande.class)
                .getResultList();
    }
}