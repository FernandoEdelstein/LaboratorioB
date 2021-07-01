INSERT INTO centrivaccinali VALUES('speranza', 'HUB', 'VIA', 'Ugo Foscolo', '41/B', 'Lainate', 'MI', 20020);
CREATE TABLE vaccinati_speranza (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));
INSERT INTO centrivaccinali VALUES('centro verde', 'OSPEDALIERO', 'VIALE', 'Resegone', '127', 'Lainate', 'MI', 20020);
CREATE TABLE vaccinati_centro_verde(nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));
INSERT INTO centrivaccinali VALUES('orsolini centre', 'AZIENDALE', 'VIA', 'Raffaello Sanzio', '84/A', 'Rho', 'MI', 20017);
CREATE TABLE vaccinati_orsolini_centre (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));
INSERT INTO centrivaccinali VALUES('centro blu', 'HUB', 'PIAZZA', 'Martinetti', '41/B', 'Gazzada', 'VA', 21045);
CREATE TABLE vaccinati_centro_blu (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));
INSERT INTO centrivaccinali VALUES('vpoint', 'OSPEDALIERO', 'CORSO', 'Europa', '188', 'Milano', 'MI', 20122);
CREATE TABLE vaccinati_vpoint (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO idunivoci VALUES(152, 'GLLMHL81B01F205J');
INSERT INTO idunivoci VALUES(80, 'TLESFN84C03L682W');
INSERT INTO idunivoci VALUES(2108, 'BLDNNA84C43C933T');
INSERT INTO idunivoci VALUES(1560, 'GZZRLN97E44E507Z');
INSERT INTO idunivoci VALUES(855, 'PZZGCM00R11A794P');
INSERT INTO idunivoci VALUES(9023, 'BNCGPP63T19F205P');
INSERT INTO idunivoci VALUES(12, 'MLRSRA71M63F205F');
INSERT INTO idunivoci VALUES(7632, 'MNGGLI74R63G169J');
INSERT INTO idunivoci VALUES(11102, 'LVRMRC72L18C351I');
INSERT INTO idunivoci VALUES(3782, 'RZZLNR42P49H501H');

INSERT INTO utenti VALUES('mike', 'a', 'GLLMHL81B01F205J', 'michele', 'galloni');
INSERT INTO utenti VALUES('nicco', 'n', 'SLANCL99A01F205D', 'niccolò', 'sala');
INSERT INTO utenti VALUES('gio', 'g', 'MGDGNN99A01F205U', 'giovanni', 'magaudda');
INSERT INTO utenti VALUES('teo', 't', 'FRNMTT99A01F205T', 'matteo', 'franchi');
INSERT INTO utenti VALUES('tosi', 'pw', 'TSODVD85E04L682K', 'davide', 'tosi');
INSERT INTO utenti VALUES('stefanoo', 'a', 'TLESFN84C03L682W', 'stefano', 'teoli');
INSERT INTO utenti VALUES('anna78', 'annetta78', 'BLDNNA84C43C933T', 'anna', 'balada');
INSERT INTO utenti VALUES('rosalinda.agazzi', 'Rosa_agazzi7', 'GZZRLN97E44E507Z', 'rosalinda', 'agazzi');
INSERT INTO utenti VALUES('pjack', 'JCK79sinatra', 'PZZGCM00R11A794P', 'giacomo', 'pozzuolo');
INSERT INTO utenti VALUES('bpeppo63', 'giuseppestefania6365', 'BNCGPP63T19F205P', 'giuseppe', 'bianchi');
INSERT INTO utenti VALUES('malisara', 'sarEttaM71', 'MLRSRA71M63F205F', 'sara', 'maliero');
INSERT INTO utenti VALUES('giuliamango', 'giulsSsS74', 'MNGGLI74R63G169J', 'giulia', 'mangoni');
INSERT INTO utenti VALUES('marco.oliviero', 'oli-Marc0', 'LVRMRC72L18C351I', 'marco', 'oliviero');
INSERT INTO utenti VALUES('eleonorazzi', 'Briciola42', 'RZZLNR42P49H501H', 'eleonora', 'razzi');

INSERT INTO cittadinivaccinati VALUES('mike', 'micheee81@gmail.com', 152);
INSERT INTO cittadinivaccinati VALUES('stefanoo', 'stexteoli1984@gmail.com', 80);
INSERT INTO cittadinivaccinati VALUES('anna78', 'bebannetta@outlook.com', 2108);
INSERT INTO cittadinivaccinati VALUES('rosalinda.agazzi', 'pricess97rosaaa@gmail.com', 1560);
INSERT INTO cittadinivaccinati VALUES('pjack', 'micheee81@outlook.com', 855);
INSERT INTO cittadinivaccinati VALUES('bpeppo63', 'peppobianchi@virgilio.it', 9023);
INSERT INTO cittadinivaccinati VALUES('malisara', 'sara.maliero@yahoo.com', 12);
INSERT INTO cittadinivaccinati VALUES('giuliamango', 'giulsssmanggg@gmail.com', 7632);
INSERT INTO cittadinivaccinati VALUES('marco.oliviero', 'olivandviero7and2@gmail.com', 11102);
INSERT INTO cittadinivaccinati VALUES('eleonorazzi', 'ele.briciola.razzi@gmail.com', 3782);

INSERT INTO vaccinati_speranza VALUES('michele', 'galloni', 'GLLMHL81B01F205J', '2021-03-20', 'JANDJ', 152);
INSERT INTO vaccinati_speranza VALUES('stefano', 'teoli', 'TLESFN84C03L682W', '2021-03-22', 'MODERNA', 80);
INSERT INTO vaccinati_centro_verde VALUES('anna', 'balada', 'BLDNNA84C43C933T', '2021-03-20', 'PFIZER', 2108);
INSERT INTO vaccinati_orsolini_centre VALUES('rosalinda', 'agazzi', 'GZZRLN97E44E507Z', '2021-03-27', 'ASTRAZENECA', 1560);
INSERT INTO vaccinati_centro_blu VALUES('giacomo', 'pozzuolo', 'PZZGCM00R11A794P', '2021-03-28', 'ASTRAZENECA', 855);
INSERT INTO vaccinati_centro_blu VALUES('giuseppe', 'bianchi', 'BNCGPP63T19F205P', '2021-04-08', 'PFIZER', 9023);
INSERT INTO vaccinati_centro_blu VALUES('sara', 'maliero', 'MLRSRA71M63F205F', '2021-04-08', 'ASTRAZENECA', 12);
INSERT INTO vaccinati_vpoint VALUES('giulia', 'mangoni', 'MNGGLI74R63G169J', '2021-04-12', 'MODERNA', 7632);
INSERT INTO vaccinati_vpoint VALUES('marco', 'oliviero', 'LVRMRC72L18C351I', '2021-04-19', 'JANDJ', 11102);
INSERT INTO vaccinati_vpoint VALUES('eleonora', 'razzi', 'RZZLNR42P49H501H', '2021-05-04', 'ASTRAZENECA', 3782);

INSERT INTO eventiavversi VALUES(0, 'Mal di testa', 'Dolori e giramenti di testa, emicranie e cefalee costanti nella giornata');
INSERT INTO eventiavversi VALUES(1, 'Dolori intestinali', 'Dolori prolunxgati all''altezza dell''addome e/o dell''intestino');
INSERT INTO eventiavversi VALUES(2, 'Forte spossatezza', 'Debolezza di acuta intensità, fatica a svolgere le azioni quotidiane, sonnolenza');
INSERT INTO eventiavversi VALUES(3, 'Mancamenti', 'Mancamenti avvenuti dopo la somministrazione della dose del vaccino');
INSERT INTO eventiavversi VALUES(4, 'Nausea', 'Nausea consistente e costante durante tutto l''arco della giornata, necessità di rimettere');

INSERT INTO segnalazioni VALUES(0, 0, 'mike', 'speranza', 3, 'Forte mal di testa per tutta la giornata');
INSERT INTO segnalazioni VALUES(1, 1, 'stefanoo', 'speranza', 4, 'Dolore alla pancia');
INSERT INTO segnalazioni VALUES(2, 1, 'anna78', 'centro verde', 2, 'Forte male alla pancia');
INSERT INTO segnalazioni VALUES(3, 0, 'rosalinda.agazzi', 'orsolini centre', 5, 'Male alla testa prolungato tutta la giornata!');
INSERT INTO segnalazioni VALUES(4, 2, 'pjack', 'centro blu', 1, 'Un po'' debole dopo il vaccino');
INSERT INTO segnalazioni VALUES(5, 4, 'bpeppo63', 'centro blu', 4, 'Rimesso dopo vaccino');
INSERT INTO segnalazioni VALUES(6, 2, 'malisara', 'centro blu', 2, 'Sonnolenza dopo la vaccinazione');
INSERT INTO segnalazioni VALUES(7, 3, 'giuliamango', 'vpoint', 5, 'Mancamento appena dopo la somministrazione, fortunatamento ero ancora al centro');
INSERT INTO segnalazioni VALUES(8, 2, 'marco.oliviero', 'vpoint', 5, 'Debolezza per tutta la giornata, grande difficoltà a fare qualsiasi cosa');
INSERT INTO segnalazioni VALUES(9, 1, 'eleonorazzi', 'vpoint', 3, 'Dolori alla pancia ripetuti');