import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private JLabel statusLabel;
    private String playerXName;
    private String playerOName;
    private String currentPlayer;
    private boolean gameOver;

    public TicTacToeGUI() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 350);
        setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        playerXName = JOptionPane.showInputDialog("Enter name for Player X:");
        playerOName = JOptionPane.showInputDialog("Enter name for Player O:");
        currentPlayer = "X";
        gameOver = false;


        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(100, 100));
                button.setFont(new Font("Algerian", Font.PLAIN, 70));
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        statusLabel = new JLabel("Player " + currentPlayer + "'s turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetButtonClickListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean checkWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(currentPlayer) &&
                    buttons[row][1].getText().equals(currentPlayer) &&
                    buttons[row][2].getText().equals(currentPlayer)) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(currentPlayer) &&
                    buttons[1][col].getText().equals(currentPlayer) &&
                    buttons[2][col].getText().equals(currentPlayer)) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(currentPlayer) &&
                buttons[1][1].getText().equals(currentPlayer) &&
                buttons[2][2].getText().equals(currentPlayer)) {
            return true;
        }

        if (buttons[0][2].getText().equals(currentPlayer) &&
                buttons[1][1].getText().equals(currentPlayer) &&
                buttons[2][0].getText().equals(currentPlayer)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void showWinMessage() {
        String playerName = (currentPlayer.equals("X")) ? playerXName : playerOName;
        JOptionPane.showMessageDialog(this, "Congratulation " + playerName + " you win the Game!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void showDrawMessage() {
        JOptionPane.showMessageDialog(this, "It's a draw!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = "X";
        gameOver = false;
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().isEmpty() && !gameOver) {
                clickedButton.setText(currentPlayer);
                clickedButton.setEnabled(false);

                if (checkWin()) {
                    showWinMessage();
                    gameOver = true;
                } else if (checkDraw()) {
                    showDrawMessage();
                    gameOver = true;
                } else {
                    switchPlayer();
                }
            }
        }
    }

    private class ResetButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI game = new TicTacToeGUI();
            game.setVisible(true);
        });
    }
}
