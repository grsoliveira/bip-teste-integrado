-- CREATE TABLE BENEFICIO
-- (
--     ID        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     NOME      VARCHAR(100)   NOT NULL,
--     DESCRICAO VARCHAR(255),
--     VALOR     DECIMAL(15, 2) NOT NULL,
--     ATIVO     BOOLEAN DEFAULT TRUE,
--     VERSION   BIGINT  DEFAULT 0
-- );

-- O DDL vai ficar a cargo do JPA usando a classe @Entity
-- Os inserts devem ficar separados (um em cada instrução) e sem quenbras de linha para evitar erros de execução.
INSERT INTO BENEFICIO (NOME, DESCRICAO, VALOR, ATIVO) VALUES ('Beneficio A', 'Descrição A', 1000.00, TRUE);
INSERT INTO BENEFICIO (NOME, DESCRICAO, VALOR, ATIVO) VALUES ('Beneficio B', 'Descrição B', 500.00, TRUE);
