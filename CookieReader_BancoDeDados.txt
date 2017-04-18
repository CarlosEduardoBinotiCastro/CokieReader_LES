CREATE TABLE Uf (
  sigla          CHAR(2)      NOT NULL,
  nome           VARCHAR(30)  NOT NULL,
  CONSTRAINT pk_uf
    PRIMARY KEY (sigla)
);

CREATE TABLE Cidade (
  cdCidade         INTEGER      NOT NULL,
  nome             VARCHAR(120) NOT NULL,
  sigla            CHAR(2)      NOT NULL,
  CONSTRAINT pk_Cidade
    PRIMARY KEY (cdCidade),
  CONSTRAINT fk_Cidade_Uf
    FOREIGN KEY (sigla)
    REFERENCES Uf(sigla)
);

CREATE TABLE Usuario (
  cdUsuario        SERIAL       NOT NULL,
  nome             VARCHAR(50)  NOT NULL,
  login            VARCHAR(50)  NOT NULL,
  senha            VARCHAR(50)  NOT NULL,
  cpf              CHAR(11)  NOT NULL,
  cdCidade         INTEGER      NOT NULL,
  CONSTRAINT pk_cdUsuario
    PRIMARY KEY (cdUsuario),
  CONSTRAINT fk_Usuario_Cidade
    FOREIGN KEY (cdUsuario)
    REFERENCES Cidade(cdCidade)
);

CREATE TABLE Empresa (
  cdEmpresa        INTEGER      NOT NULL,
  nome             VARCHAR(120) NOT NULL,
  cdCidade         INTEGER      NOT NULL,
  CONSTRAINT pk_Empresa
    PRIMARY KEY (cdEmpresa),
  CONSTRAINT fk_Empresa_Cidade
    FOREIGN KEY (cdCidade)
    REFERENCES Cidade(cdCidade)
);

CREATE TABLE Biscoito (
  cdBiscoito         INTEGER      NOT NULL,
  nome               VARCHAR(120) NOT NULL,
  cdEmpresa          INTEGER      NOT NULL,
  CONSTRAINT pk_Biscoito
    PRIMARY KEY (cdBiscoito),
  CONSTRAINT fk_Biscoito_Empresa
    FOREIGN KEY (cdEmpresa)
    REFERENCES Empresa(cdEmpresa)
);

CREATE TABLE Nutriente (
  cdNutriente         INTEGER      NOT NULL,
  nome                VARCHAR(120) NOT NULL,
  CONSTRAINT pk_Nutriente
    PRIMARY KEY (cdNutriente)
);


CREATE TABLE BiscoitoNutriente (
  cdBiscoito         INTEGER      NOT NULL,              
  cdNutriente        INTEGER      NOT NULL,
  quant              NUMERIC(6,2) NOT NULL,
  CONSTRAINT pk_Biscoito_Nutriente
    PRIMARY KEY (cdBiscoito, cdNutriente),
  CONSTRAINT fk_Biscoito
    FOREIGN KEY (cdBiscoito)
    REFERENCES Biscoito(cdBiscoito),
  CONSTRAINT fk_Nutriente
    FOREIGN KEY (cdNutriente)
    REFERENCES Nutriente(cdNutriente)
);

CREATE TABLE dcnt (
  cdDcnt         INTEGER      NOT NULL,
  nome           VARCHAR(120) NOT NULL,
  CONSTRAINT pk_Dcnt
    PRIMARY KEY (cdDcnt)
);

CREATE TABLE DCNTpeso (
  cdDcnt         INTEGER      NOT NULL,               
  cdNutriente    INTEGER      NOT NULL,
  peso           INTEGER      NOT NULL,
  CONSTRAINT pk_Dcnt_Nutriente
    PRIMARY KEY (cdDcnt, cdNutriente),
  CONSTRAINT fk_dcnt
    FOREIGN KEY (cdDcnt)
    REFERENCES dcnt(cdDcnt),
  CONSTRAINT fk_Nutriente
    FOREIGN KEY (cdNutriente)
    REFERENCES Nutriente(cdNutriente)
);

CREATE TABLE scanBiscoito (
  cdScan         INTEGER      NOT NULL,           
  data           DATE         NOT NULL,
  hora	         TIME         NOT NULL,
  cdDcnt	 INTEGER      NOT NULL,
  cdBiscoito     INTEGER      NOT NULL,
  cdUsuario      INTEGER      NOT NULL,
  CONSTRAINT pk_cdScan
    PRIMARY KEY (cdScan),
  CONSTRAINT fk_dcnt
    FOREIGN KEY (cdDcnt)
    REFERENCES dcnt(cdDcnt),
  CONSTRAINT fk_biscoito
    FOREIGN KEY (cdBiscoito)
    REFERENCES Biscoito(cdBiscoito),
  CONSTRAINT fk_usuario
    FOREIGN KEY (cdUsuario)
    REFERENCES Usuario(cdUsuario)
);
