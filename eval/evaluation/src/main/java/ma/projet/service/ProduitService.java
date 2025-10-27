package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Produit produit) {
        try {
            entityManager.persist(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit produit) {
        try {
            entityManager.merge(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit produit) {
        try {
            entityManager.remove(entityManager.contains(produit) ?
                    produit : entityManager.merge(produit));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(Long id) {
        return entityManager.find(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        return entityManager.createQuery("SELECT p FROM Produit p", Produit.class)
                .getResultList();
    }

    /**
     * Afficher la liste des produits par catégorie
     */
    public List<Produit> findByCategorie(Long categorieId) {
        return entityManager.createNamedQuery("Produit.findByCategorie", Produit.class)
                .setParameter("categorieId", categorieId)
                .getResultList();
    }

    /**
     * Afficher les produits commandés entre deux dates
     */
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        String jpql = "SELECT DISTINCT p FROM Produit p " +
                "JOIN p.lignesCommande lc " +
                "JOIN lc.commande c " +
                "WHERE c.date BETWEEN :dateDebut AND :dateFin";

        return entityManager.createQuery(jpql, Produit.class)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    /**
     * Afficher les produits commandés dans une commande donnée
     */
    public List<Object[]> findProduitsParCommande(Long commandeId) {
        String jpql = "SELECT p.reference, p.prix, lc.quantite " +
                "FROM Produit p " +
                "JOIN p.lignesCommande lc " +
                "WHERE lc.commande.id = :commandeId";

        return entityManager.createQuery(jpql, Object[].class)
                .setParameter("commandeId", commandeId)
                .getResultList();
    }

    /**
     * Afficher les produits dont le prix est supérieur à 100 DH (requête nommée)
     */
    public List<Produit> findByPrixSuperieur(Double prix) {
        return entityManager.createNamedQuery("Produit.findByPrixSuperieur", Produit.class)
                .setParameter("prix", prix)
                .getResultList();
    }
}
