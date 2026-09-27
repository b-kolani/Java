public class Portefeuille {
    
    private String  proprietaire;
    private double  solde;
    private String  type;

    public Portefeuille(String nomDuClient,
        double soldeDeDepart,
        String typeDePortefeuille) {
        this.proprietaire = nomDuClient;
        this.solde = soldeDeDepart;
        this.type = typeDePortefeuille;
    }

    public String getProprietaire() {
        return this.proprietaire;
    }

    public double voirSolde() {
        return this.solde;
    }

    public String getType() {
        return this.type;
    }

    public void crediter(double montant) {

        if (montant > 0) {
            this.solde += montant;
        }else if (montant < 0 && this.type == "premium") {
                this.solde += montant;
        } else {
            System.out.println("Erreur : Le montant doit être supérieur à 0 !");
        }
    }

    public void debiter(double montant) {
        
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur ou égal à 0.");
        }

        if (this.solde < montant) {
            throw new IllegalStateException("Solde insuffisant pour effectuer le débit.");
        }

        this.solde -= montant;
    }

    public void transferer(double montant, Portefeuille destination) {

        if (montant > 0 && montant <= this.solde) {

            this.solde -= montant;

            destination.crediter(montant);

            System.out.println("Virement réussi de " + montant + " MAD !");
        } else {
            System.out.println("Échec de virement : Solde insuffisant ou montant invalide.");
        }
    }
}
