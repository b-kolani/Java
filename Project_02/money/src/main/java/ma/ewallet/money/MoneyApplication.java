package ma.ewallet.money;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyApplication.class, args);
	}

	@Bean 
	public CommandLineRunner testApp(WalletRepository repository) {
		return args -> {
			System.out.println("====== DÉBUT DU TEST SPRING DATA JPA ======");

			// 1. Création d'un nouvelobjet Wallet (En mémoire Java)
			Wallet nouveauWallet = new Wallet("Aymane", 500.0);

			// 2. Sauvegarde en Base de données (Génère un INSERT SQL automatique)
			// La méthode .save() est fournie gratuitement par JpaRepository !
			Wallet walletSauvegarde = repository.save(nouveauWallet);
			System.out.println("Portefeuille sauvegardé avec l'ID : " + walletSauvegarde.getId());

			// 3. Test de modification avec le "Dirty Checking"
			// On récupère le portefeuille qu'on vient d'enregistrer
			Wallet walletAEditer = repository.findById(walletSauvegarde.getId()).orElseThrow();

			// On utilise notre méthode métier
			walletAEditer.crediter(150.0);

			// On sauvegarde à nouveau (Génère un UPDATE SQL)
			repository.save(walletAEditer);

			// 4. Recherche par nom (En utilisant la méthode personnalisée qu'on a écrite !)
			Wallet walletTrouve = repository.findByProprietaire("Aymane");
			if (walletTrouve != null) {
				System.out.println("Recherche réussie ! " + walletTrouve.getProprietaire() + " possède " + walletTrouve.getSolde() + " MAD.");
			}

			System.out.println("====== FIN DU TEST AVEC SUCCÈS ======");
		};
	}
}
