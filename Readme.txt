Server

Maven
1. Dal terminale andiamo alla cartella del progetto
2. Eseguiamo il seguente comando:
⁃ mvn exec:java -Dexec.mainClass="serverCV.Server"
3. Ci saranno richiesti le credenziali di accesso al DB
⁃ Username: postgres
⁃ Password: pass
4. Il server sará pronto


Mac - Windows
1. Dal terminale andiamo alla cartella del progetto
2. Andiamo alla cartella target
3. Eseguiamo il seguente commando
⁃ java -jar Server.jar
4. Ci saranno richiesti le credenziali di accesso al DB
⁃ Username: postgres
⁃ Password: pass
5. Il server sará pronto


Client

Una volta che il Server è pronto, possiamo lanciare il client

Maven
1. Dal terminale andiamo alla cartella del progetto
2. Eseguiamo il seguente comando:
⁃ mvn exec:java -Dexec.mainClass="clientCV.Launcher"

Mac
1. Dal terminale andiamo alla cartella del progetto
2. Andiamo alla cartella target
3. Eseguiamo il seguente commando
⁃ java -jar LaboratorioB.jar
4. Applicazione pronta

Nella cartella del progetto si trova un file chiamato "Utenti per Test.txt" che contiene diversi utenti per testare l'applicazione.

