package pt.isec.pa.deepsea.model.data;

public class Settings {
    public static final boolean MODO_DEFESA = false;

    //Dimensao das grelhas
    public static final int LINHAS_SUPERFICIE = 30;
    public static final int COLUNAS_SUPERFICIE = 15;
    public static final int LINHAS_FOSSO = 20;
    public static final int COLUNAS_FOSSO = 10;
    public static final int LINHAS_FUNDO = 10;
    public static final int COLUNAS_FUNDO = 10;

    //quantidades e limintes
    public static final int ID_OBSTACULO_INICIAL = 1001;
    public static final int ID_ARTEFACTOS_INICIAL = 1;
    public static final int NUM_DRONES_INICIAIS = 3;
    public static final int NUM_ARTEFACTOS = 16;
    public static final int MINERIO_QTD_MIN = 1;
    public static final int MINERIO_QTD_MAX = 5;
    public static final int MINERIO_ZONA_MIN = 1;
    public static final int MINERIO_ZONA_MAX = 1;
    public static final int OBSTACULOS_FOSSO_MAX = 10;
    public static final int MONSTROS_FUNDO_MIN = 1;
    public static final int MONSTROS_FUNDO_MAX = 7;

    //combustivel e integridade casco
    public static final double NAVIO_COMBUSTIVEL_INICIAL = 1000;
    public static final double NAVIO_COMBUSTIVEL_MAX = 1000;
    public static final double COMBUSTIVEL_REPARACAO = 20;
    public static final double COMBUSTIVEL_MOV_NAVIO = 1;

    public static final double DRONE_COMBUSTIVEL_MAX = 200;
    public static final double DRONE_CONSUMO_MOV = 1;
    public static final double DRONE_CONSUMO_CORRENTE = 2;
    public static final double DRONE_INTEGRIDADE_MAX = 100;

    //mini jogo
    public static final int PUZZLE_MAX_MOVIMENTOS = 50;
    public static final int PUZZLE_GRELHA = 4;

    //percentagens
    public static final double ROCHAS_PERCENTAGEM_MAX = 0.50;
    public static final double CONSUMO_EXTRA_MINERIO = 0.01;
    public static final double DANO_MONSTRO_PERCENTAGEM = 0.05;

    //custo e melhoramentos oficina
    public static final int CUSTO_MINERIOS_MELHORAR_TANQUE = 5;
    public static final int CUSTO_MINERIOS_MELHORAR_BLINDAGEM = 5;
    public static final int INCREMENTO_TANQUE_COMBUSTIVEL = 50;
    public static final int INCREMENTO_BLINDAGEM_MAXIMA = 20;

    //modo defesa
    public static final int DEFESA_NUM_ARTEFACTOS_LADO = NUM_ARTEFACTOS/2;
    public static final int DEFESA_NUM_ROCHAS_LADO = 1;
    public static final int DEFESA_OBSTACULOS = 2;
    public static final int DEFESA_MONSTROS = 1;
    public static final int DEFESA_MINERIOS = 2;
    public static final int DEFESA_MINERIO_QTD_MAX = 10;

    private Settings(){};




}
