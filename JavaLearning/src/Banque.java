import java.util.ArrayList;
import java.util.List;

public class Banque {

    private List<Portefeuille> portefeuilles;

    public Banque() {
        portefeuilles = new ArrayList<>();
    }
    
    public void ajouterPortefeuille(Portefeuille p) {
        this.portefeuilles.add(p);
        System.out.println("Portefeuille ajouté avec succès !");
    }

    public void afficherTousLesSoldes() {
        System.out.println("--- Liste des soldes de la banque ---");

        for (Portefeuille p : this.portefeuilles) {

            System.out.println("Propriétaire : " + p.getProprietaire());
            System.out.println("Solde : " + p.voirSolde() + " MAD");
        }
    }

    public double calculerArgentTotal() {
        double total = 0;

        for (Portefeuille p : this.portefeuilles) {
            total += p.voirSolde();
        }
        return total;
    }

    public Portefeuille chercherPortefeuille(String nomRecherche) {

        for (Portefeuille p : this.portefeuilles) {
            if (p.getProprietaire().equals(nomRecherche)) {
                return p;
            }
        }

        return null;
    }
}
