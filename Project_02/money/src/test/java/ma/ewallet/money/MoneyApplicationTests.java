package ma.ewallet.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest //pring et Dit à JUnit de démarrer Spring et la base de données pour le test
class MoneyApplicationTests {

	@Autowired // Injecte automatiquement le repository dans notre classe de test
	private WalletRepository walletRepository;

	@BeforeEach // S'exécute automatiquement AVANT chaque méthode de test
	void setUp() {
		// On nettoie la table pour être sûr de repartir de zéro à chaque lancement
		walletRepository.deleteAll(); 
	}
	
	@Test // Indique que cette méthode est un scénario de test automatique
	void testSauvegardeEtRechercheWallet() {
		// 1. GIVEN (Étant donné un nouveau portefeuille)
		Wallet nouveau = new Wallet("Sarah", 1000.0);

		// 2. WHEN (Quand on effectue des actions)
		Wallet sauvegarde = walletRepository.save(nouveau);
		System.out.println("Portefeuille sauvegardé avec l'ID : " + sauvegarde.getId());
		Wallet trouve = walletRepository.findByProprietaire("Sarah");

		// 3. THEN (Alors on vérifie que le résultat est correct)
		// On utilise des "Assertions" pour valider le comportement du code
		assertNotNull(trouve, "Le portefeuille devrait être trouvé en BDD");
		assertEquals("Sarah", trouve.getProprietaire());
		assertEquals(1000.0, trouve.getSolde());
	}
}
