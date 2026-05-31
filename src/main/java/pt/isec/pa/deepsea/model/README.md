# Package: pt.isec.pa.deepsea.model

**Descrição do Package:**

Este package representa a camada principal de domínio da aplicação e serve como
ponto de entrada para toda a lógica do jogo "Deep Sea Mining".

* **Objetivo principal e responsabilidade:**
  A principal responsabilidade deste package é agregar toda a lógica do jogo,
  garantindo que a Interface Gráfica (UI) não interaja diretamente com os dados ou com
  a máquina de estados.
  Ele gere a comunicação entre os subpackages
  (`data`, `state` e `utils`) e expõe as funcionalidades do jogo de forma segura
  e controlada.


* **Tipos de classes incluídas:**
  Este package contém maioritariamente classes de gestão de dados e constantes:
  - **Facade:** O `DeepSeaManager` atua como a classe central (Facade), centralizando os pedidos da UI e propagando as notificações de mudança de estado (Observer pattern via `PropertyChangeSupport`).
  - **Enums Globais:** Classes como `Direcao` e `TipoComponente`, que são partilhadas e utilizadas por todos os sub-packages do modelo para padronizar e tipificar a informação.

*(Nota: A lógica de dados, a implementação da máquina de estados (FSM) e as ferramentas
utilitárias foram delegadas para os respetivos subpackages internos).*