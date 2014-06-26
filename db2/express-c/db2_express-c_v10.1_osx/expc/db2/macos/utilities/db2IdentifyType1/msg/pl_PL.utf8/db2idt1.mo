 D         T      Q   T      w  T      R  T      �  T      y  S      q  T   	   Q  T   e   �  T   f     T   g     S   h   �  S   i   �  S   j      S   k   �  S   l   	  T   m   �	  S   n   
  S   �   �
  T   �   �
  T   �   k  S   �     T   �   �  T   �     S   �   �  S   �     T   �   �  T    db2IdentifyType1 Tool - Komenda narzędziowa do identyfikacji indeksów typu 1  Komenda db2IdentifyType1 służy do identyfikowania indeksów typu 1. Generuje plik wyjściowy zawierający odpowiednie komendy REORG INDEXES ALL z klauzulami ALLOW WRITE ACCESS i CONVERT, którego można użyć do przekształcenia indeksów typu 1 w indeksy typu 2 w określonej bazie danych. 
SKŁADNIA: db2IdentifyType1 -d <baza danych>
                         -o <nazwa pliku>
                        [-s <nazwa schematu>]
                        [-t <nazwa tabeli>]
        lub
        db2IdentifyType1 -h
 Nazwa bazy danych i nazwa pliku są wymagane. W wypadku podania opcji -h wyświetlana jest niniejsza informacja pomocnicza.  Instrukcje REORG INDEXES mogą zostać utworzone dla wszystkich tabel w bazie danych, dla wszystkich tabel z podanym schematem lub dla wszystkich tabel o podanej nazwie. DBT3007I W zbadanych tabelach znaleziono indeksy typu 1. Komendy REORG INDEXES ALL z klauzulami ALLOW WRITE ACCESS i CONVERT zostały wygenerowane w pliku %1S. Indeksy dla tabel określonego typu w bazach danych z wersji 8 nie zostały sprawdzone. DBT3008I Indeksy w podanej bazie danych, schemacie lub tabeli są już indeksami typu 2. Indeksy dla tabel określonego typu w bazach danych z wersji 8 nie zostały sprawdzone. Nie wygenerowano żadnych danych wyjściowych. DBT3009I Zbadane tabele nie mają indeksów. Nie jest wymagane przekształcanie indeksów. DBT3101E Z parametrem -d nie podano nazwy bazy danych. Popraw składnię i ponownie uruchom komendę. DBT3102E Z parametrem -o nie podano nazwy pliku wyjściowego. Popraw składnię i ponownie uruchom komendę. DBT3103E Nie podano wartości następującego parametru: "-%1S". Podaj brakującą wartość i ponownie uruchom komendę. DBT3104E Następujący parametr podano więcej niż jeden raz: "-%1S". Usuń dodatkowy parametr lub parametry i ponownie uruchom komendę. DBT3105E Wartość następującego parametru jest za długa: "-%1S". Podaj krótszą wartość i ponownie uruchom komendę. DBT3106E Następujący parametr nie należy do zbioru poprawnych parametrów: "-%1S". Podaj poprawny parametr i ponownie uruchom komendę. DBT3107E Dla następującego parametru można podać tylko jedną wartość: "-%1S". Usuń dodatkowe wartości i ponownie uruchom komendę. DBT3108I Bazy danych w tej wersji nie są obsługiwane przez komendę db2IdentifyType1. Badane mogą być tylko bazy danych w wersji 8 lub nowszych. DBT3109E Nie znaleziono następującego schematu: "%1S". Popraw nazwę schematu i ponownie uruchom komendę. DBT3110E Nie znaleziono następującej tabeli "%1S". Popraw nazwę tabeli i ponownie uruchom komendę. DBT3201E Komenda db2IdentifyType1 nie mogła przydzielić uchwytu środowiska. DBT3202E Komenda db2IdentifyType1 nie mogła przydzielić uchwytu połączenia. Więcej informacji można znaleźć w pliku dziennika db2IdentifyType1.err. DBT3203E Napotkano problem przy próbie nawiązania połączenia z następującą bazą danych: "%1S". Więcej informacji można znaleźć w pliku dziennika db2IdentifyType1.err. DBT3204E Nie można określić autoryzacji użytkownika. Więcej informacji można znaleźć w pliku dziennika db2IdentifyType1.err. DB23205E Komenda musi być uruchamiania przez użytkownika, którego ID ma uprawnienie DBADM lub SYSADM. DBT3206E Komenda db2IdentifyType1 napotkała problem podczas komunikacji z następującą bazą danych: '%1S'. Więcej informacji można znaleźć w pliku dziennika db2IdentifyType1.err. DBT3207E Komenda db2IdentifyType1 nie mogła zapisywać do pliku o nazwie "%1S". DBT3208E Komenda db2IdentifyType1 nie mogła przydzielić uchwytu instrukcji. Więcej informacji można znaleźć w pliku dziennika db2IdentifyType1.err. DBT3209E Niepowodzenie przy ładowaniu modułu: '%s'.  SQLCODE = %d. 