CREATE TABLE INSTITUICAO (NUMREGISTRO VARCHAR(255) NOT NULL, EMAIL VARCHAR(255) UNIQUE, NOME VARCHAR(255) UNIQUE, PRIMARY KEY (NUMREGISTRO));
CREATE TABLE GRUPO (ID VARCHAR(255) NOT NULL, PRIMARY KEY (ID));
CREATE TABLE ACESSO (GRUPO_ID VARCHAR(255) NOT NULL, INSTITUICAO_NUMREGISTRO VARCHAR(255) NOT NULL, USUARIO_ID VARCHAR(255) NOT NULL, PRIMARY KEY (GRUPO_ID, INSTITUICAO_NUMREGISTRO, USUARIO_ID));
CREATE TABLE USUARIO (ID VARCHAR(255) NOT NULL, ADMINISTRADOR BOOLEAN, ATIVO BOOLEAN, EMAIL VARCHAR(255) UNIQUE, SENHA VARCHAR(255), VALIDADE DATE, PRIMARY KEY (ID));
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_GRUPO_ID FOREIGN KEY (GRUPO_ID) REFERENCES GRUPO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_USUARIO_ID FOREIGN KEY (USUARIO_ID) REFERENCES USUARIO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_INSTITUICAO_NUMREGISTRO FOREIGN KEY (INSTITUICAO_NUMREGISTRO) REFERENCES INSTITUICAO (NUMREGISTRO);
