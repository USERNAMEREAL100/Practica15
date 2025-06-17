public class GameBoard {
    public char[][] board;
    public int size;

    public GameBoard(int size) {
        this.size = size;
        board = new char[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = ' ';
    }
}