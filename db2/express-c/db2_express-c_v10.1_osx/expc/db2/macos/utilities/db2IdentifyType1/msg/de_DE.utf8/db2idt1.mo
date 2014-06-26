 D         T      I   T      �  T      U  T      �  T      �  S      �  T   	   �  T   e   �  T   f   }  T   g     S   h   �  S   i   1  S   j   �  S   k   S	  S   l   �	  T   m   �
  S   n     S   �   �  T   �   �  T   �   �  S   �   8  T   �   �  T   �   0  S   �   �  S   �   S  T   �   �  T    Tool db2IdentifyType1 - Tool zum Identifizieren von Indizes des Typs 1  Mit db2IdentifyType1 können Indizes des Typs 1 identifiziert werden. Dieser Befehl generiert in einer Ausgabedatei die entsprechenden REORG INDEXES ALL-Befehle mit den Klauseln ALLOW WRITE ACCESS und CONVERT, mit denen Sie Indizes des Typs 1 für eine angegebene Datenbank in Indizes des Typs 2 umwandeln können. 
SYNTAX: db2IdentifyType1 -d <datenbank>
                         -o <dateiname>
                        [-s <schemaname>]
                        [-t <tabellenname>]
        oder
        db2IdentifyType1 -h
 Ein Datenbankname und ein Dateiname sind erforderlich. Bei Angabe der Option -h werden diese Hilfeinformationen angezeigt.  REORG INDEXES-Anweisungen können für alle Tabellen in der Datenbank, für alle Tabellen mit einem angegebenen Schema oder für alle Tabellen mit einem angegebenen Namen erstellt werden. DBT3007I In den überprüften Tabellen wurden Indizes des Typs 1 gefunden. REORG INDEXES ALL-Befehle mit den Klauseln ALLOW WRITE ACCESS und CONVERT wurden in der Befehlsdatei %1S generiert. Indizes für typisierte Tabellen in Datenbanken der Version 8 wurden nicht geprüft.  DBT3008I Die Indizes in der angegebenen Datenbank oder Tabelle oder im angegebenen Schema sind bereits Indizes des Typs 2. Indizes für typisierte Tabellen in Datenbanken der Version 8 wurden nicht geprüft. Es wurde keine Ausgabe generiert. DBT3009I Für die überprüften Tabellen gibt es keine Indizes. Keine Indexumwandlung erforderlich. DBT3101E Für den Parameter -d wurde kein Datenbankname angegeben. Korrigieren Sie die Syntax und führen Sie den Befehl erneut aus. DBT3102E Für den Parameter -o wurde kein Ausgabedateiname angegeben. Korrigieren Sie die Syntax und führen Sie den Befehl erneut aus. DBT3103E Für den folgenden Parameter wurde kein Wert angegeben: '-%1S'. Geben Sie den fehlenden Wert an und führen Sie den Befehl erneut aus. DBT3104E Der folgende Parameter wurde mehrmals angegeben: '-%1S'. Entfernen Sie den bzw. die zusätzlichen Parameter und führen Sie den Befehl erneut aus. DBT3105E Der Wert für den folgenden Parameter ist zu lang: '-%1S'. Geben Sie einen kürzeren Wert ein und führen Sie den Befehl erneut aus. DBT3106E Der folgende Parameter ist kein gültiger Parameter: '-%1S'. Geben Sie einen gültigen Parameter an und führen Sie den Befehl erneut aus. DBT3107E Für den folgenden Parameter kann nur ein Wert angegeben werden: '-%1S'. Entfernen Sie die  zusätzlichen Werte und führen Sie den Befehl erneut aus. DBT3108I Der Versionsstand dieser Datenbank wird von db2IdentifyType1 nicht unterstützt. Nur Datenbanken der Version 8 oder später können überprüft werden. DBT3109E Das folgende Schema wurde nicht gefunden: '%1S'. Korrigieren Sie den Schemanamen und führen Sie den Befehl erneut aus. DBT3110E Die folgende Tabelle wurde nicht gefunden: '%1S'. Korrigieren Sie den Tabellennamen und führen Sie den Befehl erneut aus. DBT3201E Der Befehl db2IdentifyType1 konnte keine Umgebungskennung zuordnen. DBT3202E Der Befehl db2IdentifyType1 konnte keine Verbindungskennung zuordnen. Weitere Informationen finden Sie in der Protokolldatei db2IdentifyType1.err. DBT3203E Beim Versuch, die Verbindung zur folgenden Datenbank herzustellen, trat ein Problem auf: '%1S'. Weitere Informationen finden Sie in der Protokolldatei db2IdentifyType1.err. DBT3204E Die Benutzerberechtigung konnte nicht ermittelt werden. Weitere Informationen finden Sie in der Protokolldatei db2IdentifyType1.err. DB23205E Der Befehl muss von einer Benutzer-ID mit der Berechtigung DBADM oder SYSADM ausgeführt werden. DBT3206E Der Befehl db2IdentifyType1 stellte bei der Kommunikation mit der folgenden Datenbank ein Problem fest: '%1S'. Weitere Informationen finden Sie in der Protokolldatei db2IdentifyType1.err. DBT3207E Der Befehl db2IdentifyType1 konnte nicht in die Datei mit dem Namen '%1S' schreiben. DBT3208E Der Befehl db2IdentifyType1 konnte keine Anweisungskennung zuordnen. Weitere Informationen finden Sie in der Protokolldatei db2IdentifyType1.err. DBT3209E Fehler beim Laden des Moduls: '%s'. SQLCODE = %d. 