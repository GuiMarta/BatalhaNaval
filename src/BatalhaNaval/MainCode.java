package BatalhaNaval;
import  java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class MainCode {

    //int [][] tabuleiro = new int[10][10];
    //int [][] tabuleiroAdversario = new int [10][10];

    public static final int TM_TABULEIRO = 10;
    public static char [][] board;
    public static final int TOT_NAVIOS = 10;


    public static final int TM_NAVIO_4 = 4;  //sizeSheaps
    public static final int NAVIO4_QNT = 1; //1 maxSheaps

    public static final int TM_NAVIO_3 = 3;
    public static final int NAVIO3_QNT = 1; //2

    public static final int TM_NAVIO_2 = 2;
    public static final int NAVIO2_QNT = 1; //3

    public static final int TM_NAVIO_1 = 1;
    public static final int NAVIO1_QNT = 1; //4

    //===========================================================

    public static void main(String[] args) {
        int op = 0;

            startGame();
            configTabuleiro();



    }

    //===========================================================

    public static void startGame() { //printa o tabuleiro com os caracteres
        System.out.printf("Batalha naval da Maria e do Marta.\n O tabuleiro está sendo iniciiado, aguarde!");

    }


    public static void configTabuleiro() {
        board = new char[TM_TABULEIRO][TM_TABULEIRO];
        for (int i = 0; i < TM_TABULEIRO; i++){
            Arrays.fill(board[i], '-'); // definir o carcter usado no mapa.
        }

    }




    //===========================================================
    public static void colocarNavio01(int x, int y, boolean naLinha){
        for (int k = x; k < (x + TM_NAVIO_1); k++){
            if(naLinha)
                board[y][k] = 'o';
            else
                board[k][y] = 'o';
        }
    }
    public static void colocarNavio02(int x, int y, boolean naLinha){
        for (int k = x; k < (x + TM_NAVIO_2); k++){
            if(naLinha)
                board[y][k] = 'o';
            else
                board[k][y] = 'o';
        }
    }
    public static void colocarNavio03(int x, int y, boolean naLinha){
        for (int k = x; k < (x+ TM_NAVIO_3); k++){
            if(naLinha)
                board[y][k] = 'o';
            else
                board[k][y] = 'o';
        }
    }
    public static void colocarNavio04(int x, int y, boolean naLinha){
        for (int k = x; k < (x+ TM_NAVIO_4); k++){
            if(naLinha)
                board[y][k] = 'o';
            else
                board[k][y] = 'o';
        }
    }
    //===========================================================
    public static void alocarNavios() {
        Random random = new Random();
        for (int qntTotNavios = 1; qntTotNavios <= TOT_NAVIOS;) {
            int posicaoX = random.nextInt(TM_TABULEIRO);
            int posicaoY = random.nextInt(TM_TABULEIRO);
            if (conferirNavio(posicaoX, posicaoY)) {
                qntTotNavios++;
            }
        }
    }

    //===========================================================

    private static int qntNavioJaAlocado4 = 0;
    private static int qntNavioJaAlocado3 = 0;
    private static int qntNavioJaAlocado2 = 0;
    private static int qntNavioJaAlocado1 = 0;


    public static boolean conferirNavio(int i , int j) {

        // Para o navio de 4 lugares
        if (qntNavioJaAlocado4 < NAVIO4_QNT) {
            if (j + TM_NAVIO_4 <= TM_TABULEIRO) {
                if (!navioNaColuna4(i, j)) {
                    colocarNavio04(i,j, false);
                    qntNavioJaAlocado4++;
                    return true;
                }
            } else if (i + TM_NAVIO_4 <= TM_TABULEIRO) {
                if (!navioNaLinha(i, j)) {
                    colocarNavio04(i, j, true);
                    qntNavioJaAlocado4++;
                    return true;
                }
            }
        }

        // Para o navio de 3 lugares
        if (qntNavioJaAlocado3 < NAVIO3_QNT) {
            if (j + TM_NAVIO_3 <= TM_TABULEIRO) {
                if (!navioNaColuna3(i, j)) {
                    colocarNavio03(i, j, false);
                    qntNavioJaAlocado3++;
                    return true;
                }
            } else if (i + TM_NAVIO_3 <= TM_TABULEIRO) {
                if (!navioNaLinha(i, j)) {
                    colocarNavio03(i, j, true);
                    qntNavioJaAlocado3++;
                    return true;
                }
            }
        }

        // Para o navio de 2 lugares
        if (qntNavioJaAlocado2 < NAVIO2_QNT) {
            if (j + TM_NAVIO_2 <= TM_TABULEIRO) {
                if (!navioNaColuna2(i, j)) {
                    colocarNavio02(i, j, false);
                    qntNavioJaAlocado2++;
                    return true;
                }
            } else if (i + TM_NAVIO_2 <= TM_TABULEIRO) {
                if (!navioNaLinha(i, j)) {
                    colocarNavio02(i, j, true);
                    qntNavioJaAlocado2++;
                    return true;
                }
            }
        }

        // Para o navio de 1 lugar
        if (qntNavioJaAlocado1 < NAVIO1_QNT) {
            if (j + TM_NAVIO_1 <= TM_TABULEIRO) {
                if (!navioNaColuna1(i, j)) {
                    colocarNavio01(i, j, false);
                    qntNavioJaAlocado1++;
                    return true;
                }
            } else if (i + TM_NAVIO_1 <= TM_TABULEIRO) {
                if (!navioNaLinha(i, j)) {
                    colocarNavio01(i, j, true);
                    qntNavioJaAlocado1++;
                    return true;
                }
            }
        }
        return false;
    }
    //===========================================================

    public static boolean navioNaLinha (int i, int j){
        return parteDoNavioNalinha (i, j, 'o') !=0;
    }

    public static int parteDoNavioNalinha (int i, int j, char simbolo){
        int contarNavios = 0;
        for (int k = j; k<(j + TM_NAVIO_4); k++){
            if(board[i][k]==simbolo) contarNavios++;
        }
        return contarNavios;
    }





    public static boolean navioNaColuna4 (int i, int j){ //verifica se tem algum navio na coluna / ocupando 4 poições
        return parteDoNavioNaColuna4 (i, j, 'o')!= 0;

    }
    public static int parteDoNavioNaColuna4 (int i, int j, char simbolo){
        int contarBarcos = 0;
        for( int k = i; i<(k + TM_NAVIO_4); k++){
            if (board[i][j]==simbolo) contarBarcos++;
        }
        return contarBarcos;
    }




    public static boolean navioNaColuna3 (int i, int j){ //verifica se tem algum navio na coluna ; 3 posições
        return parteDoNavioNaColuna3(i, j, 'o') != 0;

    }

    public static int parteDoNavioNaColuna3 (int i, int j, char simbolo){
        int contarBarcos = 0;
        for( int k = i; i<(k + TM_NAVIO_3); k++){
            if (board[i][k]==simbolo) contarBarcos++;
        }
        return contarBarcos;
    }

    public static boolean navioNaColuna2 (int i, int j){ //verifica se tem algum navio na coluna/2 posições
        return parteDoNavioNaColuna2 (i, j, 'o') != 0;

    }

    public static int parteDoNavioNaColuna2 (int i, int j, char simbolo){
        int contarBarcos = 0;
        for( int k = i; i<(k + TM_NAVIO_2); k++){
            if (board[i][j]==simbolo) contarBarcos++;
        }
        return contarBarcos;
    }






    public static boolean navioNaColuna1 (int i, int j){ //verifica se tem algum navio na coluna / 1 posição
        return parteDoNavioNaColuna1 (i, j, 'o') != 0;

    }

    public static int parteDoNavioNaColuna1 (int i, int j, char simbolo){
        int contarBarcos = 0;
        for( int k = i; k <(k + TM_NAVIO_1); k++){
            if (board[i][j]==simbolo) contarBarcos++;
        }
        return contarBarcos;
    }
















}
//====================Printar  o tabuleiro====================
// for (int i = 0; i < TM_TABULEIRO; i++) {
//            for (int j = 0; j < TM_TABULEIRO; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }