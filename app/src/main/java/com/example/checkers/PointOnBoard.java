package com.example.checkers;

import android.content.Context;
import android.graphics.Point;
import android.widget.FrameLayout;

public class PointOnBoard extends FrameLayout {
    private Point point;

    public PointOnBoard(Context context) {
        super(context);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
