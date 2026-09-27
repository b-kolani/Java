public class App {
    public static void main(String[] args) throws Exception {
    
        try {
            Portefeuille p = new Portefeuille("Aymane", 50.0, "normal");

            p.debiter(100.0);
            System.out.println("Cette ligne ne sera jamais exécutée.");
        } catch (IllegalArgumentException e) {
            System.out.println("Alerte Sécurité : " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Erreur de saisie : " + e.getMessage());
        }
    }
}
