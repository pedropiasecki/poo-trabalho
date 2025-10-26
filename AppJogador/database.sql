CREATE TABLE jogador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    posicao VARCHAR(50),
    idade INT
);

CREATE TABLE partida (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    adversario VARCHAR(100),
    local VARCHAR(100)
);

CREATE TABLE estatistica (
    id SERIAL PRIMARY KEY,
    jogador_id INT REFERENCES jogador(id),
    partida_id INT REFERENCES partida(id),
    gols INT DEFAULT 0,
    assistencias INT DEFAULT 0
);

