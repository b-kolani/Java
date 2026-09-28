package ma.ewallet.money;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // Dit à Spring que cette classe va gérer des adresses URL (API) au format JSON 
public class WalletController {

    // Le repository
    private WalletRepository walletRepository;
    // Le service  
    private WalletService walletService;

    // Injection du repository ET service via le constructeur (Meilleure pratique)
    public WalletController(WalletRepository walletRepository, WalletService walletService) {
        this.walletRepository = walletRepository;
        this.walletService = walletService;
    }

    // Cette méthode va répondre à l'adresse : http://localhost:8080/solde?nom=LeNomDuClient
    @GetMapping("/solde")
    public String consulterSolde(@RequestParam String nom) {

        // On utilise notre méthode de recherche 
        Wallet wallet = this.walletRepository.findByProprietaire(nom);

        // Gestion de la sécurité (Le piège du null !)
        if (wallet == null) {
            System.out.println("Désolé, aucun portefeuille trouvé au nom de : " + nom);
        }

        // ON renvoie un texte clair au navigateur
        return "Le client " + wallet.getProprietaire() + " possède actuellemnt " + wallet.getSolde() + " MAD.";
    }

    // Cette méthode va répondre aux requêtes POST sur : http://localhost:8080/creer
    @PostMapping("/creer")
    public String creerPortefeuille(@RequestParam String nom, @RequestParam double solde) {
        try {
            // On appelle notre service sécurisé
            Wallet nouveau = walletService.creerPortefeuille(nom, solde);
            return "Succès ! Le portefeuille de " + nouveau.getProprietaire() + " a été créé avec l'ID " + nouveau.getId();

        } catch (IllegalStateException e) {
            // On attrape l'exception si le client existe déjà 
            return "Erreur : " + e.getMessage();
        }
    }
    
    @PostMapping("/crediter")
    public String crediter(@RequestParam String nom, @RequestParam double montant) {

        try {
            Wallet wallet = walletService.crediterPortefeuille(nom, montant);
            return "Crédit réussi ! Nouveau solde de " + wallet.getProprietaire() + " : " + wallet.getSolde() + " MAD.";
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }

    @PostMapping("/debiter")
    public String debiter(@RequestParam String nom, @RequestParam double montant) {

        try {
            Wallet wallet = walletService.debiterPortefeuille(nom, montant);
            return "Débit réussi ! Nouveau solde de " + wallet.getProprietaire() + " : " + wallet.getSolde() + " MAD.";
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }
}
