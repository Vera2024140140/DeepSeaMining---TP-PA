package pt.isec.pa.deepsea.model.data.jogo;

import pt.isec.pa.deepsea.model.data.Direcao;
import pt.isec.pa.deepsea.model.data.Settings;
import pt.isec.pa.deepsea.model.data.TipoComponente;
import pt.isec.pa.deepsea.model.data.elementos.Artefacto;
import pt.isec.pa.deepsea.model.data.grelhas.GrelhaSuperficie;
import pt.isec.pa.deepsea.model.data.puzzle.Puzzle;

import java.io.Serializable;
import java.util.*;

/**
 * Esta classe representa o Motor de Dados principal do jogo
 * <p>
 * Esta clase tem nela toda a lógica, agregando em si as entidades principais,
 * Navio, 'Drone' e o sistema de Grelhas (Superficie, Fosso, Fundo).
 * Possui implementado o {@link Serializable} para permitirmos guardar e
 * recuperar o jogo quando nos apetecer.
 * </p>
 * @author Rafael2024140344
 * @author Diogo2024152576
 * @author Vera2024140140
 */

public class Jogo {
    private final Navio navio;
    private final GrelhaSuperficie grelhaSuperficie;
    private Puzzle puzzleAtual = null;

    /**
     * Constructor da classe Jogo, nela são inicializados o navio e a grelha
     * da superfície com os valores por omissão.
     */
    public Jogo() {
        this.grelhaSuperficie = new GrelhaSuperficie();
        this.navio = new Navio();
    }

    // -- Getter's
    Navio getNavio() {
        return navio;
    }

    GrelhaSuperficie getGrelhaSuperficie() {
        return grelhaSuperficie;
    }

    public double getCombustivelNavio() {
        return navio.getCombustivelNavio();
    }

    public int getMineriosNavio() {
        return navio.getMineriosNavio();
    }

    public List<Integer> getIdsArtefactosNavio() {
        return navio.getIdsArtefactosNavio();
    }

    public Set<Integer> getIdsDronesNavio() {
        return navio.getIdsDronesNavio();
    }

    // ===================================================================
    // --- MOVIMENTO ---
    // ===================================================================

    /**
     * Move o 'Navio' na grelha da superfície.
     * Remove o combustível necessário por movimento e verifica os limites do mapa.
     *
     * @param dir A direção pretendida para onde o navio se quer mover (CIMA/BAIXO/DIREITA/ESQUERDA)
     * @return true caso o movimento seja válido e efetuado, false em caso contrário
     */
    public boolean moverNavio(Direcao dir) {
        if (dir == null) return false;
        if (navio.getCombustivelNavio() < Settings.COMBUSTIVEL_MOV_NAVIO) return false;

        int novaLinha = navio.getLinha();
        int novaColuna = navio.getColuna();
        switch (dir) {
            case CIMA -> novaLinha--;
            case BAIXO -> novaLinha++;
            case DIREITA -> novaColuna++;
            case ESQUERDA -> novaColuna--;
        }

        if (novaLinha < 0 || novaLinha >= Settings.LINHAS_SUPERFICIE || novaColuna < 0 || novaColuna >= Settings.COLUNAS_SUPERFICIE) {
            return false;
        }

        navio.setLocalizacao(novaLinha, novaColuna);
        navio.setCombustivel(navio.getCombustivelNavio() - Settings.COMBUSTIVEL_MOV_NAVIO);
        return true;
    }

