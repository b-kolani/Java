public class PortefeuillePremium extends Portefeuille {

    public PortefeuillePremium(String nomDuClient, double soldeDeDepart) {
        super(nomDuClient, soldeDeDepart, "premium");
    }

    @Override 
    public void debiter(double montant) {

        if (montant > 0 && ((this.voirSolde() - montant) >= -500)) {

            super.crediter(-montant);
        } else {
            System.out.println("Erreur Premium : Limite de découvert de -500 MAD dépassé");
        }
    }
}
