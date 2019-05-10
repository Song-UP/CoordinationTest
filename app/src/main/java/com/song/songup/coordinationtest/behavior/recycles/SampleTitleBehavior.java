package com.song.songup.coordinationtest.behavior.recycles;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/6 20:13
 * 修改备注：
 */
public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    public final String TAG = SampleTitleBehavior.class.getSimpleName();
    //顶部和title底部重合时，列表互动距离
    private float deltaY;

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0){
            deltaY = dependency.getY() - child.getHeight();
            Log.d(TAG,"depency.y:"+dependency.getY()+"\tchild.height:"+child.getHeight()+"\tdeltal:"+deltaY);
        }
        float dy = dependency.getY() - child.getHeight();
        dy = dy<0?0:dy;
        float y = -(dy/deltaY)*child.getHeight();

        Log.d(TAG,"dy:"+dy+"\ty:"+y);
        float alpha = 1 - (dy / deltaY);
        child.setAlpha(alpha);
        child.setTranslationY(y);
        return true;
    }
}
