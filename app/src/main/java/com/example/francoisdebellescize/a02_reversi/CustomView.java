package com.example.francoisdebellescize.a02_reversi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.francoisdebellescize.a02_reversi.Model.Board;
import com.example.francoisdebellescize.a02_reversi.Model.Cell;
import com.example.francoisdebellescize.a02_reversi.Model.Player;
import com.example.francoisdebellescize.a02_reversi.Model.Vector2d;

import static android.widget.Toast.LENGTH_SHORT;


public class CustomView extends View {
    private Cell board[][];
    private Paint grid_color, player_1_color, player_2_color, finish_text;
    private Vector2d action_down;
    private boolean finish = false;
    Vector2d score_players;

    public CustomView(Context c) {
        super(c);
        init();
    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);
        init();
    }

    private void init() {
        board = new Cell[Board.CELLS][Board.CELLS];
        score_players = new Vector2d(0, 0);

        for (int x = 0; x < Board.CELLS; x++){
            for (int y = 0; y < Board.CELLS; y++){
                board[x][y] = new Cell(x, y);
            }
        }

        grid_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        grid_color.setColor(Board.GRID_COLOR);

        player_1_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        player_1_color.setColor(Player.PLAYER_1_COLOR);

        player_2_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        player_2_color.setColor(Player.PLAYER_2_COLOR);

        finish_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        finish_text.setTextAlign(Paint.Align.CENTER);
        finish_text.setTextSize(100);
        finish_text.setColor(Board.GRID_COLOR);

        action_down = new Vector2d(0, 0);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 1; i < Board.CELLS; i++){
            canvas.drawLine(Board.CELLS_SIZE * i, 0, Board.CELLS_SIZE * i, Board.SIZE, grid_color);
            canvas.drawLine(0, Board.CELLS_SIZE * i, Board.SIZE, Board.CELLS_SIZE * i, grid_color);
        }

        for (int x = 0; x < Board.CELLS; x++){
            for (int y = 0; y < Board.CELLS; y++){
                board[x][y].draw(canvas, board[x][y].getPlayer() == Player.PLAYER_1 ? player_1_color : player_2_color);
            }
        }

        if (finish){
            canvas.drawText("Player " + (score_players.x > score_players.y ? "1" : "2") + " WIN !", Board.SIZE/2, Board.SIZE/2,
                    finish_text);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (finish)
            return true;

        int gridX = (int)(event.getX() / Board.SIZE * Board.CELLS);
        int gridY = (int)(event.getY() / Board.SIZE * Board.CELLS);


        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            action_down.set(gridX, gridY);
            invalidate();
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (gridX == action_down.getX() && gridY == action_down.getY()){
                putPieceOnBoard(gridX, gridY);
            }
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void putPieceOnBoard(int pos_x, int pos_y){
        if (checkReverse(pos_x, pos_y, false) && board[pos_x][pos_y].setPiece()){
            checkReverse(pos_x, pos_y, true);
            Player.next();
            refreshScore();

            if (!checkAvailableMoves())
                finish();
        }
        else{
            Toast.makeText(getContext(), "You can't put a piece here", LENGTH_SHORT).show();
        }
    }

    private boolean recCheck(Vector2d dir, Vector2d pos, boolean seeEnemy, boolean action){
        pos.add(dir);

        if (pos.x < 0 || pos.x >= Board.CELLS || pos.y < 0 || pos.y >= Board.CELLS)
            return false;

        if (board[pos.x][pos.y].getPlayer() == Player.CURRENT && seeEnemy){
            return true;
        }

        if (board[pos.x][pos.y].getPlayer() != Player.CURRENT
                && board[pos.x][pos.y].getPlayer() != Player.EMPTY){
            if (recCheck(dir, pos, true, action)){
                pos.remove(dir);
                if (action)
                    board[pos.x][pos.y].setPlayer(Player.CURRENT);
                return true;
            }
        }
        return false;
    }

    private boolean checkReverse(int x, int y, boolean action){
        boolean ret = false;
        for (int dirX = -1; dirX <= 1; dirX++){
            for (int dirY = -1; dirY <= 1; dirY++){
                if (dirX != 0 || dirY != 0)
                    if (recCheck(new Vector2d(dirX, dirY), new Vector2d(x, y), false, action))
                        ret = true;
            }
        }
        return ret;
    }

    private boolean checkAvailableMoves(){
        boolean ret = false;
        for (int x = 0; x < Board.CELLS; x++){
            for (int y = 0; y < Board.CELLS; y++){
                if (board[x][y].getPlayer() == Player.EMPTY && checkReverse(x, y, false))
                ret = true;
            }
        }
        return ret;
    }

    private void refreshScore(){
        score_players.x = 0;
        score_players.y = 0;
        for (int x = 0; x < Board.CELLS; x++){
            for (int y = 0; y < Board.CELLS; y++){
                if (board[x][y].getPlayer() == Player.PLAYER_1)
                    ++score_players.x;
                else if (board[x][y].getPlayer() == Player.PLAYER_2)
                    ++score_players.y;
            }
        }
        ((MainActivity)getContext()).setScore(score_players);
        ((MainActivity)getContext()).setTurn();
    }

    public void reset(){
        for (int x = 0; x < Board.CELLS; x++){
            for (int y = 0; y < Board.CELLS; y++){
                if ((x == 3 && y == 3) || (x == 4 && y == 4))
                    board[x][y].setPlayer(Player.PLAYER_1);
                else if ((x == 4 && y == 3) || (x == 3 && y == 4))
                    board[x][y].setPlayer(Player.PLAYER_2);
                else
                    board[x][y].setPlayer(Player.EMPTY);
            }
        }
        finish = false;
        Player.CURRENT = Player.PLAYER_1;
        refreshScore();

        invalidate();
    }

    private void finish(){
        finish = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            Board.SIZE = height;
        } else {
            Board.SIZE = width;
        }
        Board.CELLS_SIZE = Board.SIZE / Board.CELLS;
        setMeasuredDimension(Board.SIZE, Board.SIZE);
    }
}