package ma.projet.dao;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD
 * @param <T> Type de l'entité
 */
public interface IDao<T> {

    /**
     * Créer une nouvelle entité
     * @param o L'entité à créer
     * @return true si la création réussit, false sinon
     */
    boolean create(T o);

    /**
     * Mettre à jour une entité existante
     * @param o L'entité à mettre à jour
     * @return true si la mise à jour réussit, false sinon
     */
    boolean update(T o);

    /**
     * Supprimer une entité
     * @param o L'entité à supprimer
     * @return true si la suppression réussit, false sinon
     */
    boolean delete(T o);

    /**
     * Récupérer une entité par son ID
     * @param id L'identifiant de l'entité
     * @return L'entité trouvée ou null
     */
    T findById(Long id);

    /**
     * Récupérer toutes les entités
     * @return Liste de toutes les entités
     */
    List<T> findAll();
}