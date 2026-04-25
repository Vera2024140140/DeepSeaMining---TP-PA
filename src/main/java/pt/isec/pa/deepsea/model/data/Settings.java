package pt.isec.pa.deepsea.model.data;

public interface Settings {
     boolean MODO_DEFESA = false;

    //Dimensao das grelhas
    int LINHAS_SUPERFICIE = 30;
    int COLUNAS_SUPERFICIE = 15;
    int LINHAS_FOSSO = 20;
    int COLUNAS_FOSSO = 10;
    int LINHAS_FUNDO = 10;
    int COLUNAS_FUNDO = 10;

    //quantidades e limintes
    int ID_OBSTACULO_INICIAL = 1001;
    int ID_MONSTRO_INICIAL = 2001;
    int ID_ARTEFACTOS_INICIAL = 1;
    int NUM_DRONES_INICIAIS = 3;
    int NUM_ARTEFACTOS = 16;
    int MINERIO_QTD_MIN = 1;
    int MINERIO_QTD_MAX = 5;
    int MINERIO_ZONA_MIN = 1;
    int MINERIO_ZONA_MAX = 1;
    int OBSTACULOS_FOSSO_MAX = 10;
    int OBSTACULOS_FOSSO_MIN = 1;
    int MONSTROS_FUNDO_MIN = 1;
    int MONSTROS_FUNDO_MAX = 7;
    int MINIMO_ROCHAS_LADO = 1;

    //combustivel e integridade casco
    double NAVIO_COMBUSTIVEL_INICIAL = 1000;
    double NAVIO_COMBUSTIVEL_MAX = 1000;
    double COMBUSTIVEL_REPARACAO = 20;
    double COMBUSTIVEL_MOV_NAVIO = 1;

    double DRONE_COMBUSTIVEL_MAX = 200;
    double DRONE_CONSUMO_MOV = 1;
    double DRONE_CONSUMO_CORRENTE = 2;
    int DRONE_INTEGRIDADE_MAX = 100;

    //mini jogo
    int PUZZLE_MAX_MOVIMENTOS = 50;
    int PUZZLE_GRELHA = 4;

    //percentagens
    double ROCHAS_PERCENTAGEM_MAX = 0.50;
    double CONSUMO_EXTRA_MINERIO = 0.01;
    double DANO_MONSTRO_PERCENTAGEM = 0.05;

    //custo e melhoramentos oficina
    int CUSTO_MINERIOS_MELHORAR_TANQUE = 5;
    int CUSTO_MINERIOS_MELHORAR_INTEGRIDADE = 5;
    int INCREMENTO_INTEGRIDADE_MAXIMA = 20;
    double INCREMENTO_COMBUSTIVEL_MAX = 20;

    //modo defesa
    int DEFESA_NUM_ARTEFACTOS_LADO = NUM_ARTEFACTOS/2;
    int DEFESA_NUM_ROCHAS_LADO = 1;
    int DEFESA_MONSTROS = 1;
    int DEFESA_MINERIOS = 2;
    int DEFESA_MINERIO_QTD_MAX = 10;





}
