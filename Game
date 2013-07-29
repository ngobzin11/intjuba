package intjuba;

import java.util.LinkedList;

public class Game {

    private int board_height;
    private int board_width;
    private int pebbles;
    private int[] board;
    private int num_pockets;
    private int home;
    private int pebbles_in_hand;
    private LinkedList<Integer> skipped;

    public Game(int height, int width, int pebbles, int home) {
        this.board_height = height;
        this.board_width = width;
        this.pebbles = pebbles;
        this.num_pockets = height * width;
        this.board = new int[num_pockets];
        this.home = home;

        // Setup board
        setup();

    }

    public Game(int height, int width, int pebbles) {
        this.board_height = height;
        this.board_width = width;
        this.pebbles = pebbles;
        this.num_pockets = height * width;
        this.board = new int[num_pockets];
        this.home = intjuba.fixed.Parameters.home;

        // Setup board
        setup();

    }

    public Game() {
        board_height = Parameters.board_height;
        board_width = Parameters.board_width;
        pebbles = Parameters.pebbles_per_hole;
        num_pockets = board_height * board_width;
        board = new int[num_pockets];
        home = Parameters.home;

        // Setup board
        setup();
    }

    /**
     * Simulate a sequence of moves on a new board and return the number of pebbles
     * remaining at the end of the simulation
     *
     * @param sequence The sequence of moves to be simulated on the board
     * @return The number of pebbles remaining after sequence of moves is simulated
     */
    public void simulate(LinkedList<Integer> sequence) {

        for (int i = 0; i < sequence.size(); i++) {
            // Get the current operating pocket from the sequence
            int cur_pocket = sequence.get(i);

            // Get pebbles from the first pocket in the sequence
            pebbles_in_hand = board[cur_pocket];
            board[cur_pocket] = 0;

            if (pebbles_in_hand == 0) {
                skipped.add(i);
                continue;
            }

            while (true) {

                // Change the current pocket to the next in line
                cur_pocket = (cur_pocket == 9) ? 0 : cur_pocket + 1;

                // If there is one pebble in hand
                if (pebbles_in_hand == 1) {

                    // If the current pocket is home
                    if (isHome(cur_pocket)) {

                        // Drop pebble in pocket
                        dropPebble(cur_pocket);

                        // End of turn
                        break;
                    }

                    // If there are pebbles in the current pocket
                    if (board[cur_pocket] > 0) {

                        // Collect all the pebbles
                        collectPebbles(cur_pocket);

                        // There are no pebbles in the current pocket
                    } else {

                        // Move on to the next pocket
                        continue;
                    }

                    // There is more than one pebble in hand
                } else {

                    // Drop pebble in pocket
                    dropPebble(cur_pocket);
                }

            }

            // Stopping the game when we have a complete solution
            if (gameComplete()) {
                i++;
                for (;i < sequence.size(); i++) {
                    skipped.add(i);
                }
                return;
            }
        }
    }

    private void printBoard(int run) {
        int index = 0;
        System.out.println("Run: " + run);
        for (Integer box : board) {
            System.out.print("Box: " + index + "[" + box + "]" + ((index < board.length - 1) ? ", ": ""));
            index++;
        }
        System.out.println();
    }

    public LinkedList<Integer> getSkipped() {
        return skipped;
    }

    private boolean gameComplete() {
        return board[home] == (pebbles * num_pockets);
    }

    private void dropPebble(int cur_pocket) {
        pebbles_in_hand--;
        board[cur_pocket]++;
    }

    private void collectPebbles(int cur_pocket) {
        pebbles_in_hand += board[cur_pocket];
        board[cur_pocket] = 0;
    }

    private void setup() {
        // Add Pebbles to pockets
        for (int i = 0; i < num_pockets; i++) {
            board[i] = pebbles;
        }

        // Initialize skipped variable
        skipped = new LinkedList<Integer>();

        // Change default parameters to user defined parameters
        Parameters.board_height = board_height;
        Parameters.board_width = board_width;
        Parameters.pebbles_per_hole = pebbles;
        Parameters.home = home;
    }

    public int getRemainingPebbles() {
        int remaining = 0;

        for (int i = 0; i < num_pockets; i++) {
            if ((board[i] > 0) && !isHome(i)) {
                remaining += board[i];
            }
        }

        return remaining;
    }

    private boolean isHome(int index) {
        return home == index;
    }

    public int getHeight() {
        return board_height;
    }

    public int getWidth() {
        return board_width;
    }

    public int[] getBoard() {
        return board;
    }

    public int getNumpockets() {
        return num_pockets;
    }

    public void setNumpockets(int num_pockets) {
        this.num_pockets = num_pockets;
    }
}
