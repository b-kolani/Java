CREATE TABLE wallet (
    id BIGSERIAL PRIMARY KEY, -- Équivalent de Long en Java, auto-incrémenté
    owner_name VARCHAR(255) NOT NULL, -- Le nom du propriétaire
    balance NUMERIC(15, 2) NOT NULL -- Le solde (NUMERIC est parfait pour l'argent)
)