    /**
     * Move o ‘Drone’ na grelha do fundo, mar.
     * Remove o combustível necessário por movimento, revela a célula de destino
     * e aplica possíveis danos causados por monstros.
     *
     * @param dir A direção do movimento
     * @return true se o movimento for executado com sucesso
     */
    public boolean moverDroneFundo(Direcao dir) {
        if(dir == null) return false;
        Drone drone = navio.getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        if (drone == null) return false;

        int novaLinha = drone.getLinha();
        int novaColuna = drone.getColuna();

        switch (dir) {
            case CIMA -> novaLinha--;
            case BAIXO -> novaLinha++;
            case DIREITA -> novaColuna++;
            case ESQUERDA -> novaColuna--;
        }
        if (novaLinha < 0 || novaLinha >= Settings.LINHAS_FUNDO || novaColuna < 0 || novaColuna >= Settings.COLUNAS_FUNDO) {
            return false;
        }
        if (drone.getCombustivel() < Settings.DRONE_CONSUMO_MOV) {
            return false; // Sem combustivel suficiente para andar
        }
        drone.consumirCombustivelDrone(Settings.DRONE_CONSUMO_MOV);
        drone.setLocalizacao(novaLinha, novaColuna);
        if (!grelhaSuperficie.fundoIsRevelada(lSup, cSup, novaLinha, novaColuna))
            grelhaSuperficie.fundoRevelar(lSup, cSup, novaLinha, novaColuna);
        TipoComponente tipo = grelhaSuperficie.fundoGetTipo(lSup, cSup, novaLinha, novaColuna);

        if(tipo == TipoComponente.MONSTRO){
            drone.sofrerImpacto();
        }
        return true;
    }

    /**
     * Posiciona o drone selecionado no centro do Fundo (0, centro).
     *
     * @return true, caso o posicionamento seja efeutado com sucesso.
     */
    public boolean meteDroneNoFundo() {
        Drone drone = navio.getDroneAtivo();
        if (drone != null) {
            drone.setLocalizacao( 0, grelhaSuperficie.getColunasFundo(navio.getLinha(), navio.getColuna()) / 2);
            return true;
        }
        return false;
    }

    /**
     * Verifica se o 'drone' ativo está na linha 0 da grelha onde se encontra.
     * @return true se estiver na linha 0
     */
    public boolean droneNoTopo(){
        Drone drone = navio.getDroneAtivo();
        if (drone == null) {
            return false;
        }
        return drone.getLinha() == 0;
    }

    public boolean droneNoFundoFosso(){
        Drone drone = navio.getDroneAtivo();
        if (drone == null) {
            return false;
        }
        return drone.getLinha() == grelhaSuperficie.getLinhasFosso(navio.getLinha(), navio.getColuna()) - 1;
    }

