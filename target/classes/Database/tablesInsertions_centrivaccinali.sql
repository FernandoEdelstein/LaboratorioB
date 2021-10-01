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

INSERT INTO sintomi VALUES(0, 'Mal di testa', 'Dolori, giramenti di testa e cefalee costanti nella giornata');
INSERT INTO sintomi VALUES(1, 'Dolori intestinali', 'Forti dolori nella zona dell''addome e/o dell''intestino');
INSERT INTO sintomi VALUES(2, 'Forte spossatezza', 'Debolezza, fatica e sonnolenza durante tutta la giornata');
INSERT INTO sintomi VALUES(3, 'Mancamenti', 'Mancamenti avvenuti dopo la somministrazione del vaccino');
INSERT INTO sintomi VALUES(4, 'Nausea', 'Nausea forte e costante durante tutta la giornata, necessità di rimettere');

INSERT INTO idunivoci VALUES(3782, 'GLKMRL32F02F423Y', 'malpensa fiere');
INSERT INTO idunivoci VALUES(437, 'GTFTTPN56R34L642L', 'malpensa fiere');
INSERT INTO idunivoci VALUES(394, 'TLRFEF76E93L243N', 'lariofiere');
INSERT INTO idunivoci VALUES(2387, 'BNNTLP43F11N584P', 'asst sette laghi');
INSERT INTO idunivoci VALUES(821, 'MDRLPP52W31B903L', 'rancio valcuvia');
INSERT INTO idunivoci VALUES(1324, 'FNERNE21L49W039Y', 'rancio valcuvia');
INSERT INTO idunivoci VALUES(742, 'PLRNDS90L21N342P', 'rancio valcuvia');
INSERT INTO idunivoci VALUES(4521, 'POMMRE33N82G349M', 'vaccination center');
INSERT INTO idunivoci VALUES(643, 'HRDGNH23Y73C938U', 'vaccination center');
INSERT INTO idunivoci VALUES(8421, 'FEPWRH88K00O302J', 'vaccination center');

INSERT INTO utenti VALUES('ferna', 'f', 'DLSFNN99E02Z600X', 'fernando', 'edelstein');
INSERT INTO utenti VALUES('eli', 'e', 'MTDDFF39A05U192U', 'eliana', 'monteleone');
INSERT INTO utenti VALUES('elena', 'e', 'FNNTDO21E02P423L', 'elena', 'lago');
INSERT INTO utenti VALUES('giuly', 'g', 'FRMSPW43A01P223L', 'giulia', 'galparoli');

INSERT INTO utenti VALUES('matte', 'm', 'GLKMRL32F02F423Y', 'matteo', 'effimeri');
INSERT INTO utenti VALUES('roccodb', 'r', 'GTFTTPN56R34L642L', 'rocco', 'de bueno');
INSERT INTO utenti VALUES('michi', 'ml', 'TLRFEF76E93L243N', 'michela', 'liuzzo');
INSERT INTO utenti VALUES('marce', 'me', 'BNNTLP43F11N584P', 'marcelo', 'edelstein');
INSERT INTO utenti VALUES('thachab', 'chab', 'MDRLPP52W31B903L', 'martin', 'chab');
INSERT INTO utenti VALUES('elibie', 'eb', 'FNERNE21L49W039Y', 'elisa', 'bielli');
INSERT INTO utenti VALUES('serife', 'serfe', 'PLRNDS90L21N342P', 'serena', 'fedrigoni');
INSERT INTO utenti VALUES('eddie', 'ec', 'POMMRE33N82G349M', 'eduard', 'chiriac');
INSERT INTO utenti VALUES('phili', 'firthoffifth', 'HRDGNH23Y73C938U', 'phil', 'collins');
INSERT INTO utenti VALUES('violet', 'viola', 'FEPWRH88K00O302J', 'viola', 'mattioni');

