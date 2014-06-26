 D         T      [   T      �  T      [  T      �  T      �  S      �  T   	   �  T   e     T   f   �  T   g     S   h   �  S   i   %  S   j   �  S   k   3	  S   l   �	  T   m   p
  S   n   �
  S   �   V  T   �   �  T   �   U  S   �     T   �   �  T   �   �  S   �   �  S   �     T   �   �  T    Ferramenta db2IdentifyType1 - Comando da ferramenta de identificação do índice Tipo-1  db2IdentifyType1 é utilizado para identificar índices de tipo-1. Ele gera, em um arquivo de saída, os comandos REORG INDEXES ALL adequados com as cláusulas ALLOW WRITE ACCESS e CONVERT que você pode utilizar para converter índices de tipo-1 em índices de tipo-2 para um banco de dados especificado. 
SINTAXE: db2IdentifyType1 -d <database>
                         -o <file name>
                        [-s <schema name>]
                        [-t <table name>]
        ou
        db2IdentifyType1 -h
 Um nome de banco de dados e um nome de arquivo são necessários. Se a opção -h for especificada, essas informações de ajuda serão exibidas.  As instruções REORG INDEXES podem ser criadas para todas as tabelas no banco de dados, todas as tabelas com um esquema especificado ou todas as tabelas com um nome especificado. DBT3007I Índices de Tipo-1 foram localizados nas tabelas inspecionadas. Os comandos REORG INDEXES ALL com as cláusulas ALLOW WRITE ACCESS e CONVERT foram gerados no seguinte arquivo de comando: %1S. Os índices nas tabelas digitadas nos bancos de dados da Versão 8 não foram verificados. DBT3008I Os índices no banco de dados, esquema ou tabela especificados já são índices de tipo-2. Os índices nas tabelas digitadas nos bancos de dados da Versão 8 não foram verificados. Nenhuma saída foi gerada. DBT3009I Não há índices nas tabelas inspecionadas. Nenhuma conversão de índice é necessária. DBT3101E Nenhum nome de banco de dados foi especificado para o parâmetro -d. Corrija a sintaxe e execute o comando novamente. DBT3102E Nenhum nome de arquivo de saída foi especificado para o parâmetro -o. Corrija a sintaxe e execute o comando novamente. DBT3103E Nenhum valor foi especificado para o seguinte parâmetro: '-%1S'. Especifique o valor ausente e execute o comando novamente. DBT3104E O parâmetro a seguir foi especificado mais de uma vez: '-%1S'. Remova o parâmetro ou parâmetros adicional(is) e execute o comando novamente. DBT3105E O valor para o parâmetro a seguir é muito longo: '-%1S'. Forneça um valor mais curto e execute o comando novamente. -DBT3106E O parâmetro a seguir não é um dos parâmetros válidos: '-%1S'. Especifique um parâmetro válido e execute o comando novamente. DBT3107E Somente um valor pode ser especificado para o seguinte parâmetro: '-%1S'. Remova os valores adicionais e execute o comando novamente. DBT3108I O nível da versão deste banco de dados não é suportado pelo db2IdentifyType1. Somente os bancos de dados da Versão 8 ou mais recentes podem ser inspecionados. DBT3109E O esquema a seguir não foi localizado: '%1S'. Corrija o nome do esquema e execute o comando novamente. DBT3110E A tabela a seguir não pôde ser localizada: '%1S'. Corrija o nome da tabela e execute o comando novamente. DBT3201E O comando db2IdentifyType1 não conseguiu alocar o identificador de ambientes.  DBT3202E O comando db2IdentifyType1 não conseguiu alocar um identificador de conexões. Revise o arquivo de logs db2IdentifyType1.err para obter mais informações. DBT3203E Foi encontrado um problema durante uma tentativa de conexão ao seguinte banco de dados: '%1S'. Revise o arquivo de logs db2IdentifyType1.err para obter mais informações. DBT3204E Não foi possível determinar a autorização do usuário. Revise o arquivo de logs db2IdentifyType1.err para obter mais informações. DB23205E O comando deve ser executado por um ID de usuário com autoridade DBADM ou SYSADM. DBT3206E O comando db2IdentifyType1 encontrou um problema ao comunicar-se com o seguinte banco de dados: '%1S'. Revise o arquivo de logs db2IdentifyType1.err para obter mais informações. DBT3207E O comando db2IdentifyType1 não conseguiu gravar no arquivo denominado '%1S'. DBT3208E O comando db2IdentifyType1 não conseguiu alocar um identificador de instrução. Revise o arquivo de logs db2IdentifyType1.err para obter mais informações. DBT3209E Falha ao carregar módulo: '%s'. SQLCODE = %d. 