# Deep Sea Mining 🌊🚁

<div align="center">

**Instituto Superior de Engenharia de Coimbra (ISEC)**  
Licenciatura em Engenharia Informática  
Unidade Curricular: **Programação Avançada** — 2025/2026  
Docente: **Prof. Álvaro Santos**

</div>

---

## 🏆 Avaliação

> **Nota Final: 94 / 100**

---

## 👥 Grupo

| Nome | Nº Aluno |
|------|----------|
| Rafael Marques | a2024152576 |
| Diogo | a2024152576 |
| Vera | a2024140140 |

---

## 📖 Sobre o Projeto

O **Deep Sea Mining** é um jogo de estratégia e exploração desenvolvido inteiramente em **Java** com interface gráfica em **JavaFX**. O jogador assume o comando de uma operação de resgate em águas profundas com o objetivo de recuperar artefactos perdidos de uma civilização antiga.

A missão exige uma gestão rigorosa de recursos — combustível e integridade do casco dos drones — e a superação de perigos nas profundezas do oceano. O jogo desenrola-se alternando entre a **gestão estratégica do navio à superfície** e a **exploração subaquática** através de drones.

### 🎮 Mecânicas Principais

- **Gestão de Drones:** O jogador dispõe de 3 drones, usando apenas um de cada vez nas expedições. Cada drone tem combustível e integridade próprios, podendo ser reparado e abastecido na oficina do navio.
- **Zonas de Jogo:**
  - **Superfície** — Grelha de gestão e preparação onde o navio se desloca para posicionar a descida do drone.
  - **Fosso Marinho** — Fase de descida e subida com obstáculos (rochas, animais marinhos, correntes) que danificam ou desviam o drone.
  - **Fundo do Mar** — Zona de exploração para recolha de minérios e artefactos, com monstros marinhos a patrulhar.
  - **Puzzle** — Desafio lógico (tipo *sliding puzzle*) que o jogador deve resolver para recuperar um artefacto.
  - **Oficina** — Ecrã de manutenção onde se abastecem e reparam os drones usando o combustível do navio.
- **Coleção:** O objetivo global é completar uma coleção de *n* artefactos únicos para vencer o jogo.
- **Condições de Derrota:** O jogo termina se todos os drones forem destruídos ou se o navio ficar sem combustível suficiente para operar.

---

## 🏗️ Arquitetura e Padrões de Design

O projeto foi desenvolvido seguindo o paradigma da **Programação Orientada a Objetos (POO)**, aplicando a arquitetura **Model-View-Controller (MVC)** e diversos padrões de design:

| Padrão | Aplicação no Projeto |
|--------|---------------------|
| **Finite State Machine (FSM)** | Gestão do fluxo do jogo através de estados (`SuperficieState`, `DescidaState`, `FundoState`, `SubidaState`, `PuzzleState`, `OficinaState`, `AcabouState`) com transições controladas por um `DeepSeaContext`. |
| **Factory Method** | Enum `DeepSeaState` com método `getInstance()` que instancia o estado concreto correto. |
| **Observer** | `PropertyChangeSupport` no `DeepSeaManager` notifica as views (RootPane, Canvas, Barras Laterais) de alterações no modelo. |
| **Singleton** | Classe `DeepSeaLog` — log centralizado do jogo com instância única. |
| **Facade** | `DeepSeaManager` expõe uma API simplificada à UI, escondendo a complexidade do `DeepSeaContext` e do `Jogo`. |
| **Multiton** | Gestão de recursos multimédia (imagens) com `ImageLoader`. |
| **State (via Adapter)** | `DeepSeaStateAdapter` — classe abstrata que implementa `IDeepSeaState` com comportamento por omissão, evitando código repetido nos estados concretos. |

---

## 📂 Estrutura do Projeto

