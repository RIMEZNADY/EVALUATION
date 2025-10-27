package ma.projet.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mariage")
@NamedQueries({
    @NamedQuery(
        name = "femme.marieeAuMoinsDeuxFois",
        query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme.id = f.id) >= 2"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "nombreEnfantsEntreDeuxDates",
        query = "SELECT COUNT(*) FROM mariage m " +
                "WHERE m.femme_id = :femmeId " +
                "AND m.date_debut BETWEEN :dateDebut AND :dateFin",
        resultClass = Long.class
    )
})
public class Mariage implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin", nullable = true)
    private Date dateFin;
    
    @Column(nullable = false)
    private int nombreEnfants;
    
    public Mariage() {
    }
    
    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nombreEnfants) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nombreEnfants = nombreEnfants;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Homme getHomme() {
        return homme;
    }
    
    public void setHomme(Homme homme) {
        this.homme = homme;
    }
    
    public Femme getFemme() {
        return femme;
    }
    
    public void setFemme(Femme femme) {
        this.femme = femme;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    public int getNombreEnfants() {
        return nombreEnfants;
    }
    
    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }
    
    public boolean isEnCours() {
        return dateFin == null;
    }
    
    @Override
    public String toString() {
        return "Mariage{" +
                "id=" + id +
                ", homme=" + (homme != null ? homme.getNomComplet() : "null") +
                ", femme=" + (femme != null ? femme.getNomComplet() : "null") +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nombreEnfants=" + nombreEnfants +
                '}';
    }
}

