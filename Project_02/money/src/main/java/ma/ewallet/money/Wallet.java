package ma.ewallet.money;

import jakarta.persistence.*;

@Entity // Dit à JPA que cette classe correspond à notre table SQL
@Table(name = "wallet") 
public class Wallet {
    
    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // L'ID est géré par le SERIAL de PostgreSQL
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String proprietaire;

    @Column(name = "balance", nullable = false)
    private double solde;

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

}
