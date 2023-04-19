package Tic_Tac_Toe;

import java.util.ArrayList;
import java.util.Scanner;

public class Game 
{
    static int called=0;//how many times minimax is called.

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        BitBoard board = new BitBoard();
        int input;

        while (!board.checkTie()) 
        {
            System.out.println("Enter the location to enter[1-9]");
            input = sc.nextInt();

            if (!board.makeMove(input)) 
            {
                System.out.println("Invalid move");
                continue;
            }

            board.displayBoard();
            if (board.checkWin()) 
            {
                System.out.println(board.getWinner() + " Won");
                System.out.printf("Minimax called--> %d times",called);
                sc.close();
                System.exit(0);
            }
            int[] eval=getEval(board);
            switch (eval[0]) {
                case 1:
                    System.out.println("Player Should Win");
                    break;

                case 0:
                    System.out.println("Should Tie");
                    break;
                    
                case -1:
                    System.out.println("Bot Should Win");
                    break;

                default:
                    System.out.println("Something Broke!");
                    break;
            }
            System.out.println("Best Move-->"+eval[1]);
        }
        System.out.println("Game Tied!");
        System.out.printf("Minimax called--> %d times",called);
        sc.close();
    }

    //eval[0]=evaluation,eval[1]=best move
    public static int[] getEval(BitBoard board){
        int bestBoardIndex=-1;
        int res[]=new int[2];
        if (board.isPlayerTurn()) 
        {
            int maxEval = Integer.MIN_VALUE;

            for (BitBoard child : getMoves(board)) 
            {
                int eval = minimax(child);
                if(eval>=maxEval){
                    maxEval=eval;
                    bestBoardIndex=child.lastLoc;
                }
            }
            res[0]=maxEval;
            res[1]=bestBoardIndex;
            return res;
        } 
        else 
        {
            int minEval = Integer.MAX_VALUE;
            for (BitBoard child : getMoves(board)) 
            {
                int eval=minimax(child);
                if(eval<=minEval){
                    minEval=eval;
                    bestBoardIndex=child.lastLoc;
                }
            }
            res[0]=minEval;
            res[1]=bestBoardIndex;
            return res;
        }
    }

    public static int minimax(BitBoard board) 
    {//TODO add better evals for positions which win faster
        called++;
        if (board.checkTie())
            return 0;
        if (board.checkWin())
            return board.isPlayerTurn()?-1:1;// if players move and win found bot must've won.

        if (board.isPlayerTurn()) 
        {
            int maxEval = Integer.MIN_VALUE;

            for (BitBoard child : getMoves(board)) 
            {
                int eval = minimax(child);
                maxEval = Math.max(eval, maxEval);
            }
            return maxEval;
        } 
        else 
        {
            int minEval = Integer.MAX_VALUE;
            for (BitBoard child : getMoves(board)) 
            {
                int eval=minimax(child);
                minEval=Math.min(eval, minEval);
            }
            return minEval;
        }

    }

    public static ArrayList<BitBoard> getMoves(BitBoard board) 
    {
        BitBoard currBoard = new BitBoard();
        ArrayList<BitBoard> moves = new ArrayList<>(9);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board.pos(i, j).isEmpty()) 
                {
                    currBoard = board.clone();
                    currBoard.makeMove(i * 3 + j + 1);
                    currBoard.lastLoc=i*3+j+1;

                    moves.add(currBoard);
                    currBoard = new BitBoard();
                }
        return moves;
    }
}