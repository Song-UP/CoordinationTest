package com.song.songup.coordinationtest.behavior.recycles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/7 15:24
 * 修改备注：
 */
public class SampleHeaderBehavior extends CoordinatorLayout.Behavior<TextView> {
    private String TAG = SampleTitleBehavior.class.getSimpleName();
    //界面整体向上滑动，打到列表滑动临界点
    private boolean upReach;//下滑，临界点
    private boolean downReach;//下滑临界点
    private int lastPositon = -1;

    public SampleHeaderBehavior() {
    }

    public SampleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //是否拦截（viewGroup才会有Intercept）
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, TextView child, MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downReach=false;
                upReach=false;
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    //用来监听Recyclyview(依赖者)的滑动事件
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     *用来监听滑动事件，实现整体滑动和列表单独滑动（header是否完全隐藏是滑动的临界点）
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull TextView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (!(target instanceof RecyclerView))
            return;
        RecyclerView recyclerView = (RecyclerView)target;
        //列表的第一个可见item位置
        int pos = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//        if (pos == 0 && pos < lastPositon) {//向下滑动
//            downReach = true;
//        }
        Log.d(TAG, "lastPosition="+lastPositon);
        //滑动的实现原理：
        // 1.向上滑动   textView和Recycleview都向上移动translate,并且Coordination消费事件consumed[1] =dy，
        // 直到textview不可见了,此时Recycleview接管，
        //2.向下滑动   开始Recycleview接管事件，直到Recycleview第一列可见，再往下走，就开始
        if (canScroll(child, dy) && pos ==0) {
            float finalY = child.getTranslationY() - dy;
            if (finalY < -child.getHeight()) {
                finalY = -child.getHeight();
                upReach = true;
            } else if (finalY > 0) {
                finalY = 0;
            }
            Log.d(TAG,"dy:"+dy+"finalY:"+finalY);
            child.setTranslationY(finalY);
            //让coordinatorLayout消费滑动事件(只是接管，并没有移动coorditation)
            consumed[1] =dy;
        }
        lastPositon = pos;
    }


    private boolean canScroll(View child, float scrollY){
        //向下滑动
        if (scrollY>0 && child.getTranslationY() == -child.getHeight()){
            return false;
        }
//        if (downReach){ //向上滑动
//            return false;
//        }
        return true;
    }
}
