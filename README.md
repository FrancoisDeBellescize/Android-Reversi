# Reversi

## Models

### Vector2d

This model only contain two int. I use it in general to pass coordinate as parameter.

    public int x;
    public int y;

### Board

It will only contain some informations to the game.

    final static int GRID_COLOR = 0xFFD3D3D3;
    final static int CELLS = 8;
    static int SIZE;
    static float CELLS_SIZE;

The `GRID_COLOR` is the color of the line of the grid.

The `CELLS` is the number of the cells of the game.

The `SIZE` is the size in dp of the board.

The `CELLS_SIZE` is the `SIZE` divided by the `CELLS` to know the size of a cell in dp;

### Player

It will keep some informations like the current player.

    final static int EMPTY = 0;
    final static int PLAYER_1 = 1;
    final static int PLAYER_2 = 2;
    static int CURRENT = PLAYER_1;

    final static int PLAYER_1_COLOR = 0xFFFF6961;
    final static int PLAYER_2_COLOR = 0xFF779ECB;

EMPTY, PLAYER_1 and PLAYER_2 are used to define the players.

CURRENT is the current player.

PLAYER_1_COLOR and PLAYER_2_COLOR is the variables to store the colors of the players.

The class Player contain a function next() to change the current player.

> static void next()

### Cell

This is a cell of the board. BOARD.CELLS * BOARD.CELLS will bi generate.

It contains coordinate and an integer to know if there is a player on this cell.

     int x;
     int y;
     int player = Player.EMPTY;

There is a function draw that will display the cells if there is a circle if there is a player on.

    public void draw(Canvas canvas, Paint paint){
        if (player != Player.EMPTY){
            float coord_x = (x * Board.CELLS_SIZE)
              + (Board.CELLS_SIZE / 2);
            float coord_y = (y * Board.CELLS_SIZE)
              + (Board.CELLS_SIZE / 2);
            canvas.drawCircle(coord_x, coord_y,
               Board.CELLS_SIZE / 2, paint);
        }
    }



## MainActivity

### Template

For the activity_main Template, i'm using a `LinearLayout` to align vertically my elements.

A `CustomView` to display my board. It's give me the possibility to display elements on a canvas.

After the `CustomView`, i'm using a `LinearLayout` to split horizontally my informations.

Inside this `LinearLayout`, i'm using another `LinearLayout` at the left to display two `TextView` to tell to the players, who's the turn.

Another `LinearLayout` at the right to display the score of the players.

### Variables

I'm using four class variables in the main Activity :

    TextView player_turn;
    CustomView customView;
    TextView score_player_1;
    TextView score_player_2;

Three `TextView` to be able to modify the text and the `customView` to be able to call functions for manage the game.

### Functions

> onCreate(Bundle savedInstanceState)

This is the function to initialize my variables. It's call automatically.

> onCreateOptionsMenu(Menu menu)

This function is an override. It will create the menu to be able to reset the game.

> onOptionsItemSelected(MenuItem item)

It's an override too and this is to handle a clic on an item on the menu.

> setScore(Vector2d score)

This is a function to modify the text of the `TextView` to display the score.

> setTurn()

It will set the color and the text to display who have to play

## CustomView

### Variables

    Cell board[][];
    Paint grid_color, player_1_color, player_2_color, finish_text;
    Vector2d action_down;
    boolean finish = false;
    Vector2d score_players;

The board is an array of `Cell` to contain all the cells of the game.

The `grid_color`, `player_1_color`, `player_2_color` and the `finish_text` are the `Paint` to contains the color of different elements.

The `action_down` is the coordinate stocked to know where the player start to touch the screen.

The `boolean` `finish` is to know if the game is currently running.

The `score_players` is a variable to store the actual score of the both players.

### Functions

> init()

This function will initialize all the variables including the board and each cells.

> onDraw(Canvas canvas)

It will draw the grid and each cell where there is a player.

> onTouchEvent(MotionEvent event)

`onTouchEvent` is the function to handle a touch on the screen.

> putPieceOnBoard(int pos_x, int pos_y)

This function will try to put a piece on the board.

> checkReverse(int x, int y, boolean action)

> recCheck(Vector2d dir, Vector2d pos, boolean seeEnemy, boolean action)

The function checkReverse will call `recCheck`. This is a recursive function to check if it's possible to reverse a piece.

> checkAvailableMoves()

It will call `recCheck `to determine if the player can do an action or not.

> refreshScore()

It will look at each cells to count the score of both player and update the variable `score_players`

> reset()

This function is call from the button reset on the `MainActivity`. It will reset all variables to start a new game.

> onMeasure(int widthMeasureSpec, int heightMeasureSpec)

It will set the `SIZE` of the board make the custom view a square.
