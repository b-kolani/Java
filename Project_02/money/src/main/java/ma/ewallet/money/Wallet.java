package ma.ewallet.money;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@Entity // Dit à JPA que cette classe correspond à notre table SQL
@Table(name = "wallet") 
@EntityListeners(AuditingEntityListener.class) // <--- Écoute les événements de sauvegarde
public class Wallet {
    
    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // L'ID est géré par le SERIAL de PostgreSQL
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String proprietaire;

    @Column(name = "balance", nullable = false)
    private double solde;

    @CreatedDate 
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate 
    @Column(name = "last_modified_at", nullable = false)
    private Instant lastModifiedAt;

    // Le fameux constructeur protected obligatoire pour JPA 
    protected  Wallet() {
    }

    // Notre constructeur métier pour créer un nouvel objet dans notre code
    public Wallet(String proprietaire, double soldeInitial) {
        if (soldeInitial < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif.");
        }

        this.proprietaire = proprietaire;
        this.solde = soldeInitial;
    }

    // --- Nos méthodes métiers (Rich Domain Model) ---
    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0.");
        }
        
        this.solde += montant;
    }

    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0.");
        }

        if (this.solde < montant) {
            throw new IllegalStateException("Solde insuffisant");
        }

        this.solde -= montant;
    }

    
    // --- Getters ---
    public Long getId() {
        return this.id;
    }
    
    public String getProprietaire() {
        return this.proprietaire;
    }

    public double getSolde() {
        return this.solde;
    }

    // Getters pour l'audit 
    public Instant getCreatedAt() { return this.createdAt; }
    public Instant getLastModifiedAt() { return this.lastModifiedAt; }
}
