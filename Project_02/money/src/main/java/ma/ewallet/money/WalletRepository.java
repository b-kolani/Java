package ma.ewallet.money;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository // Indique à Spring que ce composant gère les accès à la base de données 
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
    // En écrivant juste cette signature, Spring comprend tout seul 
    // et va générer la requête SQL : SELECT * FROM wallet WHERE owner_name = ?
    Wallet findByProprietaire(String proprietaire);
}
