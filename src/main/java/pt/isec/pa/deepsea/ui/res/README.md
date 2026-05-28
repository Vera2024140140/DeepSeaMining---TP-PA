# Package: pt.isec.pa.deepsea.ui.res

Recursos partilhados da camada de UI: carregamento de **imagens** em cache e
gestão de **áudio** (efeitos sonoros e música de fundo). Centraliza o acesso
aos ficheiros estáticos em `src/main/resources/images/` e
`src/main/resources/sounds/`, evitando duplicação de código nos vários
`Canvas`, painéis e barras laterais da UI.

## Objetivo e responsabilidade

- Disponibilizar uma API estática simples para qualquer componente da UI obter
  uma `Image` (`ImageLoader.getImage`) sem precisar de conhecer a estrutura
  de diretórios dos recursos.
- Encapsular a reprodução de som através do JavaFX `MediaPlayer`, com cache
  dos objetos `Media` para evitar re-carregamentos.
- Subscrever as propriedades do `DeepSeaManager` correspondentes a eventos
  sonoros do jogo (movimento do drone/navio, colisões com rocha/monstro/
  corrente/animal marinho, recolha de minério e artefacto, ganhar/perder
  jogo, perder puzzle, perder drone, abrir/fechar oficina) e executar a
  reprodução do ficheiro `.mp3` adequado.
- Permitir ao utilizador silenciar/reativar globalmente o som através de
  uma `BooleanProperty` observável, à qual o `BotaoSom` (em
  `ui/BotaoSom.java`) está ligado.

## Conteúdo

- **`ImageLoader`** — utilitário estático com um `HashMap<String, Image>`.
  O método `getImage(nome)` devolve a imagem em cache ou carrega-a de
  `/images/` na primeira utilização.

- **`SoundManager`** — utilitário estático que mantém em cache os `Media`
  num `HashMap<String, Media>` e expõe três modos de reprodução:
  - `playMovimento(ficheiro)` — toca um som de movimento limitado aos
    primeiros 500 ms, parando primeiro qualquer som anterior. Quando o
    excerto termina, executa o eventual som pendente. Usado para
    `moverDrone.mp3` e `moverNavio.mp3`.
  - `playSomSobreposto(ficheiro)` — toca um efeito pontual. Se houver um
    som de movimento a tocar, o pedido fica em `somPendente` para tocar
    logo a seguir; caso contrário toca imediatamente num novo
    `MediaPlayer`. Usado para `minerio.mp3`, `dronerocha.mp3`,
    `dronecorrente.mp3`, `polvo.mp3`, `monstro.mp3`,
    `recolherArtefacto.mp3`, `ganharJogo.mp3`, `somPerder.mp3` e
    `perderDrone.mp3`.
  - `playLoop(ficheiro)` / `stopLoop()` — música de fundo em loop infinito
    (`MediaPlayer.INDEFINITE`). Usado para `musicaOficina.mp3` ao entrar
    na oficina; é parado ao sair.

  Disponibiliza ainda `somLigadoProperty()` (a `BooleanProperty` que o
  `BotaoSom` observa), `isSomLigado()`, `setSomLigado(boolean)` (que pára
  tudo quando desligado) e `stop()`.

  O método-chave `setSounds(DeepSeaManager)` regista, num único sítio,
  todos os `PropertyChangeListener` que ligam eventos do modelo aos
  respetivos ficheiros de áudio.
