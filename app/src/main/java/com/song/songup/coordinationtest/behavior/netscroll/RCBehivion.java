package com.song.songup.coordinationtest.behavior.netscroll;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/9 20:30
 * 修改备注：
 */
public class RCBehivion extends CoordinatorLayout.Behavior<NestedScrollView> {
    public RCBehivion() {
    }

    public RCBehivion(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        return dependency instanceof ImageView;
    }

//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, NestedScrollView child, View dependency) {
//        float y= dependency.getHeight()+dependency.getTranslationY();
//        if(y<0){
//            y=0;
//        }
//        child.setY(y);
//        return true;
//    }



}
