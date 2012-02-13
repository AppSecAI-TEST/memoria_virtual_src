CREATE TABLE USUARIO (ID VARCHAR(255) NOT NULL, ADMINISTRADOR BOOLEAN, ATIVO BOOLEAN, EMAIL VARCHAR(255) UNIQUE, NOMECOMPLETO VARCHAR(255), SENHA VARCHAR(255), TELEFONE VARCHAR(255), VALIDADE DATE, PRIMARY KEY (ID));
CREATE TABLE GRUPO (ID VARCHAR(255) NOT NULL, PRIMARY KEY (ID));
CREATE TABLE ACESSO (VALIDADE BOOLEAN, GRUPO VARCHAR(255) NOT NULL, INSTITUICAO BIGINT NOT NULL, USUARIO VARCHAR(255) NOT NULL, PRIMARY KEY (GRUPO, INSTITUICAO, USUARIO));
CREATE TABLE INSTITUICAO (ID BIGINT NOT NULL, CEP VARCHAR(255), CIDADE VARCHAR(255), ENDERECO VARCHAR(255), ESTADO VARCHAR(255), LOCALIZACAO VARCHAR(255), NOME VARCHAR(255), TELEFONE VARCHAR(255), VALIDADE BOOLEAN, PRIMARY KEY (ID));
CREATE TABLE ITEMAUDITORIA (ID BIGINT NOT NULL,DATA DATE, ATRIBUTOSIGNIFICATIVO VARCHAR(255), NOTAS VARCHAR(255), TIPOACAO INTEGER, AUTORACAO_ID VARCHAR(255), PRIMARY KEY (DATA));
CREATE TABLE APROVACAO (ID BIGINT NOT NULL, CHAVEESTRANGEIRA VARCHAR(255), DATA DATE, EXPIRACAO DATE, TABELAESTRANGEIRA VARCHAR(255), APROVADOR VARCHAR(255), PRIMARY KEY (ID));
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_USUARIO FOREIGN KEY (USUARIO) REFERENCES USUARIO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_GRUPO FOREIGN KEY (GRUPO) REFERENCES GRUPO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_INSTITUICAO FOREIGN KEY (INSTITUICAO) REFERENCES INSTITUICAO (ID);
ALTER TABLE ITEMAUDITORIA ADD CONSTRAINT FK_ITEMAUDITORIA_AUTORACAO_ID FOREIGN KEY (AUTORACAO_ID) REFERENCES USUARIO (ID);
ALTER TABLE APROVACAO ADD CONSTRAINT FK_APROVACAO_APROVADOR FOREIGN KEY (APROVADOR) REFERENCES USUARIO (ID);
CREATE SEQUENCE INSTITUICAO_SEQ START WITH 1;
CREATE SEQUENCE APROVACAO_SEQ START WITH 1;
CREATE SEQUENCE ITEMAUDITORIA_SEQ START WITH 1;
/* Usuario admin padr�o para o desenvolvimento do sistema:
ID:mvirtual
Email:mvirtual@usp.br
senha:mvirtual
*/
INSERT INTO USUARIO values( 'mvirtual', true, true, 'mvirtual@usp.br','Mem�ria Virtual',
'8D1E3B49C3A725414FBED43AD7E0A480DEA6220A83DF3B10C4496270FC5A1E6328732550F4AC8C4F6ADE0EAE7F82DC9CF3219D724E6369AA044FD630B9C5E178',
'(16)7777-7777',
						null);