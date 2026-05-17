package pt.isec.pa.deepsea.model.data;

import javafx.scene.text.Font;

public interface Settings {
     boolean MODO_DEFESA = false;

    //Dimensao das grelhas
    int LINHAS_SUPERFICIE = 14;
    int COLUNAS_SUPERFICIE = 14;
    int LINHAS_FOSSO = 14;
    int COLUNAS_FOSSO = 12;
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
    int PUZZLE_BARALHAR_GRELHA = 100;

    //percentagens
    double ROCHAS_PERCENTAGEM_MAX = 0.50;
    double CONSUMO_EXTRA_MINERIO = 0.01;

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

    String FICHEIRO_SAVE = "deepsea_save.sav";

    String FORMATO_TEMPO_LOG = "yyyy-MM-dd HH:mm:ss";

    int CELL_SIZE = 50;

    int CANVAS_DRONE_W = 240;
    int CANVAS_DRONE_H = 200;
    int WIDTH_LOG = 300;
    int HEIGHT_LOG = 500;
    int WIDTH_BARRA_LATERAL = 340;
    int HEIGHT_JANELA = 800;
    int WIDTH_JANELA = 1300;

    String FICHEIRO_RECENTES = "recentes.txt";
    int MAX_RECENT = 5;

    String BG_FUNDO      = "#b0b7bd";
    String BG_PANEL      = "#34495E";
    String BG_GRELHAS    = "#c9ddf0";
    String TEXT_PRIMARY  = "#ECF0F1";
    String TEXT_DARK     = "#2C3E50";
    String BAR_CRIT      = "#C0392B";
    String BTN_PRIMARY   = "#3498DB";
    String BTN_ACTION    = "#F39C12";

    Font FONTE = Font.font("Arial");
}
