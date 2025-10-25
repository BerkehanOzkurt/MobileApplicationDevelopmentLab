package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "x";
    static final String PLAYER_2 = "o";
    boolean player1_Turn = true;
    byte[][] board = new byte[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.board), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) tableRow.getChildAt(j);
                button.setOnClickListener(new CellListener(i, j));
            }
        }
    }

    public boolean isValidMove(int row, int column) {
        return board[row][column] == 0;
    }

    public int gameEnded(int row, int column) {
        int symbol = board[row][column];
        boolean win = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][column] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[row][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }
        win = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] != symbol) {
                win = false;
                break;
            }
        }
        if (win) {
            return symbol;
        }
        boolean draw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    draw = false;
                }
            }
        }
        if (draw) return 0;
        return -1;
}

    class CellListener implements View.OnClickListener {
        int row, column;

        public CellListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void onClick(View view) {
            if (!isValidMove(row, column)) {
                Toast.makeText(MainActivity.this, "Invalid move", Toast.LENGTH_LONG).show();
                return;
            }

            if(player1_Turn) {
                ((Button)view).setText(PLAYER_1);
                board[row][column] = 1;
            } else {
                ((Button)view).setText(PLAYER_2);
                board[row][column] = 2;
            }

            if (gameEnded(row, column) == -1) {
                player1_Turn =! player1_Turn;
            } else if (gameEnded(row, column) == 0) {
                Toast.makeText(MainActivity.this, "Draw", Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, column) == 1) {
                Toast.makeText(MainActivity.this, "Player 1 Wins", Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, column) == 2) {
                Toast.makeText(MainActivity.this, "Player 2 Wins", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn", player1_Turn);
        byte[] boardOneDimensional = new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardOneDimensional[3*i+j] = board[i][j];
            }
        }
        outState.putByteArray("board", boardOneDimensional);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1_Turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardOneDimensional = savedInstanceState.getByteArray("board");
        for (int i = 0; i < 9; i++) {
            board[i/3][i%3] = boardOneDimensional[i];
        }
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) tableRow.getChildAt(j);
                if (board[i][j] == 1) {
                    button.setText("x");
                } else if (board[i][j] == 2){
                    button.setText("o");
                }
            }
        }
    }
}