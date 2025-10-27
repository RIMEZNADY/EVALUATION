package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produits")
@NamedQueries({
        @NamedQuery(
                name = "Produit.findByPrixSuperieur",
                query = "SELECT p FROM Produit p WHERE p.prix > :prix"
        ),
        @NamedQuery(
                name = "Produit.findByCategorie",
                query = "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId"
        )
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;

    // Constructeurs
    public Produit() {}

    public Produit(String reference, Double prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommande> getLignesCommande() { return lignesCommande; }
    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }
}
