# Relatório

# Objetivos

O objetivo do trabalho envolvia a manipulação de data sets. A forma como o mesmo deveria ser manipulado era escolhida pelo aluno e deveria ser uma das opções dadas pela professora.

Foi escolhido o aumento de indivíduos em um data set, onde tais pessoas não poderiam realizar colisões.

Como entrada temos um data set no formato que pode ser visualizado neste repositório (vide arquivo Paths_D_BR1.txt) e como saída temos um data set no mesmo formato mas com mais indivíduos.

# Implementação

Foi utilizado Java para implementar o programa que altera data sets. Após ler o arquivo de entrada o programa define quantos pixels equivalem à um metro para aquele data set (informação presente no início do arquivo) e armazena todas as pessoas e seus trajetos (posição em determinado tempo) em um vetor de Person (classe que armazena os dados e trajeto de uma pessoa).

Para realizar o aumento de indivíduos o programa depende de três variáveis, multDensity, minDistance e maxDistance, que representam, respectivamente, o número de indivíduos extras que irão surgir para cada indivíduo presente no arquivo de entrada, a menor distância, em metros, que estes indivíduos irão surgir de um determinado indivíduo já existente e a distância máxima, em metros, que uma pessoa irá surgir de uma determinada pessoa já existente.

O método raiseDensity(int multDensity, int minDistance e int maxDistance) cria, para cada indivíduo do arquivo original, multDensity novos indivíduo. O trajeto definido para o novo indivíduo é igual ao trajeto de uma pessoa do arquivo inicial, a diferença é que ele estará transladado no universo dentro do intervalo de distância imposto por minDistance e maxDistance. Esta translação é gerada a partir da operação de mod entre um número aleatório e o intervalo de distância multiplicado pela quantidade de pixels que representa um metro e somado com minDistance (para evitar que o novo indivíduo surja à zero de distância).

O método collisionAvoidance() evita que o data set gerado possua colisões. Este método é executado após todos os novos indivíduo serem criados. Para cada indivíduo existente é verificado se em algum determinado frame ele se encontra exatamente na mesma posição que outro indivíduo, se isto ocorre um deles para de prosseguir em seu caminho, aumentando o instante de tempo em que ele realizará seus próximos passos, e deixa o outro passar por aquele ponto para então prosseguir.

A visualização 3D dos trajetos dos indivíduos nos data sets pode ser vista a partir do programa presente na pasta DataAnalysis deste repositório, que agora apresenta a função de visualização além do relatório de eventos entre indivíduos do data set.
