package com.example.francoisdebellescize.a02_reversi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.francoisdebellescize.a02_reversi.Model.Player;
import com.example.francoisdebellescize.a02_reversi.Model.Vector2d;

public class MainActivity extends AppCompatActivity {

    private TextView player_turn;
    private CustomView customView;
    private TextView score_player_1;
    private TextView score_player_2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void setScore(Vector2d score){
        score_player_1.setText(String.valueOf(score.x));
        score_player_2.setText(String.valueOf(score.y));
    }

    public void setTurn(){
        player_turn.setTextColor(Player.CURRENT == Player.PLAYER_1 ? Player.PLAYER_1_COLOR : Player.PLAYER_2_COLOR);
        player_turn.setText("Player " + (Player.CURRENT == Player.PLAYER_1 ? "1" : "2"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = (CustomView) findViewById(R.id.board);
        player_turn = (TextView) findViewById(R.id.player_turn);
        score_player_1 = (TextView) findViewById(R.id.player_1_score);
        score_player_2 = (TextView) findViewById(R.id.player_2_score);

        customView.reset();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                customView.reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
