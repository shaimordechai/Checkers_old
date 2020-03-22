package com.example.checkers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final int BOARD_HIGH = 8;
    private static final int BOARD_WIDTH = 8;

    private int pointWidth;
    private int stonePadding;
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
        pointWidth = 50; // in dp
        stonePadding = 10; // in dp
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshButton = findViewById(R.id.refresh);
        endButton = findViewById(R.id.end);
        vPlayerName =  findViewById(R.id.playerName);
        vBoard = findViewById(R.id.board);
        vGameBoard = findViewById(R.id.gameBoard);

        initButtons();
        initGame();
        printGameBoard();
        printStones();
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
                StoneEnum currentPoint = board[i][j];
                if(currentPoint == null){
                    points[i][j].removeAllViews();
                    continue;
                }
                if (currentPoint != null) {
                    ImageView solider = new ImageView(points[i][j].getContext());
                    switch (currentPoint) {
                        case BLACK_SOLIDER:
                            solider.setImageResource(R.drawable.black_solider);
                            break;
                        case WHITE_SOLIDER:
                            solider.setImageResource(R.drawable.white_solider);
                            initDragListener(solider);
                            break;
                        case BLACK_KING:

                            break;
                        case WHITE_KING:

                            break;
                        default:
                            break;
                    }
                    points[i][j].addView(solider);
                }
            }
        }
    }

    private void initDragListener(ImageView solider) {
        solider.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                String tag = (String)v.getTag();
                ClipData clipData = ClipData.newPlainText("", tag);
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(clipData, dragShadowBuilder, v, 0);
                return false;
            }
        });
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
                points[i][j] = new PointOnBoard(vGameBoard.getContext());
                points[i][j].setPoint(new Point(j, i));
                points[i][j].setPadding(dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dpToPx(50), dpToPx(50));
                points[i][j].setLayoutParams(lp);

                whiteSoliders[i][j] = new ImageView(points[i][j].getContext());
                whiteSoliders[i][j].setImageResource(R.drawable.white_solider);
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
                points[i][j] = new PointOnBoard(vGameBoard.getContext());
                points[i][j].setPoint(new Point(j, i));
                points[i][j].setPadding(dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dpToPx(50), dpToPx(50));
                points[i][j].setLayoutParams(lp);
                if((i+j)%2 == 0) {
                    points[i][j].setBackgroundColor(Color.BLACK);
                }else{
                    points[i][j].setBackgroundColor(Color.WHITE);
                }
                initDropListener(points[i][j]);
                vGameBoard.addView( points[i][j]);
            }
        }

    }

    private void initDropListener(PointOnBoard point) {
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