```text
📦 PA-Project/
 ┣ 📂 PA-Project/
 ┃ ┣ 📂 src/
 ┃ ┃ ┣ 📂 main/java/pt/isec/pa/deepsea/
 ┃ ┃ ┃ ┣ 📄 DeepSeaApp.java                  # Ponto de entrada da aplicação
 ┃ ┃ ┃ ┣ 📂 model/
 ┃ ┃ ┃ ┃ ┣ 📄 DeepSeaManager.java             # Facade + Observer (PropertyChangeSupport)
 ┃ ┃ ┃ ┃ ┣ 📄 Direcao.java                    # Enum de direções de movimento
 ┃ ┃ ┃ ┃ ┣ 📄 TipoComponente.java             # Enum de tipos de componentes
 ┃ ┃ ┃ ┃ ┣ 📂 state/                          # Máquina de Estados (FSM)
 ┃ ┃ ┃ ┃ ┃ ┣ 📄 DeepSeaContext.java            # Contexto da FSM
 ┃ ┃ ┃ ┃ ┃ ┣ 📄 IDeepSeaState.java             # Interface dos estados
 ┃ ┃ ┃ ┃ ┃ ┣ 📄 DeepSeaStateAdapter.java       # Adapter abstrato
 ┃ ┃ ┃ ┃ ┃ ┣ 📄 DeepSeaState.java              # Enum + Factory Method
 ┃ ┃ ┃ ┃ ┃ ┗ 📂 states/                       # Estados concretos
 ┃ ┃ ┃ ┃ ┃   ┣ SuperficieState, DescidaState, FundoState,
 ┃ ┃ ┃ ┃ ┃   ┣ SubidaState, PuzzleState, OficinaState,
 ┃ ┃ ┃ ┃ ┃   ┣ AcabouState, FossoState
 ┃ ┃ ┃ ┃ ┣ 📂 data/                           # Estruturas de dados e regras de negócio
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 jogo/                         # Jogo, Navio, Drone
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 grelhas/                      # Grelhas (Superfície, Fosso, Fundo)
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 elementos/                    # Componentes (Rocha, Animal, Corrente, Monstro, Minério, Artefacto)
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 puzzle/                       # Lógica do Puzzle (sliding puzzle)
 ┃ ┃ ┃ ┃ ┃ ┗ 📄 Settings.java                 # Configurações do jogo
 ┃ ┃ ┃ ┃ ┗ 📂 utils/
 ┃ ┃ ┃ ┃   ┗ 📄 DeepSeaLog.java               # Singleton — Log centralizado
 ┃ ┃ ┃ ┗ 📂 ui/                               # Interface Gráfica (JavaFX)
 ┃ ┃ ┃   ┣ 📄 MainJFX.java                    # Inicialização do JavaFX
 ┃ ┃ ┃   ┣ 📄 RootPane.java                   # Painel raiz (StackPane)
 ┃ ┃ ┃   ┣ 📄 AppMenuBar.java                 # Barra de menus
 ┃ ┃ ┃   ┣ 📄 AcabouPane.java                 # Ecrã de fim de jogo
 ┃ ┃ ┃   ┣ 📄 LogStage.java                   # Segunda janela sincronizada (log)
 ┃ ┃ ┃   ┣ 📄 BotaoSom.java                   # Toggle de som
 ┃ ┃ ┃   ┣ 📂 canvas/                         # Canvas para cada zona do jogo
 ┃ ┃ ┃   ┃ ┣ DeepSeaCanvas (abstrato), SuperficieCanvas,
 ┃ ┃ ┃   ┃ ┣ FossoCanvas, FundoCanvas, PuzzleCanvas
 ┃ ┃ ┃   ┣ 📂 barrasLaterais/                 # Barras laterais contextuais
 ┃ ┃ ┃   ┃ ┣ BarraLateralBase (abstrata), BarraLateralSuperficie,
 ┃ ┃ ┃   ┃ ┣ BarraLateralFosso, BarraLateralFundo,
 ┃ ┃ ┃   ┃ ┣ BarraLateralPuzzle, BarraLateralNavegacao
 ┃ ┃ ┃   ┣ 📂 oficina/                        # UI da Oficina
 ┃ ┃ ┃   ┃ ┣ OficinaPane, OficinaInfoBox,
 ┃ ┃ ┃   ┃ ┣ OficinaActionsBox, DronesCanvas
 ┃ ┃ ┃   ┗ 📂 res/                            # Recursos multimédia
 ┃ ┃ ┃     ┣ 📄 ImageLoader.java               # Multiton — carregamento de imagens
 ┃ ┃ ┃     ┗ 📄 SoundManager.java              # Gestão de efeitos sonoros
 ┃ ┃ ┣ 📂 main/resources/
 ┃ ┃ ┃ ┣ 📂 images/                           # Sprites e ícones do jogo
 ┃ ┃ ┃ ┗ 📂 sounds/                           # Efeitos sonoros (.mp3)
 ┃ ┃ ┗ 📂 test/java/pt/isec/pa/deepsea/       # Testes unitários (JUnit 5)
 ┃ ┃   ┗ 📂 model/
 ┃ ┃     ┣ 📄 DeepSeaManagerTest.java
 ┃ ┃     ┣ 📂 state/                          # Testes de todos os estados da FSM
 ┃ ┃     ┣ 📂 data/jogo/                      # Testes do Jogo, Navio, Drone
 ┃ ┃     ┣ 📂 data/grelhas/                   # Testes das grelhas
 ┃ ┃     ┣ 📂 data/puzzle/                    # Testes do Puzzle
 ┃ ┃     ┣ 📂 data/elementos/                 # Testes dos componentes
 ┃ ┃     ┗ 📂 utils/                          # Testes do DeepSeaLog
 ┃ ┗ 📄 pom.xml                               # Configuração Maven
 ┣ 📄 PA.TrabPratico.pdf                       # Enunciado completo
 ┣ 📄 PA.TrabPratico.Etapa1.pdf                # Enunciado — Etapa 1
 ┣ 📄 PA.TrabPratico.Etapa2.pdf                # Enunciado — Etapa 2
 ┣ 📄 PA.TrabPratico.Etapa3.pdf                # Enunciado — Etapa 3
 ┣ 📄 PA.TrabPratico.EtapaFinal.pdf            # Enunciado — Etapa Final
 ┗ 📄 README.md
```

