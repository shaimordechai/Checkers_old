package com.example.checkers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private int pointWidth;
    private int stonePadding;
    private Button endButton;
    private FrameLayout vBoard;
    private GridLayout vGameBoard;
    private GameDTO gameDTO;
    private FrameLayout point[][];

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        endButton = findViewById(R.id.end);
        vBoard = findViewById(R.id.board);
        vGameBoard = findViewById(R.id.gameBoard);
        pointWidth = 50; // in dp
        stonePadding = 10; // in dp

        initButtons();
        initGame();
        printGameBoard();
        printStones();

       /*for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final FrameLayout point = new FrameLayout(vGameBoard.getContext());
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dpToPx(pointWidth), dpToPx(pointWidth));
                point.setLayoutParams(lp);


                if ((i + j) % 2 == 0) {
                    point.setBackgroundColor(Color.WHITE);
                    final ImageView solider = new ImageView(point.getContext());
                    point.addView(solider);
                    point.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
                    solider.setImageResource(R.drawable.black_solider);
                    solider.setOnLongClickListener(new View.OnLongClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public boolean onLongClick(View v) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadow = new View.DragShadowBuilder(solider);
                            v.startDragAndDrop(data, shadow, null, 0);
                            return false;
                        }
                    });
                } else {
                    point.setBackgroundColor(Color.BLACK);
                }

                if (i == 0 && j == 0) {
                    point.setOnDragListener(new View.OnDragListener() {
                        @Override
                        public boolean onDrag(View v, DragEvent event) {
                            switch (event.getAction()) {
                                case DragEvent.ACTION_DRAG_STARTED:
                                    point.setBackgroundColor(Color.BLUE);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                }
                vGameBoard.addView(point);
            }
        }*/
    }

    private void initButtons() {
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(1000);
                rotate.setInterpolator(new LinearInterpolator());
               vBoard.startAnimation(rotate);
            }
        });
    }

    private void printStones() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                ImageView solider = new ImageView(point[i][j].getContext());
                StoneEnum currentPoint = gameDTO.getGameBoard()[i][j];
                if (currentPoint != null) {
                    switch (currentPoint) {
                        case BLACK_SOLIDER:
                            solider.setImageResource(R.drawable.black_solider);
                            break;
                        case WHITE_SOLIDER:
                            solider.setImageResource(R.drawable.white_solider);
                            break;
                        case BLACK_KING:

                            break;
                        case WHITE_KING:

                            break;
                        default:
                            break;
                    }
                    point[i][j].addView(solider);
                }
            }
        }
    }

    private void initGame() {
        gameDTO = new GameDTO();
        point = new FrameLayout[8][8];
    }

    private void printGameBoard() {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                point[i][j] = new FrameLayout(vGameBoard.getContext());
                point[i][j].setPadding(dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding), dpToPx(stonePadding));
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dpToPx(50), dpToPx(50));
                point[i][j].setLayoutParams(lp);
                if((i+j)%2 == 0) {
                    point[i][j].setBackgroundColor(Color.BLACK);
                }else{
                    point[i][j].setBackgroundColor(Color.WHITE);
                }
                vGameBoard.addView( point[i][j]);
            }
        }

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
