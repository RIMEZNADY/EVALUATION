package ma.projet.classes;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@NamedQueries({
        @NamedQuery(
                name = "Categorie.findAll",
                query = "SELECT c FROM Categorie c"
        )
})
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "categorie", fetch = FetchType.LAZY)
    private List<Produit> produits;

    // Constructeurs
    public Categorie() {}

    public Categorie(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }
}