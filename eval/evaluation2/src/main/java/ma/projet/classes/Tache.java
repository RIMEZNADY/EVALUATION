package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "Tache.findByPrixSuperieur",
        query = "SELECT t FROM Tache t WHERE t.prix > :prix"
    ),
    @NamedQuery(
        name = "Tache.findBetweenDates",
        query = "SELECT t FROM Tache t WHERE t.dateDebutReelle BETWEEN :dateDebut AND :dateFin"
    )
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nom;
    
    @Temporal(TemporalType.DATE)
    private Date dateDebutPlanifiee;
    
    @Temporal(TemporalType.DATE)
    private Date dateFinPlanifiee;
    
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;
    
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;
    
    private double prix;
    
    @ManyToOne
    private Projet projet;
    
    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL)
    private List<EmployeTache> employeTaches;

    public Tache() {
    }

    public Tache(String nom, Date dateDebutPlanifiee, Date dateFinPlanifiee, 
                 Date dateDebutReelle, Date dateFinReelle, double prix) {
        this.nom = nom;
        this.dateDebutPlanifiee = dateDebutPlanifiee;
        this.dateFinPlanifiee = dateFinPlanifiee;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebutPlanifiee() {
        return dateDebutPlanifiee;
    }

    public void setDateDebutPlanifiee(Date dateDebutPlanifiee) {
        this.dateDebutPlanifiee = dateDebutPlanifiee;
    }

    public Date getDateFinPlanifiee() {
        return dateFinPlanifiee;
    }

    public void setDateFinPlanifiee(Date dateFinPlanifiee) {
        this.dateFinPlanifiee = dateFinPlanifiee;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebutPlanifiee=" + dateDebutPlanifiee +
                ", dateFinPlanifiee=" + dateFinPlanifiee +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                ", prix=" + prix +
                '}';
    }
}
