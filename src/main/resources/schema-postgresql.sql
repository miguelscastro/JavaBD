
CREATE TABLE IF NOT EXISTS tipo(
    cd_TipoProduto serial primary key,
    nm_TipoProduto VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS produto(
    cd_Produto serial primary key,
    nm_Produto VARCHAR(50),
    ds_Produto VARCHAR(300),
    cd_TipoProduto INT,
    ds_caminhoImagem VARCHAR(255),
    CONSTRAINT FK_Tipo FOREIGN KEY (cd_TipoProduto) REFERENCES tipo(cd_TipoProduto)
);

CREATE TABLE IF NOT EXISTS usuarios(
    cd_ID serial primary key,
    nm_Usuario VARCHAR(50),
    senha_Hash TEXT
);
