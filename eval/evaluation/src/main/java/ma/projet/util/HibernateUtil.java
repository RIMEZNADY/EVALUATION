package ma.projet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe utilitaire pour Hibernate
 * Note: Avec Spring Boot, l'EntityManager est automatiquement géré
 * Cette classe est fournie pour la compatibilité avec l'exercice
 */
@Component
public class HibernateUtil {

    private static EntityManagerFactory entityManagerFactory;

    @Autowired
    public HibernateUtil(EntityManagerFactory emf) {
        HibernateUtil.entityManagerFactory = emf;
    }

    /**
     * Obtenir l'EntityManagerFactory
     * @return EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Créer un EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Fermer l'EntityManagerFactory
     */
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}