CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    codigo VARCHAR(100),
    qnt INT,
    status varchar(20),
    usuario_id BIGINT,
    CONSTRAINT fk_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);

CREATE TABLE transicao_produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT,
    usuario_id BIGINT,
    tipo VARCHAR(20),
    quantidade INT,
    data TIMESTAMP,
    observacao VARCHAR(255),
    CONSTRAINT fk_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),
    CONSTRAINT fk_usuario_transacao
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);