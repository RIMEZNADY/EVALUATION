package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Categorie categorie) {
        try {
            entityManager.persist(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Categorie categorie) {
        try {
            entityManager.merge(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Categorie categorie) {
        try {
            entityManager.remove(entityManager.contains(categorie) ?
                    categorie : entityManager.merge(categorie));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Categorie findById(Long id) {
        return entityManager.find(Categorie.class, id);
    }

    @Override
    public List<Categorie> findAll() {
        return entityManager.createNamedQuery("Categorie.findAll", Categorie.class)
                .getResultList();
    }
}