    /**
     * Move o 'Drone' durante os estados de Subida/Descida do Fosso.
     * Calcula os gastos extras causados por correntes e regista impactos em rochas/animais.
     *
     * @param dir Direção prentendida
     * @return true, se o movimento for executado (mesmo sofrendo impacto)
     */
    public boolean moverDroneFosso(Direcao dir) {
        Drone drone = getDroneAtivo();
        if (drone == null) return false;

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        //calc pos destino
        int destinoLinha = drone.getLinha();
        int destinoColuna = drone.getColuna();

        switch (dir) {
            case CIMA -> destinoLinha--;
            case BAIXO -> destinoLinha++;
            case DIREITA -> destinoColuna++;
            case ESQUERDA -> destinoColuna--;
        }

        //ver paredes grelha
        if (destinoLinha < 0 || destinoLinha >= grelhaSuperficie.getLinhasFosso(lSup, cSup) ||
                destinoColuna < 0 || destinoColuna >= grelhaSuperficie.getColunasFosso(lSup, cSup)) {
            return false; //fora do mapa
        }

        drone.consumirCombustivelDrone(Settings.DRONE_CONSUMO_MOV);

        if (grelhaSuperficie.fossoTemRocha(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.sofrerImpacto();
            return true;
        }

        if (grelhaSuperficie.fossoTemAnimal(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.sofrerImpacto();
        }

        if (grelhaSuperficie.fossoTemCorrente(lSup, cSup, destinoLinha, destinoColuna)) {
            drone.consumirCombustivelDrone(Settings.DRONE_CONSUMO_CORRENTE);
        }
        drone.setLocalizacao(destinoLinha, destinoColuna);
        return true;
    }

    public boolean selecionarDrone(int idDrone) {
        return navio.setDroneAtivo(idDrone);
    }

    private Drone getDroneAtivo() {
        return navio.getDroneAtivo();
    }

    public boolean podeIniciarDescida() {
        Drone d = navio.getDroneAtivo();
        return d != null && d.getCombustivel() > 0 && d.getIntegridadeCasco() > 0;
    }

    public boolean meteDroneNoInicioFosso() {
        navio.getDroneAtivo().resetImpactos();
        return meteDroneNoFosso(0);
    }

    public boolean meteDroneNoFimFosso() {
        return meteDroneNoFosso(grelhaSuperficie.getLinhasFosso(navio.getLinha(), navio.getColuna()) - 1);
    }


    public boolean meteDroneNoFosso(int linha) {
        Drone d = navio.getDroneAtivo();
        if (d != null) {
            d.setLocalizacao( linha, grelhaSuperficie.getColunasFosso(navio.getLinha(), navio.getColuna()) / 2);
            return true;
        }
        return false;
    }

    public boolean removerDroneAtivo() {
        int id = navio.getDroneAtivoId();
        if (id == -1) return false;
        navio.rmDrones(id);
        return true;
    }

    /**
     * Caso o 'Drone' seja destruído (por falta de combustível || falta de integridade),
     * o conteúdo transportado (minérios e artefato) são espalhados pelo fundo
     * correspondente à localização atual do navio.
     */
    // retira itens do drone ativo e larga-os no fundo (posições aleatórias, cobertas pela escuridão)
    public void largarItensDroneNoFundo() {
        Drone d = getDroneAtivo();
        if (d == null) return;

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        List<Artefacto> artefactos = d.descarregarArtefactos();
        int minerios = d.descarregarMinerios();

        if (!artefactos.isEmpty() || minerios > 0) {
            grelhaSuperficie.fundoLargarItens(lSup, cSup, artefactos, minerios);
        }
    }

    public double getCombustivelDroneAtivo() {
        if (navio.getDroneAtivo() != null)
            return navio.getCombustivelDroneAtivo();
        return 0;
    }


    public int getIntegridadeDroneAtivo() {
        if (navio.getDroneAtivo() != null)
            return navio.getIntegridadeDroneAtivo();
        return 0;
    }

    //get's de grelhas

    public TipoComponente[][] getMapaFundo() {
        //return grelhaFundo.toString();
        return null;
    }

    public TipoComponente[][] getMapaFosso() {
        //return grelhafosso.toString();
        return  null;
    }

    /**
     * @return true, caso o jogador tenha recolhido todos os artefactos
     */
    public boolean vitoria() {
        return navio.getIdsArtefactosNavio().size() >= Settings.NUM_ARTEFACTOS;
    }

    /**
     * @return true, caso o navio não tenha combustível ou os 3 'Drones' tenham sido todos destruídos.
     */
    public boolean derrota() {
        return navio.getCombustivelNavio() <= 0 || navio.getIdsDronesNavio().isEmpty();
    }

    // simulação para testes de fim de jogo (PUBLIC APENAS PARA TESTES)
    public void simularVitoria() {
        while (navio.getIdsArtefactosNavio().size() < Settings.NUM_ARTEFACTOS) {
            navio.addArtefacto(new Artefacto(-1, -1));
        }
    }

    public void simularNavioSemCombustivel() {
        navio.setCombustivel(0);
    }

    public void simularNavioSemDrones() {
        for (int id : new HashSet<>(navio.getIdsDronesNavio())) {
            navio.rmDrones(id);
        }
    }

    // ===================================================================
    // --- OFICINA ---
    // ===================================================================

    /**
     * Abastece o drone ativo, transferindo combustível do navio para o drone.
     *
     * @param litros Quantidade de combustível pretendida.
     * @return true, se o drone foi abastecido (mesmo que parcialmente).
     */
    public boolean abastecerDroneAtivo(double litros) {
        if(litros <= 0)
            return false;
        Drone drone = navio.getDroneAtivo();
        if (drone == null)
            return false;
        if(navio.removerCombustivel(litros)){
            double valor = drone.addCombustivel(litros);
            if(valor == 0 )
                return true;
            else if (valor < litros){
                navio.addCombustivel(valor);
                return true;
            }else{
                //drone ja estava cheio
                navio.addCombustivel(valor);
                return false;
            }
        }
        return false;
    }

    /**
     * Repara a integridade do 'drone' ativo, gastando combustível (do navio) como custo da operação.
     *
     * @param integridade Os pontos de integridade a recuperar
     * @return true, caso a reparação seja bem sucedida.
     */
    public boolean repararIntegridadeDroneAtivo(int integridade){
        if(integridade <= 0)
            return false;
        Drone drone = navio.getDroneAtivo();
        if(drone == null || drone.getIntegridadeCasco() >= drone.getIntegridadeMax())
            return false;
        if(navio.removerCombustivel(Settings.COMBUSTIVEL_REPARACAO*integridade)){
            if(drone.addIntegridade(integridade))
                return true;
            else{
                navio.addCombustivel(Settings.COMBUSTIVEL_REPARACAO*integridade);
                return false;
            }
        }
        return false;
    }

    public boolean melhorarTanqueDroneAtivo(){
        Drone drone = navio.getDroneAtivo();
        if(drone == null)
            return false;
        if(navio.removerMinerios(Settings.CUSTO_MINERIOS_MELHORAR_TANQUE)){
            drone.setCombustivelMax(drone.getCombustivelMax() + Settings.INCREMENTO_COMBUSTIVEL_MAX);
            return true;
        }
        return false;
    }

    public boolean melhorarIntegridadeDroneAtivo(){
        Drone drone = navio.getDroneAtivo();
        if(drone == null )
            return false;
        if(navio.removerMinerios(Settings.CUSTO_MINERIOS_MELHORAR_INTEGRIDADE)){
            drone.setIntegridadeMax(drone.getIntegridadeMax() + Settings.INCREMENTO_INTEGRIDADE_MAXIMA);
            drone.addIntegridade(Settings.INCREMENTO_INTEGRIDADE_MAXIMA);
            return true;
        }
        return false;
    }

    public int[][] getMapaPistasArtefactos() {
        return grelhaSuperficie.getMapaPistasArtefactos();
    }

    public int getNumArtefactosNoFundo() {
        return grelhaSuperficie.fundoContarArtefactos(navio.getLinha(), navio.getColuna());
    }

    // mapa completo do fundo (matriz de tipos) para futura UI gráfica
    public TipoComponente[][] getMapaFundo(int lSup, int cSup) {
        int nl = grelhaSuperficie.getLinhasFundo(lSup, cSup);
        int nc = grelhaSuperficie.getColunasFundo(lSup, cSup);
        TipoComponente[][] mapa = new TipoComponente[nl][nc];
        for (int l = 0; l < nl; l++) {
            for (int c = 0; c < nc; c++) {
                mapa[l][c] = grelhaSuperficie.getTipoNoFundo(lSup, cSup, l, c);
            }
        }
        return mapa;
    }

    // mapa completo do fosso (matriz de tipos) para futura UI gráfica
    public TipoComponente[][] getMapaFosso(int lSup, int cSup) {
        int nl = grelhaSuperficie.getLinhasFosso(lSup, cSup);
        int nc = grelhaSuperficie.getColunasFosso(lSup, cSup);
        TipoComponente[][] mapa = new TipoComponente[nl][nc];
        for (int l = 0; l < nl; l++) {
            for (int c = 0; c < nc; c++) {
                mapa[l][c] = grelhaSuperficie.getTipoNoFosso(lSup, cSup, l, c);
            }
        }
        return mapa;
    }

    // mapa do fundo da célula onde o navio está
    public TipoComponente[][] getMapaFundoNavio() {
        return getMapaFundo(navio.getLinha(), navio.getColuna());
    }

    // mapa do fosso da célula onde o navio está
    public TipoComponente[][] getMapaFossoNavio() {
        return getMapaFosso(navio.getLinha(), navio.getColuna());
    }



    // ===================================================================
    // --- MOVIMENTO ---
    // ===================================================================
    public boolean droneChegouFundo() {
        Drone d =  getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        if (d == null )
            return false;

        return d.getLinha() >= grelhaSuperficie.getLinhasFosso(lSup, cSup) - 1;
    }

    /**
     * Passa os minérios e artefactos que o 'Drone' possui armazenados para o navio.
     *
     * @return true, caso o processo tenha sucesso
     */
    public boolean descarregarDroneNavio() {
        Drone d =  getDroneAtivo();
        if (d == null) return false;

        int mineriosRecolhidos = getDroneAtivo().descarregarMinerios();
        navio.addMinerios(mineriosRecolhidos);

        var artefatosRecolhidos = d.descarregarArtefactos();
        for (var art : artefatosRecolhidos) {
            navio.addArtefacto(art);
        }
        return true;
    }

    /**
     * Tenta recolher minérios na posição atual onde o 'Drone' se encontra no fundo, marinho.
     * Caso o minério seja recolhido aplicar-se-á ao 'Drone' um consumo extra de combustível
     * constante definido nas Settings.
     *
     * @return true, caso o minério seja recolhido com sucesso e guardado no drone.
     */
    public boolean recolherMinerio() {
        Drone drone = navio.getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        if (drone == null) return false;

        int linha = drone.getLinha();
        int coluna = drone.getColuna();

        TipoComponente tipo = grelhaSuperficie.getTipoNoFundo(lSup, cSup, linha, coluna);
        if (tipo == TipoComponente.MINERIO) {
            //  Calcular o custo (1% do combistivel maximo)
            double custoExtra = Settings.DRONE_COMBUSTIVEL_MAX * Settings.CONSUMO_EXTRA_MINERIO;
            if (drone.getCombustivel() >= custoExtra) {
                drone.consumirCombustivelDrone(custoExtra);
                //remover minerios
                int qtd_minerios = grelhaSuperficie.fundoRecolherMinerio(lSup, cSup, linha, coluna);
                if (qtd_minerios > 0) {
                    if (drone.addMinerios(qtd_minerios)) {
                        return true;
                    }
                }
            }
        }
        return false; //Não recolheu minerio
    }

    /**
     * Verifica se na célula atual do fundo do mar existe um artefacto.
     *
     *
     * @return true, caso exista.
     */
    public boolean verificarArtefacto() {
        Drone drone = navio.getDroneAtivo();

        if (drone == null) return false;

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        int linha = drone.getLinha();
        int coluna = drone.getColuna();

        return grelhaSuperficie.fundoGetTipo(lSup, cSup, linha, coluna) == TipoComponente.ARTEFACTO;
    }
    public void gerarMonstros() {
        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        grelhaSuperficie.gerarMonstros(lSup, cSup);
    }

    // ===================================================================
    // --- PUZZLE ---
    // ===================================================================
    /** Instancia e inicia um novo ‘puzzle’ na memória*/
    public void iniciarPuzzle() {
        this.puzzleAtual = new Puzzle();
    }

    /** Limpa o 'puzzle' da memória, usado após terminar o 'puzzle' em caso de vitória/derrota.*/
    public void limparPuzzle() {
        this.puzzleAtual = null;
    }

    /** Método responsável por delegar o comando de movimento na lógica interna do puzzle */
    public boolean moverPecaPuzzle(Direcao dir) {
        if (puzzleAtual != null) {
            return puzzleAtual.mover(dir);
        }
        return false;
    }

    public boolean isPuzzleResolvido() {
        return puzzleAtual != null && puzzleAtual.estaResolvido();
    }

    public boolean isPuzzleSemMovimentos() {
        return puzzleAtual != null && puzzleAtual.getMovimentosRestantes() <= 0;
    }

    /**
     * Este método é chamado quando o utilizador conclui o puzzle com sucesso.
     * Remove o artefacto da grelha do fundo marinho e adiciona-o à lista do drone que está a fazer a exploração do fundo.
     */
    public void recolherArtefactoPuzzle() {
        Drone d = getDroneAtivo();

        int lSup = navio.getLinha();
        int cSup = navio.getColuna();

        if (d != null) {
            int l = d.getLinha();
            int c = d.getColuna();

            if (grelhaSuperficie.fundoGetTipo(lSup, cSup, l, c) == TipoComponente.ARTEFACTO) {
                Artefacto art = grelhaSuperficie.fundoRecolherArtefacto(lSup, cSup, l, c);
                if (art != null) {
                    d.addArtefacto(art);
                }
            }
        }
        limparPuzzle();
    }
    //===================================================================
    //-------------------Metodos simulacoes testes OFICINA --------------
    //===================================================================
    public void simularGastoDrone() {
        Drone d = getDroneAtivo();
        if (d != null) {
            d.setCombustivel(1.0); // Gasta quase tudo
        }
    }

    public void simularDanoDrone() {
        Drone d = getDroneAtivo();
        if (d != null) {
            d.setIntegridadeCasco(d.getIntegridadeCasco() - 20);
        }
    }
    public void simularMinerios(int quantidade) {
        navio.addMinerios(quantidade);
    }

}