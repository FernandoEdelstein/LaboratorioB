INSERT INTO centrivaccinali VALUES('malpensa fiere', 'HUB', 'VIA', 'XI Settembre', '16', 'Busto Arsizio', 'VA', 21052);
CREATE TABLE vaccinati_malpensa_fiere (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO centrivaccinali VALUES('lariofiere', 'HUB', 'VIALE', 'Resegone', '9', 'Erba', 'CO', 22036);
CREATE TABLE vaccinati_lariofiere(nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO centrivaccinali VALUES('asst sette laghi', 'OSPEDALIERO', 'VIA', 'Bordini', '9/A', 'Angera', 'VA', 21021);
CREATE TABLE vaccinati_asst_sette_laghi (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO centrivaccinali VALUES('rancio valcuvia', 'AZIENDALE', 'VIA', 'Provinciale', '13', 'Cuveglio', 'VA', 21030);
CREATE TABLE vaccinati_rancio_valcuvia (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO centrivaccinali VALUES('vaccination center', 'AZIENDALE', 'VIA', 'Giuseppe Pecchio', '19', 'Milano', 'MI', 20131);
CREATE TABLE vaccinati_vaccination_center (nomecittadino varchar(50), cognomecittadino varchar(50), codicefiscale varchar(50), data DATE, vaccino varchar(20), idvaccinazione SMALLINT, PRIMARY KEY(codicefiscale), FOREIGN KEY(idvaccinazione) REFERENCES idunivoci(idvaccinazione), FOREIGN KEY(codicefiscale) REFERENCES idunivoci(codicefiscale));

INSERT INTO eventiavversi VALUES(0, 'Mal di testa', 'Dolori e giramenti di testa, emicranie e cefalee costanti nella giornata');
INSERT INTO eventiavversi VALUES(1, 'Dolori intestinali', 'Dolori prolunxgati all''altezza dell''addome e/o dell''intestino');
INSERT INTO eventiavversi VALUES(2, 'Forte spossatezza', 'Debolezza di acuta intensità, fatica a svolgere le azioni quotidiane, sonnolenza');
INSERT INTO eventiavversi VALUES(3, 'Mancamenti', 'Mancamenti avvenuti dopo la somministrazione della dose del vaccino');
INSERT INTO eventiavversi VALUES(4, 'Nausea', 'Nausea consistente e costante durante tutto l''arco della giornata, necessità di rimettere');

INSERT INTO idunivoci VALUES(3782, 'GLLMHL81B01F205J');
INSERT INTO idunivoci VALUES(437, 'TLESFN84C03L682W');
INSERT INTO idunivoci VALUES(394, 'BLDNNA84C43C933T');
INSERT INTO idunivoci VALUES(2387, 'GZZRLN97E44E507Z');
INSERT INTO idunivoci VALUES(821, 'PZZGCM00R11A794P');
INSERT INTO idunivoci VALUES(1324, 'BNCGPP63T19F205P');
INSERT INTO idunivoci VALUES(742, 'MLRSRA71M63F205F');
INSERT INTO idunivoci VALUES(4521, 'MNGGLI74R63G169J');
INSERT INTO idunivoci VALUES(643, 'LVRMRC72L18C351I');
INSERT INTO idunivoci VALUES(8421, 'RZZLNR42P49H501H');

INSERT INTO utenti VALUES('ferna', 'f', 'SLANCL99A01F205D', 'fernando', 'edelstein');
INSERT INTO utenti VALUES('eli', 'e', 'MGDGNN99A01F205U', 'eliana', 'monteleone');
INSERT INTO utenti VALUES('elena', 'e', 'TSODVD85E04L682K', 'elena', 'lago');
INSERT INTO utenti VALUES('giuly', 'g', 'FRNMTT99A01F205T', 'giulia', 'galparoli');

INSERT INTO utenti VALUES('matte', 'm', 'GLLMHL81B01F205J', 'matteo', 'effimeri');
INSERT INTO utenti VALUES('roccodb', 'r', 'TLESFN84C03L682W', 'rocco', 'de bueno');
INSERT INTO utenti VALUES('michi', 'ml', 'BLDNNA84C43C933T', 'michela', 'liuzzo');
INSERT INTO utenti VALUES('marce', 'me', 'GZZRLN97E44E507Z', 'marcelo', 'edelstein');
INSERT INTO utenti VALUES('thachab', 'chab', 'PZZGCM00R11A794P', 'martin', 'chab');
INSERT INTO utenti VALUES('bpeppo63', 'giuseppestefania6365', 'BNCGPP63T19F205P', 'giuseppe', 'bianchi');
INSERT INTO utenti VALUES('malisara', 'sarEttaM71', 'MLRSRA71M63F205F', 'sara', 'maliero');
INSERT INTO utenti VALUES('giuliamango', 'giulsSsS74', 'MNGGLI74R63G169J', 'giulia', 'mangoni');
INSERT INTO utenti VALUES('marco.oliviero', 'oli-Marc0', 'LVRMRC72L18C351I', 'marco', 'oliviero');
INSERT INTO utenti VALUES('eleonorazzi', 'Briciola42', 'RZZLNR42P49H501H', 'eleonora', 'razzi');

INSERT INTO cittadinivaccinati VALUES('matte', 'matteo.e@gmail.com', 3782);
INSERT INTO cittadinivaccinati VALUES('roccodb', 'roccodb@gmail.com', 437);
INSERT INTO cittadinivaccinati VALUES('michi', 'michi-liu@icloud.com', 394);
INSERT INTO cittadinivaccinati VALUES('marce', 'marce.edelstein72@outlook.com', 2387);
INSERT INTO cittadinivaccinati VALUES('thachab', 'thachab@icloud.com', 821);
INSERT INTO cittadinivaccinati VALUES('bpeppo63', 'peppobianchi@virgilio.it', 1324);
INSERT INTO cittadinivaccinati VALUES('malisara', 'sara.maliero@yahoo.com', 742);
INSERT INTO cittadinivaccinati VALUES('giuliamango', 'giulsssmanggg@gmail.com', 4521);
INSERT INTO cittadinivaccinati VALUES('marco.oliviero', 'olivandviero7and2@gmail.com', 643);
INSERT INTO cittadinivaccinati VALUES('eleonorazzi', 'ele.briciola.razzi@gmail.com', 8421);

INSERT INTO vaccinati_malpensa_fiere VALUES('matteo', 'effimeri', 'GLLMHL81B01F205J', '2021-03-20', 'PFIZER', 3782);
INSERT INTO vaccinati_malpensa_fiere VALUES('rocco', 'de bueno', 'TLESFN84C03L682W', '2021-03-22', 'MODERNA', 437);
INSERT INTO vaccinati_lariofiere VALUES('michela', 'liuzzo', 'BLDNNA84C43C933T', '2021-03-20', 'MODERNA', 394);
INSERT INTO vaccinati_asst_sette_laghi VALUES('marcelo', 'edelstein', 'GZZRLN97E44E507Z', '2021-03-27', 'JOHNSONANDJOHNSON', 2387);
INSERT INTO vaccinati_rancio_valcuvia VALUES('martin', 'chab', 'PZZGCM00R11A794P', '2021-03-28', 'PFIZER', 821);
INSERT INTO vaccinati_rancio_valcuvia VALUES('giuseppe', 'bianchi', 'BNCGPP63T19F205P', '2021-04-08', 'PFIZER', 1324);
INSERT INTO vaccinati_rancio_valcuvia VALUES('sara', 'maliero', 'MLRSRA71M63F205F', '2021-04-08', 'ASTRAZENECA', 742);
INSERT INTO vaccinati_vaccination_center VALUES('giulia', 'mangoni', 'MNGGLI74R63G169J', '2021-04-12', 'MODERNA', 4521);
INSERT INTO vaccinati_vaccination_center VALUES('marco', 'oliviero', 'LVRMRC72L18C351I', '2021-04-19', 'JOHNSONANDJOHNSON', 643);
INSERT INTO vaccinati_vaccination_center VALUES('eleonora', 'razzi', 'RZZLNR42P49H501H', '2021-05-04', 'ASTRAZENECA', 8421);

INSERT INTO segnalazioni VALUES(58472, 0, 'matte', 'malpensa fiere', 3, 'Forte mal di testa per tutta la giornata');
INSERT INTO segnalazioni VALUES(69382, 1, 'roccodb', 'malpensa fiere', 4, 'Dolore alla pancia');
INSERT INTO segnalazioni VALUES(03928, 1, 'michi', 'lariofiere', 2, 'Forte male alla pancia');
INSERT INTO segnalazioni VALUES(57294, 0, 'marce', 'asst sette laghi', 5, 'Male alla testa prolungato tutta la giornata!');
INSERT INTO segnalazioni VALUES(91823, 2, 'thachab', 'rancio valcuvia', 1, 'Un po'' debole dopo il vaccino');
INSERT INTO segnalazioni VALUES(13245, 4, 'bpeppo63', 'rancio valcuvia', 4, 'Rimesso dopo vaccino');
INSERT INTO segnalazioni VALUES(28374, 2, 'malisara', 'rancio valcuvia', 2, 'Sonnolenza dopo la vaccinazione');
INSERT INTO segnalazioni VALUES(65748, 3, 'giuliamango', 'vaccination center', 5, 'Mancamento appena dopo la somministrazione, fortunatamento ero ancora al centro');
INSERT INTO segnalazioni VALUES(78675, 2, 'marco.oliviero', 'vaccination center', 5, 'Debolezza per tutta la giornata, grande difficoltà a fare qualsiasi cosa');
INSERT INTO segnalazioni VALUES(37552, 1, 'eleonorazzi', 'vaccination center', 3, 'Dolori alla pancia ripetuti');