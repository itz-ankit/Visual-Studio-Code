package Tic_Tac_Toe;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static boolean makeMove(int loc, char[][] board, boolean player) {
        loc--;
        if (board[loc / 3][loc % 3] != ' ') {
            System.out.println("Position already occupied");
            return false;
        }
        board[loc / 3][loc % 3] = player ? 'X' : 'O';
        player = !player;
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] board = new char[3][3];
        boolean player = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }

        for (int i = 0; i < 9; i++) {
            int input;
            System.out.println("Enter the location.");
            input = sc.nextInt();

            if (makeMove(input, board, player))
                player = !player;
            else {
                i--;
                continue;
            }

            displayBoard(board);
            if (checkWin(board)) {
                System.out.println((!player ? "Player" : "Bot") + " Won!");
                System.exit(0);
            }
            int eval=minimax(board, player);
            switch (eval) {
                case 1:
                    System.out.println("Player should win");
                    break;
                
                case 0:
                    System.out.println("Should be tie");
                    break;
                case -1:
                    System.out.println("Bot should win");
                    break;

                default:
                    System.out.println("wrong eval "+eval);
                    break;
            }
        }

        System.out.println("It is a Tie!");
        sc.close();
    }

    public static int minimax(char[][] board,boolean player){
        if(checkTie(board))
            return 0;
        if(checkWin(board))
            return player?-1:1;//if players move and a win is found the bot must have won last turn.

        if(player){
            int maxEval=Integer.MIN_VALUE;
            for(char[][] child:getMoves(board, player)){
                int eval=minimax(child, !player);
                maxEval=Math.max(eval, maxEval);
            }
            return maxEval;
        }
        else{
            int minEval=Integer.MAX_VALUE;
            for(char[][] child:getMoves(board, player)){
                int eval=minimax(child, !player);
                minEval=Math.min(eval, minEval);
            }
            return minEval;
        }

    }

    public static ArrayList<char[][]> getMoves(char[][] board,boolean player){
        char move=player?'X':'O';
        char[][] currBoard;
        ArrayList<char[][]> moves=new ArrayList<>(9);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) 
                if(board[i][j]==' '){
                    currBoard=new char[3][3];
                    arrCopy(board,currBoard);
                    currBoard[i][j]=move;
                    moves.add(currBoard);
                }
        return moves;
    }

    public static void arrCopy(char[][] src,char[][] dest){
        for (int i = 0; i < dest.length; i++) {
            for (int j = 0; j < dest[i].length; j++) {
                dest[i][j]=src[i][j];
            }
        }
    }
}