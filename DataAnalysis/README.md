# Relatório

# Objetivos

O objetivo do trabalho consistia em encontrar as ocorrências de três tipos eventos, escolhidos pelo aluno, em vídeos de multidões. Tais vídeos mostravam o comportamento de indivíduos em diferentes países.

Também era necessário a existência de uma demonstração gráfica das trajetórias de cada pessoa existente no arquivo de entrada. Para realizar tal demonstração era obrigatório o uso da biblioteca OpenGL. Infelizmente a versão atual do programa não realiza esta demonstração.

Como entrada para o programa que deveria ser desenvolvido é dado um arquivo de texto que possui a quantidade de pixels que equivalem a um metro e as coordenadas de cada pessoa em cada instante de tempo.

Foi escolhido criar um programa que pudesse analisar os seguintes eventos:

- Encontro: Definido como a ausência de movimento de duas pessoas que estejam próximas. Foi estabelecido que o tempo necessário que as pessoas devem ficar paradas em uma distância mínima uma da outra para que elas estejam em um encontro é de três frames

- Aproximação: Definido como a aproximação de dois indivíduos com relação à distância entre ambos em frames anteriores.

- Grupo: Definido como o agrupamento de duas ou mais pessoas. Para que indivíduos estejam em um grupo eles devem estar à uma distância mínima de outro indivíduo ou de alguém que pertence à um grupo.

# Análises

Foram selecionados vídeos de quatro países diferentes, Japão, Brasil, Reino Unido e Alemanha. Para países que possuíam mais de vídeo disponível será especificado qual foi escolhido, caso não seja especificado fica-se subentendido que existia apenas uma opção de vídeo para o país.

Japão:

O vídeo selecionado foi o Japão 1. Não ocorrem encontros no vídeo e o único grupo com mais de dois indivíduos, no caso, 3, é formado nos tempos 8, 10 e 11.

Brasil:

O vídeo escolhido foi o Brasil 1. Não ocorrem encontros no vídeo mas são formados grupos de até quatro pessoas.

Reino Unido:

O vídeo do Reino Unido não contém encontros e o maior grupo possui duas pessoas.

Alemanha:

O vídeo selecionado foi a Alemanha 2. Foi o único vídeo que apresentou algum encontro entre dois indivíduos, mas isto ocorreu apenas entre dois deles. Existem grupos de até quatro pessoas.
