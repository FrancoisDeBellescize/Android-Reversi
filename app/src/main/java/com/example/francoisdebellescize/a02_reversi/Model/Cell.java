package com.example.francoisdebellescize.a02_reversi.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by francoisdebellescize on 03/04/2017.
 */

public class Cell {
    private int x;
    private int y;
    private int player = Player.EMPTY;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;

        if ((x == 3 && y == 3) || (x == 4 && y == 4))
            this.player = Player.PLAYER_1;
        else if ((x == 4 && y == 3) || (x == 3 && y == 4))
            this.player = Player.PLAYER_2;
    }

    public int getPlayer(){
        return player;
    }

    public void draw(Canvas canvas, Paint paint){
        if (player != Player.EMPTY){
            float coord_x = (x * Board.CELLS_SIZE) + (Board.CELLS_SIZE / 2);
            float coord_y = (y * Board.CELLS_SIZE) + (Board.CELLS_SIZE / 2);
            canvas.drawCircle(coord_x, coord_y, Board.CELLS_SIZE / 2, paint);
        }
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public boolean setPiece(){
        if (player != Player.EMPTY)
            return false;

        player = Player.CURRENT;
        return true;
    }
}
