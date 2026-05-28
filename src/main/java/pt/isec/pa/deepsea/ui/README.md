# Package: pt.isec.pa.deepsea.ui

Camada de visualização da aplicação Deep Sea Mining, implementada com
**JavaFX**. Comunica com o modelo exclusivamente 
através do facade `DeepSeaManager` e reage às alterações disparadas por
`PropertyChangeSupport`.

## Objetivo e responsabilidade

- Apresentar visualmente os vários momentos do jogo (superfície, fundo, fosso,
  puzzle, oficina, fim de jogo) numa única janela JavaFX, cumprindo os
  requisitos da Etapa 3.
- Capturar eventos do utilizador (teclas WASD/setas, ENTER, "O", cliques,
  menus) e traduzi-los em ações invocadas sobre o `DeepSeaManager`.
- Mostrar/esconder cada `Canvas` ou painel sobreposto consoante o estado
  atual do jogo, e atualizar o desenho em resposta às propriedades
  notificadas pelo modelo (`PROP_STATE`, `PROP_DRONE`, `PROP_NAVIO`,
  `PROP_FUNDO`, `PROP_FOSSO`, `PROP_PUZZLE`, `PROP_LOG`, …).
- Gravar/carregar partidas, manter a lista de "Open recent" em disco e
  exibir a janela de logs.

## Conteúdo

### Classes principais (deste package)

- **`MainJFX`** — classe `Application` JavaFX. Cria o `Stage`, instancia o
  `RootPane`, ativa os sons através de `SoundManager.setSounds(manager)` e
  regista o `EventFilter` de teclado que mapeia as teclas
  (W/A/S/D, setas, ENTER, O) para ações do `DeepSeaManager` (mover,
  iniciar descida, abrir oficina).
- **`RootPane`** — `BorderPane` raiz da aplicação. No centro tem um
  `StackPane` que sobrepõe os `Canvas` de todos os estados (`SuperficieCanvas`,
  `FossoCanvas`, `FundoCanvas`, `PuzzleCanvas`) mais os painéis especiais
  (`OficinaPane`, `AcabouPane`). À direita tem outro `StackPane` com as
  várias barras laterais. No topo coloca a `AppMenuBar` em conjunto com o
  `BotaoSom`. Reage a `PROP_STATE` para forçar updates e propaga o
  redimensionamento da janela aos canvas filhos.
- **`AppMenuBar`** — barra de menus com os menus *Game* (New, Open, Open
  Recent, Save as…, Exit) e *Log* (Show/Hide, Save Logs, Clear Logs).
  Usa `FileChooser` para gravar/carregar `.sav` e gere a lista de ficheiros
  recentes em `Settings.FICHEIRO_RECENTES`.
- **`AcabouPane`** — ecrã final apresentado quando o jogo entra em
  `ACABOU_STATE`. Mostra "GAME OVER", o número de artefactos recolhidos e
  dois botões ("Jogar de Novo" e "Sair"). Subscreve `PROP_GAME`,
  `PROP_NAVIO` e `PROP_STATE` para se mostrar/esconder e atualizar.
- **`LogStage`** — `Stage` secundário com um `ListView` que mostra todas as
  entradas de `DeepSeaLog`. Atualiza-se em `PROP_LOG` e faz scroll automático
  para a entrada mais recente.
- **`BotaoSom`** — botão de ícone (volume.png) com uma barra vermelha em
  diagonal quando o som está desligado. Alterna o estado de
  `SoundManager.somLigadoProperty()`.

### Subpacotes

- **`canvas/`** — `DeepSeaCanvas` (classe abstrata com lógica de
  redimensionamento e `CELL_SIZE`) e as implementações concretas
  `SuperficieCanvas`, `FundoCanvas`, `FossoCanvas` e `PuzzleCanvas`.
  Cada canvas desenha a grelha do seu momento de jogo usando imagens
  obtidas via `ImageLoader`.
- **`barrasLaterais/`** — `BarraLateralBase` (VBox abstrato com título,
  subtítulo e autores) e as variantes específicas
  `BarraLateralSuperficie`, `BarraLateralFundo`, `BarraLateralFosso`,
  `BarraLateralPuzzle` e o componente partilhado `BarraLateralNavegacao`
  com as instruções de controlos.
- **`oficina/`** — `OficinaPane` (vista da oficina), `DronesCanvas`
  (canvas com os drones disponíveis para selecionar), `OficinaInfoBox`
  (informação do drone ativo) e `OficinaActionsBox` (botões de
  abastecer/reparar/upgrade).
- **`res/`** — recursos partilhados (carregamento de imagens em cache e
  gestão de áudio). Ver README desse package.