---

## 🔧 Tecnologias e Dependências

| Tecnologia | Versão | Utilização |
|------------|--------|------------|
| **Java** | 25 | Linguagem principal |
| **JavaFX** | 21.0.2 | Interface gráfica (Canvas, controlos, layouts) |
| **JUnit 5** | 5.10.2 | Testes unitários |
| **Maven** | — | Gestão de dependências e build |

---

## 🚀 Como Executar

### Pré-requisitos
- **Java JDK 25** (ou superior)
- **Maven** instalado

### Compilar e Executar

```bash
# Clonar o repositório
git clone https://github.com/Vera2024140140/DeepSeaMining---TP-PA.git
cd PA-Project/PA-Project

# Compilar
mvn clean compile

# Executar a aplicação
mvn javafx:run
```

### Executar os Testes

```bash
mvn test
```

---

## 📋 Etapas do Desenvolvimento

O projeto foi desenvolvido ao longo de **4 etapas**, cada uma com requisitos incrementais:

### Etapa 1 — Modelo de Dados
- Implementação das classes base do modelo (`Jogo`, `Navio`, `Drone`, grelhas, elementos).
- Definição das estruturas de dados para as três zonas do jogo.

### Etapa 2 — Máquina de Estados
- Implementação da FSM com `DeepSeaContext`, `IDeepSeaState` e `DeepSeaStateAdapter`.
- Criação de todos os estados concretos e respetivas transições.
- Introdução do padrão Factory Method no enum `DeepSeaState`.

### Etapa 3 — Interface Gráfica e Padrões
- Desenvolvimento da UI em JavaFX com arquitetura MVC.
- Implementação do padrão Observer via `PropertyChangeSupport`.
- Canvas customizados para cada zona do jogo.
- Barras laterais contextuais por estado.
- Facade (`DeepSeaManager`) como ponto de acesso único da UI ao modelo.
- Serialização para gravar/carregar jogos.

### Etapa Final — Polimento e Funcionalidades Avançadas
- **Segunda janela sincronizada** — ambas as janelas respondem ao mesmo `DeepSeaManager` via `PropertyChangeListener`.
- **Redimensionamento proporcional** das grelhas (Canvas) ao tamanho da janela.
- **Sistema de som** (`SoundManager`) — efeitos sonoros para movimentos, eventos especiais e música de oficina, com toggle on/off.
- **Testes unitários abrangentes** (18 classes de teste) cobrindo estados, modelo de dados, grelhas e puzzle.
- **Javadoc** das classes do modelo e estados.
- **Relatório final** com diagramas de classes e de estados.

---

## 📊 Diagrama de Estados

```mermaid
stateDiagram-v2
    [*] --> Superficie
    Superficie --> Descida : iniciarDescida()
    Superficie --> Oficina : abrirOficina()
    Superficie --> Acabou : sem drones / sem combustível

    Descida --> Fundo : chegou ao fundo
    Descida --> Subida : drone destruído

    Fundo --> Subida : iniciarSubida()
    Fundo --> Puzzle : encontrou artefacto
    Fundo --> Acabou : drone destruído (último)

    Puzzle --> Subida : puzzle resolvido / perdido
    Puzzle --> Acabou : drone destruído (último)

    Subida --> Superficie : chegou à superfície
    Subida --> Acabou : drone destruído (último)

    Oficina --> Superficie : fecharOficina()
    Oficina --> Acabou : abastecer esgotou combustível do navio

    Acabou --> [*]
```

---
