package com.example.checkers;

import android.content.ClipData;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

class DragListener implements View.OnLongClickListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onLongClick(View v) {
        String tag = (String)v.getTag();
        ClipData clipData = ClipData.newPlainText("", tag);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
        v.startDragAndDrop(clipData, dragShadowBuilder, v, 0);
        return false;
    }
}