INSERT INTO cittadinivaccinati VALUES('matte', 'matteo.e@gmail.com', 3782);
INSERT INTO cittadinivaccinati VALUES('roccodb', 'roccodb@gmail.com', 437);
INSERT INTO cittadinivaccinati VALUES('michi', 'michi-liu@icloud.com', 394);
INSERT INTO cittadinivaccinati VALUES('marce', 'marce.edelstein72@outlook.com', 2387);
INSERT INTO cittadinivaccinati VALUES('thachab', 'thachab@icloud.com', 821);
INSERT INTO cittadinivaccinati VALUES('elibie', 'elisabielli@virgilio.it', 1324);
INSERT INTO cittadinivaccinati VALUES('serife', 'seri.fedri@outlook.com', 742);
INSERT INTO cittadinivaccinati VALUES('eddie', 'theeduard@gmail.com', 4521);
INSERT INTO cittadinivaccinati VALUES('phili', 'philcollins@genesis.com', 643);
INSERT INTO cittadinivaccinati VALUES('violet', 'violamattioni99@gmail.com', 8421);

INSERT INTO vaccinati_malpensa_fiere VALUES('matteo', 'effimeri', 'GLKMRL32F02F423Y', '2021-04-22', 'PFIZER', 3782);
INSERT INTO vaccinati_malpensa_fiere VALUES('rocco', 'de bueno', 'GTFTTPN56R34L642L', '2021-03-21', 'MODERNA', 437);
INSERT INTO vaccinati_lariofiere VALUES('michela', 'liuzzo', 'TLRFEF76E93L243N', '2021-03-12', 'MODERNA', 394);
INSERT INTO vaccinati_asst_sette_laghi VALUES('marcelo', 'edelstein', 'BNNTLP43F11N584P', '2021-05-01', 'JOHNSONANDJOHNSON', 2387);
INSERT INTO vaccinati_rancio_valcuvia VALUES('martin', 'chab', 'MDRLPP52W31B903L', '2021-03-14', 'PFIZER', 821);
INSERT INTO vaccinati_rancio_valcuvia VALUES('elisa', 'bielli', 'FNERNE21L49W039Y', '2021-05-04', 'PFIZER', 1324);
INSERT INTO vaccinati_rancio_valcuvia VALUES('serena', 'fedrigoni', 'PLRNDS90L21N342P', '2021-03-05', 'ASTRAZENECA', 742);
INSERT INTO vaccinati_vaccination_center VALUES('eduard', 'chiriac', 'POMMRE33N82G349M', '2021-05-12', 'MODERNA', 4521);
INSERT INTO vaccinati_vaccination_center VALUES('phil', 'collins', 'HRDGNH23Y73C938U', '2021-04-03', 'JOHNSONANDJOHNSON', 643);
INSERT INTO vaccinati_vaccination_center VALUES('viola', 'mattioni', 'FEPWRH88K00O302J', '2021-03-15', 'ASTRAZENECA', 8421);

INSERT INTO segnalazioni VALUES(58472, 0, 'matte', 'malpensa fiere', 5, 'Non ho dormito');
INSERT INTO segnalazioni VALUES(69382, 0, 'roccodb', 'malpensa fiere', 2, 'Solo un po di dolore');
INSERT INTO segnalazioni VALUES(03928, 1, 'michi', 'lariofiere', 4, 'Forte male alla pancia, sono stata senza mangiare durante una giornata');
INSERT INTO segnalazioni VALUES(57294, 1, 'marce', 'asst sette laghi', 5, 'Mi faceva malissimo la pancia, non potevo muovermi');
INSERT INTO segnalazioni VALUES(91823, 2, 'thachab', 'rancio valcuvia', 2, 'Un po'' debole il giorno dopo');
INSERT INTO segnalazioni VALUES(13245, 4, 'elibie', 'rancio valcuvia', 2, 'Rimesso qualche ora dopo il vaccino');
INSERT INTO segnalazioni VALUES(28374, 2, 'serife', 'rancio valcuvia', 3, 'Sonnolenza dopo la vaccinazione durante tutta la giornata');
INSERT INTO segnalazioni VALUES(65748, 3, 'eddie', 'vaccination center', 4, 'Mancamento subito dopo aver fatto il vaccino');
INSERT INTO segnalazioni VALUES(78675, 1, 'phili', 'vaccination center', 3, 'Debolezza per tutta la giornata, dopo aver dormito mi sono rimesso');
INSERT INTO segnalazioni VALUES(37552, 0, 'violet', 'vaccination center', 4, 'Dolori fortissimi!');