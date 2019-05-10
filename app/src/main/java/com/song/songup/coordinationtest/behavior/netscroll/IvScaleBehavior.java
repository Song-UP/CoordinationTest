package com.song.songup.coordinationtest.behavior.netscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.song.songup.coordinationtest.util.ConvertUtils;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/9 19:42
 * 修改备注：
 */
public class IvScaleBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private View linearLayout;

    private String Tag = "IvScaleBehavior";
    private int minHeight = ConvertUtils.dp2px(30);
    private int maxHeight = ConvertUtils.dp2px(100);

    private int dispatity = maxHeight - minHeight;
    private int barHeight = ConvertUtils.dp2px(50);

    long moveDistance = 0;

    public IvScaleBehavior() {
    }

    public IvScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    //用来监听NetScroll上下滑动(只监听垂直方向滑动)
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) !=0;
    }

//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//
//        if (linearLayout == null){
//            linearLayout = target.findViewById(R.id.linear);
//        }
//
//
//        if (!(target instanceof NestedScrollView))
//            return;
//
//        int scaleY;
//        int finalHeiht = 0;
//
//
//        int linearPos = linearLayout.getTop();
//        if (-barHeight <= linearPos && linearPos <= barHeight) {
//            moveDistance += linearPos;
//            scaleY = ((int) moveDistance)/barHeight;
//            finalHeiht = minHeight+dispatity*scaleY;
//
//            Log.d(Tag,"-------------------------------------------");
//            Log.d(Tag,"linearPos:"+linearPos+"\tbarHeight:"+barHeight);
//            Log.d(Tag,"moveDistance:"+moveDistance+"\tscaleY:"+scaleY);
//            Log.d(Tag,"maxHeight:"+maxHeight+"\tfinalHeiht:"+finalHeiht);
//            Log.d(Tag,"-------------------------------------------");
//
//
//        }
//
//        if (finalHeiht !=0){
//            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//            layoutParams.height = finalHeiht;
//            child.setLayoutParams(layoutParams);
//        }
//    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        int follow = target.getScrollY();

        float scaleY = ((float) follow)/barHeight;
        int finalY = (int) (minHeight + dispatity*scaleY);
        if (finalY > maxHeight){
            finalY = maxHeight;
        }
        if (finalY<minHeight){
            finalY = minHeight;
        }
        if (target.getHeight() == finalY)
            return;
        Log.d(Tag,"scaleY:"+scaleY+"follow:"+follow+"\tminHeight:"+minHeight+"\tfinalY:"+finalY);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            layoutParams.height = finalY;
            child.setLayoutParams(layoutParams);

    }
}
