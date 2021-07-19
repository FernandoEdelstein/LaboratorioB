CREATE TABLE centrivaccinali (
	  nome varchar PRIMARY KEY,
	  tipologia varchar NOT NULL,
	  qualificatore varchar NOT NULL,
	  strada varchar NOT NULL,
	  civico varchar NOT NULL,
	  comune varchar NOT NULL,
	  provincia varchar NOT NULL,
	  cap varchar NOT NULL
);


CREATE TABLE idunivoci (
	  idvaccinazione SMALLINT PRIMARY KEY,
	  codicefiscale varchar UNIQUE
);

CREATE TABLE utenti (
	  userid varchar PRIMARY KEY,
	  pass varchar NOT NULL,
	  codicefiscale varchar NOT NULL,
	  nome varchar NOT NULL,
	  cognome varchar NOT NULL
);

CREATE TABLE cittadinivaccinati (
	  userid varchar PRIMARY KEY,
	  email varchar NOT NULL,
	  idvaccinazione SMALLINT NOT NULL,
	        FOREIGN KEY(userid) REFERENCES utenti(userid),
	        FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione)
);

CREATE TABLE eventiavversi (
	  idevento SERIAL PRIMARY KEY,
	  sintomo varchar NOT NULL,
	  descrizione varchar NOT NULL
);


CREATE TABLE segnalazioni (
      idsegnalazione SERIAL PRIMARY KEY,
	  idevento SMALLINT,
	  userid varchar,
	  centrovaccinale varchar,
	  severita SMALLINT CHECK(severita BETWEEN 1 AND 5) NOT NULL,
	  descrizione varchar(256),
	       FOREIGN KEY(idevento) REFERENCES eventiavversi(idevento),
           FOREIGN KEY(userid) REFERENCES cittadinivaccinati(userid),
	       FOREIGN KEY(centrovaccinale) REFERENCES centrivaccinali(nome)
);