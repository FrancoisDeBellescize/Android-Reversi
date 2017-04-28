package com.example.francoisdebellescize.a02_reversi.Model;

/**
 * Created by francoisdebellescize on 03/04/2017.
 */

public class Player {
    public final static int EMPTY = 0;
    public final static int PLAYER_1 = 1;
    public final static int PLAYER_2 = 2;
    public static int CURRENT = PLAYER_1;

    public final static int PLAYER_1_COLOR = 0xFFFF6961;
    public final static int PLAYER_2_COLOR = 0xFF779ECB;

    public static void next(){
        CURRENT = CURRENT == PLAYER_1 ? PLAYER_2 : PLAYER_1;
    }
}
