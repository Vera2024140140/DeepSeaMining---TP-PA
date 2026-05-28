# Package: data

**Objetivo principal e responsabilidades:**
O package  data é responsável por agrupar, gerir e manipular toda a informação 
 do jogo. O principal objetivo deste package é garantir o isolamento dos dados do
modelo permitindo separar toda a lógica do jogo da forma como o jogo é apresentado (ui).

**Tipo de classes e funcionalidades contidas:**
Este package contém a classe de utilidades utilizada em algumas classes do modelo de dados e a interface
Settings que contém todas as constantes utilizadas pela aplicação.
Para além disso este package contém 4 sub-packages:
* **elementos**: Contém as classes que representam os elementos do jogo (AnimalMarinho, Artefacto, Corrente, Minério, Monstro e Rocha).
* **grelhas**: Contém as classes que permitem representar as grelhas de navagação do jogo (GrelhaSuperfície, FossoMarinho e FundoMarinho).
* **jogo**: Contém as classes que gere, os recursos do jogo (Navio, Drone e Jogo que centraliza toda a informação necessária ao jogo).
* **puzzle**: Contém a classe que permite gerir e representar a grelha do puzzle.