package com.song.songup.coordinationtest.behavior.recycles;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/7 15:59
 * 修改备注：
 */
public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        float y= dependency.getHeight()+dependency.getTranslationY();
        if(y<0){
            y=0;
        }
        child.setY(y);
        return true;
    }
}
