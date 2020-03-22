package com.example.checkers;

import android.content.ClipData;
import android.os.Build;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

class DropListener implements View.OnDragListener {

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
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
}
