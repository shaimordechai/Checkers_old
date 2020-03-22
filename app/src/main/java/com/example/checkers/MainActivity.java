package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int BOARD_HIGH = 8; //points
    private static final int BOARD_WIDTH = 8; //points
    private static final int POINT_HIGH = 50; //dp
    private static final int POINT_WIDTH = 50; //dp
    private static final int STONE_PADDING = 10; //dp

    private Button endButton;
    private Button refreshButton;
    private TextView vPlayerName;
    private FrameLayout vBoard;
    private GridLayout vGameBoard;
    private GameDTO gameDTO;
    private PointOnBoard points[][];
    private ImageView whiteSoliders[][];
    private ImageView blackSoliders[][];
    private ImageView whiteKings[][];
    private ImageView blackKings[][];
    private ImageView currentStone;
    private PointOnBoard from;
    private PointOnBoard to;
    private Point eatPoint;



    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initGame();
        initButtons();
        printGameBoard();
        printStones();
        initDragListener();
        initDropListener();
    }

    private void initViews() {
        refreshButton = findViewById(R.id.refresh);
        endButton = findViewById(R.id.end);
        vPlayerName =  findViewById(R.id.playerName);
        vBoard = findViewById(R.id.board);
        vGameBoard = findViewById(R.id.gameBoard);
    }

    private void initButtons() {
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameDTO.updateOldGameBoard();
                gameDTO.switchPlayer();
                vPlayerName.setText(gameDTO.getCurrentPlayer().toString());
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameDTO.refreshGameBoard();
                printStones();
            }
        });
    }

    private void printStones() {
        StoneEnum[][] board = gameDTO.getOldGameBoard();
        for(int i = 0; i < BOARD_HIGH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++) {
                switch (board[i][j]){
                    case WHITE_SOLIDER:
                        whiteSoliders[i][j].setVisibility(View.VISIBLE);
                        blackSoliders[i][j].setVisibility(View.GONE);
                        whiteKings[i][j].setVisibility(View.GONE);
                        blackKings[i][j].setVisibility(View.GONE);
                        break;
                    case BLACK_SOLIDER:
                        whiteSoliders[i][j].setVisibility(View.GONE);
                        blackSoliders[i][j].setVisibility(View.VISIBLE);
                        whiteKings[i][j].setVisibility(View.GONE);
                        blackKings[i][j].setVisibility(View.GONE);
                        break;
                    case WHITE_KING:
                        whiteSoliders[i][j].setVisibility(View.GONE);
                        blackSoliders[i][j].setVisibility(View.GONE);
                        whiteKings[i][j].setVisibility(View.VISIBLE);
                        blackKings[i][j].setVisibility(View.GONE);
                        break;
                    case BLACK_KING:
                        whiteSoliders[i][j].setVisibility(View.GONE);
                        blackSoliders[i][j].setVisibility(View.GONE);
                        whiteKings[i][j].setVisibility(View.GONE);
                        blackKings[i][j].setVisibility(View.VISIBLE);
                        break;
                    default:
                        whiteSoliders[i][j].setVisibility(View.GONE);
                        blackSoliders[i][j].setVisibility(View.GONE);
                        whiteKings[i][j].setVisibility(View.GONE);
                        blackKings[i][j].setVisibility(View.GONE);
                        break;

                }

            }
        }
    }

    private void initDragListener() {
        for(int i = 0; i < BOARD_HIGH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++) {
                whiteSoliders[i][j].setOnLongClickListener(new DragListener());
                blackSoliders[i][j].setOnLongClickListener(new DragListener());
                whiteKings[i][j].setOnLongClickListener(new DragListener());
                blackKings[i][j].setOnLongClickListener(new DragListener());
            }
        }
    }


    private void initGame() {
        gameDTO = new GameDTO();
        points = new PointOnBoard[BOARD_HIGH][BOARD_WIDTH];
        whiteSoliders = new ImageView[BOARD_HIGH][BOARD_WIDTH];
        blackSoliders = new ImageView[BOARD_HIGH][BOARD_WIDTH];
        whiteKings = new ImageView[BOARD_HIGH][BOARD_WIDTH];
        blackKings = new ImageView[BOARD_HIGH][BOARD_WIDTH];

        for(int i = 0; i < BOARD_HIGH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++) {
                Point point = new Point(j, i);
                points[i][j] = new PointOnBoard(vGameBoard.getContext());
                points[i][j].setPoint(point);
                points[i][j].setPadding(dpToPx(STONE_PADDING), dpToPx(STONE_PADDING), dpToPx(STONE_PADDING), dpToPx(STONE_PADDING));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dpToPx(POINT_WIDTH), dpToPx(POINT_HIGH));
                points[i][j].setLayoutParams(lp);

                whiteSoliders[i][j] = new ImageView(points[i][j].getContext());
                whiteSoliders[i][j].setImageResource(R.drawable.white_solider);
                whiteSoliders[i][j].setVisibility(View.GONE);

                blackSoliders[i][j] = new ImageView(points[i][j].getContext());
                blackSoliders[i][j].setImageResource(R.drawable.black_solider);
                blackSoliders[i][j].setVisibility(View.GONE);

                whiteKings[i][j] = new ImageView(points[i][j].getContext());
                whiteKings[i][j].setVisibility(View.GONE);

                blackKings[i][j] = new ImageView(points[i][j].getContext());
                blackKings[i][j].setVisibility(View.GONE);
            }
        }
    }

    private void printGameBoard() {
        vPlayerName.setText(gameDTO.getCurrentPlayer().toString());
        if(!gameDTO.isGameOwner()){
            vBoard.setRotation(180);
        }
        for(int i = 0; i < BOARD_HIGH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++) {
                if((i + j) % 2 == 0) {
                    points[i][j].setBackgroundColor(Color.BLACK);
                }else{
                    points[i][j].setBackgroundColor(Color.WHITE);
                }
                vGameBoard.addView( points[i][j]);
            }
        }

    }

    private void initDropListener(PointOnBoard point) {
        for (int i = 0; i < BOARD_HIGH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                whiteSoliders[i][j].setOnDragListener(new DropListener());
                blackSoliders[i][j].setOnDragListener(new DropListener());
                whiteKings[i][j].setOnDragListener(new DropListener());
                blackKings[i][j].setOnDragListener(new DropListener());
            }
        }

        point.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                currentStone = (ImageView) event.getLocalState();
                switch (event.getAction()){
                    case DragEvent.ACTION_DRAG_EXITED:
                        PointOnBoard temp = (PointOnBoard) currentStone.getParent();
                        if(temp != null){
                            from = temp;
                            from.removeAllViews();
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        to = (PointOnBoard) v;
                        if(gameDTO.isMove(from.getPoint(), to.getPoint())){
                            gameDTO.move(from.getPoint(), to.getPoint());
                            to.addView(currentStone);
                            break;
                        }
                        if(gameDTO.isEat(from.getPoint(), to.getPoint())){
                            gameDTO.eat(from.getPoint(), to.getPoint());
                            eatPoint = gameDTO.getEatPoint(from.getPoint(), to.getPoint());
                            points[eatPoint.y][eatPoint.x].removeAllViews();
                            to.addView(currentStone);
                            break;
                        }
                        from.addView(currentStone);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        if(gameDTO.isMove(from.getPoint(), to.getPoint())){

                        }
                        currentStone = null;
                        from = null;
                        to = null;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
