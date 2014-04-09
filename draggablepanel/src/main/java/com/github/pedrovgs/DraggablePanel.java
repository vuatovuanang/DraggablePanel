/*
 * Copyright (C) 2014 Pedro Vicente Gómez Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pedrovgs;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Pedro Vicente Gómez Sánchez.
 */
public class DraggablePanel extends FrameLayout {

    private static final float DEFAULT_TOP_FRAGMENT_HEIGHT = 200;
    private static final float DEFAULT_SCALE_FACTOR = 2;
    private static final float DEFAULT_TOP_FRAGMENT_MARGIN = 0;
    private static final boolean DEFAULT_ENABLE_HORIZONTAL_ALPHA_EFFECT = true;

    private DraggableView draggableView;
    private Fragment topFragment;
    private Fragment bottomFragment;
    private FragmentManager fragmentManager;
    private float topFragmentHeight;
    private float topFragmentMarginRight;
    private float xScaleFactor;
    private float yScaleFactor;
    private float topFragmentMarginBottom;
    private boolean enableHorizontalAlphaEffect;

    private DraggableListener draggableListener;

    public DraggablePanel(Context context) {
        super(context);
    }

    public DraggablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(attrs);
    }


    public DraggablePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeAttrs(attrs);
    }

    public void setTopViewHeight(float topFragmentHeight) {
        this.topFragmentHeight = topFragmentHeight;
    }

    public void setTopFragment(Fragment topFragment) {
        this.topFragment = topFragment;
    }

    public void setBottomFragment(Fragment bottomFragment) {
        this.bottomFragment = bottomFragment;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setXScaleFactor(float xScaleFactor) {
        this.xScaleFactor = xScaleFactor;
    }

    public void setyScaleFactor(float yScaleFactor) {
        this.yScaleFactor = yScaleFactor;
    }

    public void setTopFragmentMarginRight(float topFragmentMarginRight) {
        this.topFragmentMarginRight = topFragmentMarginRight;
    }

    public void setTopFragmentMarginBottom(float topFragmentMarginBottom) {
        this.topFragmentMarginBottom = topFragmentMarginBottom;
    }

    public void setDraggableListener(DraggableListener draggableListener) {
        this.draggableListener = draggableListener;
    }

    public void setEnableHorizontalAlphaEffect(boolean enableHorizontalAlphaEffect) {
        this.enableHorizontalAlphaEffect = enableHorizontalAlphaEffect;
    }

    public void closeToLeft() {
        draggableView.closeToLeft();
    }

    public void closeToRight() {
        draggableView.closeToRight();
    }

    public void maximize() {
        draggableView.maximize();
    }

    public void minimize() {
        draggableView.minimize();
    }

    public void initializeView() {
        checkFragmentConsistency();
        checkSupportFragmentmanagerConsistency();

        inflate(getContext(), R.layout.draggable_panel, this);
        draggableView = (DraggableView) findViewById(R.id.draggableView);
        draggableView.setTopViewHeight(topFragmentHeight);
        draggableView.setFragmentManager(fragmentManager);
        draggableView.attachTopFragment(topFragment);
        draggableView.setXTopViewScaleFactor(xScaleFactor);
        draggableView.setYTopViewScaleFactor(yScaleFactor);
        draggableView.setTopViewMarginRight(topFragmentMarginRight);
        draggableView.setTopViewMarginBottom(topFragmentMarginBottom);
        draggableView.attachBottomFragment(bottomFragment);
        draggableView.setDraggableListener(draggableListener);
        draggableView.setHorizontalAlphaEffectEnabled(enableHorizontalAlphaEffect);
    }

    public boolean isMaximized() {
        return draggableView.isMaximized();
    }

    public boolean isMinimized() {
        return draggableView.isMinimized();
    }

    public boolean isClosedAtRight() {
        return draggableView.isClosedAtRight();
    }

    public boolean isClosedAtLeft() {
        return draggableView.isClosedAtLeft();
    }

    private void initializeAttrs(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.draggable_panel);
        this.topFragmentHeight = attributes.getDimension(R.styleable.draggable_panel_top_fragment_height, DEFAULT_TOP_FRAGMENT_HEIGHT);
        this.xScaleFactor = attributes.getFloat(R.styleable.draggable_panel_x_scale_factor, DEFAULT_SCALE_FACTOR);
        this.yScaleFactor = attributes.getFloat(R.styleable.draggable_panel_y_scale_factor, DEFAULT_SCALE_FACTOR);
        this.topFragmentMarginRight = attributes.getDimension(R.styleable.draggable_panel_top_fragment_margin_right, DEFAULT_TOP_FRAGMENT_MARGIN);
        this.topFragmentMarginBottom = attributes.getDimension(R.styleable.draggable_panel_top_fragment_margin_bottom, DEFAULT_TOP_FRAGMENT_MARGIN);
        this.enableHorizontalAlphaEffect = attributes.getBoolean(R.styleable.draggable_panel_enable_horizontal_alpha_effect, DEFAULT_ENABLE_HORIZONTAL_ALPHA_EFFECT);
        attributes.recycle();
    }

    private void checkSupportFragmentmanagerConsistency() {
        if (fragmentManager == null) {
            throw new IllegalStateException("You have to set the support FragmentManager before initialize DraggablePanel");
        }
    }

    private void checkFragmentConsistency() {
        if (topFragment == null || bottomFragment == null) {
            throw new IllegalStateException("You have to set top and bottom fragment before initialize DraggablePanel");
        }
    }

}
