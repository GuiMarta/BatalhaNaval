package CodigoBatalhaNaval;

import java.util.Random;
import java.util.Scanner;

public class CodigoBatalhaNaval{
    public static void main(String[] args) { // principal
        Scanner ler = new Scanner(System.in);


        char[][] mapaJogadorA = criarMapa();
        char[][] mapaJogadorB = criarMapa();

        System.out.println("Bem-vindo ao jogo de Batalha Naval da Maria e do Marta!");

        System.out.println("Escolha uma opção:");
        System.out.println("Computador - Jogue contra o computador");
        System.out.println("Jogador - Jogue contra outro jogador");
        String opcao = ler.nextLine();


        if (opcao.equalsIgnoreCase("Computador")) {
            jogarContraComputador(mapaJogadorA, mapaJogadorB);
        } else if (opcao.equalsIgnoreCase("Jogador")) {
            jogarContraJogador(mapaJogadorA, mapaJogadorB, ler);
        } else {
            System.out.println("Opção inválida! Jogo encerrado!");
        }
        ler.close();
    }
    //========================================================//========================================================
    public static char[][] criarMapa() { // criando mapa de matriz 10x10
        char[][] mapa = new char[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapa[i][j] = '-';
            }
        }

        return mapa;
    }
    //========================================================//========================================================
    public static void colocarNavio(char[][] mapa, int linha, int coluna, int comprimento, boolean vertical) {
        if (verificaPosicao(mapa, linha, coluna, comprimento, vertical)) { //passou como vdd pelo verificaposicao e caí dentro do if
            if (vertical) {
                for (int i = 0; i < comprimento; i++) {
                    mapa[linha + i][coluna] = 'B'; //incrementando o navio na linha
                }
            } else {
                for (int i = 0; i < comprimento; i++) {
                    mapa[linha][coluna + i] = 'B'; //incrementando o navio na coluna
                }
            }
        } else {
            System.out.println("Posição inválida para o navio!");
        }
    }
    //========================================================//========================================================
    public static void colocarBarcoAutomatico(char[][] mapa) {
        Random random = new Random();

        // Colocar um navio de 4 posições aleatóriamente
        colocarNavio(mapa, random.nextInt(7), random.nextInt(7), 4, random.nextBoolean());

        // Colocar dois navios de 3 posições aleatóriamente
        for (int i = 0; i < 2; i++) {
            colocarNavio(mapa, random.nextInt(8), random.nextInt(8), 3, random.nextBoolean());
        }

        // Colocar três navios de 2 posições aleatóriamente
        for (int i = 0; i < 3; i++) {
            colocarNavio(mapa, random.nextInt(9), random.nextInt(9), 2, random.nextBoolean());
        }

        // Colocar quatro navios de 1 posição aleatóriamente
        for (int i = 0; i < 4; i++) {
            colocarNavio(mapa, random.nextInt(10), random.nextInt(10), 1, random.nextBoolean());
        }
    }
    //========================================================//========================================================
    public static boolean verificaPosicao(char[][] mapa, int linha, int coluna, int comprimento, boolean vertical) {

        if (vertical) { //computador tenta alocar na horizontal
            if (linha + comprimento > mapa.length) {
                return false;  // Navio sai para fora da ultima linha do mapa
            }
            for (int i = 0; i < comprimento; i++) {
                if (mapa[linha + i][coluna] != '-') {
                    return false;  // Espaço já está ocupado por outro navio
                }
            }
        }
        else { //boleano (vertical)  cai falso e o computador tenta alocar na vertical
            if (coluna + comprimento > mapa[linha].length) {
                return false;  // Navio sai para fora da ultima coluna do mapa
            }
            for (int i = 0; i < comprimento; i++) {
                if (mapa[linha][coluna + i] != '-') {
                    return false;  // Espaço já está ocupado por outro navio
                }
            }
        }
        return true; //verifica a posição e se saí como true deixa alocar o navio (boolean vertical)
    }
    //========================================================//========================================================
    public static void jogarContraComputador(char[][] mapaJogadorA, char[][] mapaJogadorB) {
        Scanner ler = new Scanner(System.in);
        Random aleatorio = new Random();
        System.out.println(" ");
        System.out.println("Jogo contra o computador!\n Seu objetivo é afundar todos os navios\n A - água\n K - Návio");
        System.out.println("-------------------------");
        System.out.println("Informe seu nome: ");
        String nome = ler.nextLine();

        colocarBarcoAutomatico(mapaJogadorA);
        colocarBarcoAutomatico(mapaJogadorB);

        boolean vezJogadorA = true;
        boolean finalJogo = false;

        while (!finalJogo) {
            if (vezJogadorA) {
                System.out.println(" " + nome + " sua vez de atirar!");

                mostrarMapa(mapaJogadorB);

                System.out.print("Informe a letra da coluna para atirar (A-J): ");
                String colunaStr = ler.nextLine().toUpperCase();
                System.out.print("Informe o número da linha para atirar (0-9): ");
                int linha = ler.nextInt();
                ler.nextLine();

                int coluna = colunaStr.charAt(0) - 'A';

                if (mapaJogadorB[linha][coluna] == '-' || mapaJogadorB[linha][coluna] == 'K') { //se jogar e cair como agua ou tentar atirar em algum lugar ja atirado
                    if (mapaJogadorB[linha][coluna] == '-') {
                        System.out.println("Água!");
                        mapaJogadorB[linha][coluna] = 'A';
                    } else {
                        System.out.println("Você já atirou nessa posição!");
                    }

                    vezJogadorA = false;
                } else if (mapaJogadorB[linha][coluna] == 'B') { // se jogar e cair em cima do navio adversario
                    System.out.println("Você acertou um barco!");
                    mapaJogadorB[linha][coluna] = 'K';

                    if (barcosAfundados(mapaJogadorB)) {
                        System.out.println("Parabéns, você venceu!");
                        finalJogo = true;
                    }
                }
            } else {
                System.out.println("Agora é a vez do computador:");

                int linha = aleatorio.nextInt(10);
                int coluna = aleatorio.nextInt(10);

                if (mapaJogadorA[linha][coluna] == '-' || mapaJogadorA[linha][coluna] == 'K') {
                    if (mapaJogadorA[linha][coluna] == '-') {
                        System.out.println("Água!!!");
                        mapaJogadorA[linha][coluna] = 'A';
                    } else {
                        System.out.println("O computador já atirou nessa posição!");
                    }

                    vezJogadorA = true;
                } else if (mapaJogadorA[linha][coluna] == 'B') {
                    System.out.println("O computador acertou um barco!");
                    mapaJogadorA[linha][coluna] = 'K';

                    if (barcosAfundados(mapaJogadorA)) {
                        System.out.println("O computador venceu!");
                        finalJogo = true;
                    }
                }
            }
        }


    }
    //========================================================//========================================================
    public static void jogarContraJogador(char[][] mapaJogadorA, char[][] mapaJogadorB, Scanner ler) {

        System.out.println(" ");
        System.out.println("   Jogo contra Jogador! \n Seu objetivo é afundar todos os navios\n A - água\n K - Návio  ");
        System.out.println("-------------------------");

        System.out.println("Informe o nome dos jogadores.\n Jogador A:");
        String jogadorA = ler.nextLine();
        System.out.println("\n Jogador B:");
        String jogadorB = ler.nextLine();

        System.out.println(jogadorA + " Alocar seus barcos!");

       colocarBarcos(mapaJogadorA, ler);

        System.out.println(jogadorB + " Alocar seus barcos!");

        colocarBarcos(mapaJogadorB, ler);

        boolean vezJogadorA = false;
        boolean finalJogo = false;

        while (!finalJogo) {
            if (vezJogadorA) {
                System.out.println("\n"+jogadorA+", sua vez de atirar:");

                mostrarMapa(mapaJogadorB);

                System.out.print("Informe a letra da coluna para atirar (A-J): ");
                String colunaStr = ler.nextLine();
                System.out.print("Informe o número da linha para atirar (0-9): ");
                int linha = ler.nextInt();
                ler.nextLine();
                int coluna = colunaStr.charAt(0) - 'a';


                if (mapaJogadorB[linha][coluna] == '-' || mapaJogadorB[linha][coluna] == 'K') {
                    if (mapaJogadorB[linha][coluna] == '-') {
                        System.out.println("Água!");
                        mapaJogadorB[linha][coluna] = 'A';
                    } else {
                        System.out.println("Você já atirou nessa posição!");
                    }
                    vezJogadorA = false;
                }

                else if (mapaJogadorB[linha][coluna] == 'B') {

                    System.out.println("Você acertou um barco!");
                    mapaJogadorB[linha][coluna] = 'K';

                    if (barcosAfundados(mapaJogadorB)) {
                        System.out.println(jogadorA+" venceu!!!");
                        finalJogo = true;
                    }

                }
            }

            else { //Jogador B jogando :
                System.out.println("\n\n"+jogadorB+", sua vez de atirar:");

                mostrarMapa(mapaJogadorA);

                System.out.print("Informe a letra da coluna para atirar (A-J): ");
                String colunaStr = ler.nextLine();
                System.out.print("Informe o número da linha para atirar (0-9): ");
                int linha = ler.nextInt();
                ler.nextLine();

                int coluna = colunaStr.charAt(0) - 'a';

                if (mapaJogadorA[linha][coluna] == '-' || mapaJogadorA[linha][coluna] == 'K') {
                    if (mapaJogadorA[linha][coluna] == '-') {
                        System.out.println("\nÁgua!");
                        mapaJogadorA[linha][coluna] = 'A';
                    } else {
                        System.out.println("Você já atirou nessa posição!");
                    }

                    vezJogadorA = true;

                } else if (mapaJogadorA[linha][coluna] == 'B') {
                    System.out.println("Você acertou um barco!");
                    mapaJogadorA[linha][coluna] = 'K';

                    if (barcosAfundados(mapaJogadorA)) {
                        System.out.println(jogadorB+"Venceu!");
                        finalJogo = true;
                    }
                }
            }
        }

    }

    //========================================================//========================================================
    public static void colocarBarcos(char[][] mapa, Scanner ler) {
        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int tamanho : tamanhos) {

            boolean alocado = false;

            while (!alocado) {

                System.out.print("Digite a posição inicial (coluna letra + linha número) para o barco de tamanho " + tamanho + ": ");
                String posicao = ler.nextLine();

                char letraColuna = posicao.charAt(0);
                int linha = Integer.parseInt(posicao.substring(1));

                int coluna = letraColuna - 'a';   // Calculo responsavel por entregar a difernça de A (na tabela
                // ASCII)  para representar a conversão em numeros


                System.out.print("Digite a orientação (H - horizontal, V - vertical) para o barco de tamanho " + tamanho + ": ");
                String orientacao = ler.nextLine().toUpperCase();


                if (orientacao.equals("H")) {  //  Caso o usuario decida alocar na orientação horizontal

                    if (coluna + tamanho <= mapa[0].length) { // Verifica se o barco cabe dentro da linha
                        boolean espacoDisponivel = true;
                        for (int i = coluna; i < coluna + tamanho; i++) {
                            if (mapa[linha][i] != '-') {
                                espacoDisponivel = false;
                                break;
                            }
                        }
                        if (espacoDisponivel) {
                            for (int i = coluna; i < coluna + tamanho; i++) {
                                mapa[linha][i] = 'B'; // Substitui os caracteres do mapa pelo tamanho do barco
                            }
                            System.out.println("Barco alocado com sucesso!");
                            alocado = true;

                        }
                        else {
                            System.out.println("Posição já ocupada. Tente novamente.");
                        }
                    }
                    else {
                        System.out.println("Não é possível alocar o barco nessa posição. Tente novamente.");
                    }

                }

                else if (orientacao.equals("V")) {  // Orientação vertical

                    if (linha + tamanho <= mapa.length) { // Verifica se o barco cabe dentro da coluna
                        boolean espacoDisponivel = true;
                        for (int i = linha; i < linha + tamanho; i++) {
                            if (mapa[i][coluna] != '-') {
                                espacoDisponivel = false;
                                break;
                            }
                        }
                        if (espacoDisponivel) {
                            for (int i = linha; i < linha + tamanho; i++) {
                                mapa[i][coluna] = 'B'; // Substitui os caracteres do mapa pelo tamanho do barco
                            }
                            alocado = true;
                        } else {
                            System.out.println("Posição já ocupada. Tente novamente.");
                        }
                    } else {
                        System.out.println("Não é possível alocar o barco nessa posição. Tente novamente.");
                    }

                }
                else {
                    System.out.println("Orientação inválida. Digite 'H' para horizontal ou 'V' para vertical.");
                }
                mostrarMapaComTabuleiro(mapa);
            }
        }
    }
    //========================================================//========================================================
    //verificação dos barcos afundados
    public static boolean barcosAfundados(char[][] mapa) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == 'B') {
                    return false;  // Ainda há barcos no mapa
                }
            }
        }
        return true;  // Todos os barcos foram afundados
    }

    //========================================================//========================================================
    public static void mostrarMapa(char[][] mapa) {
        System.out.println("  A B C D E F G H I J ");


        for (int i = 0; i < mapa.length; i++) {

            System.out.print(i + " ");
            for (int j = 0; j < mapa[i].length; j++) {
                char c = mapa[i][j];

                    if (c == 'B' || c == '-') {
                        System.out.print("- ");
                    }
                    else {
                        System.out.print(c + " ");
                    }
            }
            System.out.println();
        }
        System.out.println();

    }
    public static void mostrarMapaComTabuleiro(char[][] mapa) {
        System.out.println("  A B C D E F G H I J ");


        for (int i = 0; i < mapa.length; i++) {

            System.out.print(i + " ");
            for (int j = 0; j < mapa[i].length; j++) {
                char c = mapa[i][j];
                    System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();

    }
}
