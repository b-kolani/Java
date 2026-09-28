package ma.ewallet.money;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Dit à Spring que cette classe conteint la logique métier de l'application
public class WalletService {
    
    // La propriété privée walletRepository
    WalletRepository walletRepository;

    // Le constructeur avec injection de dépendance du repository
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional // Garantit que l'opération se déroule de manière sécurisée
    public Wallet creerPortefeuille(String proprietaire, double soldeInitial) {

        // 1. LA VÉRIFICATION : On cherche si un portefeuille existe déjà avec ce nom
        Wallet portefeuilleExistant = this.walletRepository.findByProprietaire(proprietaire);

        if (portefeuilleExistant != null) {
            // Si le nom existe déjà, on lève une exception
            throw new IllegalStateException("Un portefeuille existe déjà pour le client : " + proprietaire);
        }

        // 2. Si tout est bon, on crée et on sauvegarde
        Wallet nouveauWallet = new Wallet(proprietaire, soldeInitial);
        return this.walletRepository.save(nouveauWallet);
    }

    @Transactional 
    public Wallet crediterPortefeuille(String proprietaire, double montant) {
        Wallet wallet = this.walletRepository.findByProprietaire(proprietaire);

        if (wallet == null) {
            throw new IllegalArgumentException("Portefeuille introuvable pour : " + proprietaire);
        }

        wallet.crediter(montant); // Logique métier riche
        // Ici on peut laisser le Dirty Checking opérer pour nous 
        // comme on l'a fait dans debiterPortefeuille
        return walletRepository.save(wallet);
    }

    @Transactional // <-- C'est cette annotation qui active le Dirty Checking
    public Wallet debiterPortefeuille(String proprietaire, double montant) {
        Wallet wallet = walletRepository.findByProprietaire(proprietaire); // 1. Hibernate prend une "photo"

        if (wallet == null) {
            throw new IllegalArgumentException("Portefeuille introuvable pour : " + proprietaire);
        }

        // 2. L'objet change en mémoire Java
        wallet.debiter(montant); // logique métier riche (lève une exception si solde insuffisant)

        // return walletRepository.save(wallet); <-- Au lieu de faire ceci
        // on va utiliser la puissance du Dirty Checking ainsi à la fin 
        // de notre méthode, @Transactional ferme la session. 
        // Le Dirty Checking voit le changement et déclenche l'UPDATE SQL 
        // tout seul !
        return wallet;
    }

}
