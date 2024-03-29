/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.changdao;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.Spacing;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import android.util.Log;

/**
 * View manager for {@link IFScrollView} components.
 *
 * <p>Note that {@link IFScrollView} and {@link IFScrollView} are exposed to JS
 * as a single ScrollView component, configured via the {@code horizontal} boolean property.
 */
@TargetApi(11)
@ReactModule(name = IFScrollViewManager.REACT_CLASS)
public class IFScrollViewManager
    extends ViewGroupManager<IFScrollView>
    implements IFScrollViewCommandHelper.ScrollCommandHandler<IFScrollView> {

  protected static final String REACT_CLASS = "RNIFScrollView";

  private static final int[] SPACING_TYPES = {
      Spacing.ALL, Spacing.LEFT, Spacing.RIGHT, Spacing.TOP, Spacing.BOTTOM,
  };

  private @Nullable FpsListener mFpsListener = null;

  public IFScrollViewManager() {
    this(null);
  }

  public IFScrollViewManager(@Nullable FpsListener fpsListener) {
    mFpsListener = fpsListener;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public IFScrollView createViewInstance(ThemedReactContext context) {
    Log.i("ReactNativeJS","[INFO]IFScrollView vreateViewInstance v0.0.1");
    return new IFScrollView(context, mFpsListener);
  }

  @ReactProp(name = "scrollEnabled", defaultBoolean = true)
  public void setScrollEnabled(IFScrollView view, boolean value) {
    view.setScrollEnabled(value);
  }

  @ReactProp(name = "innerFirst", defaultBoolean = false)
  public void setInnerFirst(IFScrollView view, boolean value){
    Log.i("ReactNativeJS","set innerFirst to "+value);
    view.setInnerFirst(value);
  }

  @ReactProp(name = "showsVerticalScrollIndicator")
  public void setShowsVerticalScrollIndicator(IFScrollView view, boolean value) {
    view.setVerticalScrollBarEnabled(value);
  }

  @ReactProp(name = "decelerationRate")
  public void setDecelerationRate(IFScrollView view, float decelerationRate) {
    view.setDecelerationRate(decelerationRate);
  }

  @ReactProp(name = "snapToInterval")
  public void setSnapToInterval(IFScrollView view, float snapToInterval) {
    // snapToInterval needs to be exposed as a float because of the Javascript interface.
    DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getScreenDisplayMetrics();
    view.setSnapInterval((int) (snapToInterval * screenDisplayMetrics.density));
  }

  @ReactProp(name = "snapToOffsets")
  public void setSnapToOffsets(IFScrollView view, @Nullable ReadableArray snapToOffsets) {
    DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getScreenDisplayMetrics();
    List<Integer> offsets = new ArrayList<Integer>();
    for (int i = 0; i < snapToOffsets.size(); i++) {
      offsets.add((int) (snapToOffsets.getDouble(i) * screenDisplayMetrics.density));
    }
    view.setSnapOffsets(offsets);
  }

  @ReactProp(name = ReactClippingViewGroupHelper.PROP_REMOVE_CLIPPED_SUBVIEWS)
  public void setRemoveClippedSubviews(IFScrollView view, boolean removeClippedSubviews) {
    view.setRemoveClippedSubviews(removeClippedSubviews);
  }

  /**
   * Computing momentum events is potentially expensive since we post a runnable on the UI thread
   * to see when it is done.  We only do that if {@param sendMomentumEvents} is set to true.  This
   * is handled automatically in js by checking if there is a listener on the momentum events.
   *
   * @param view
   * @param sendMomentumEvents
   */
  @ReactProp(name = "sendMomentumEvents")
  public void setSendMomentumEvents(IFScrollView view, boolean sendMomentumEvents) {
    view.setSendMomentumEvents(sendMomentumEvents);
  }

  /**
   * Tag used for logging scroll performance on this scroll view. Will force momentum events to be
   * turned on (see setSendMomentumEvents).
   *
   * @param view
   * @param scrollPerfTag
   */
  @ReactProp(name = "scrollPerfTag")
  public void setScrollPerfTag(IFScrollView view, @Nullable String scrollPerfTag) {
    view.setScrollPerfTag(scrollPerfTag);
  }

  @ReactProp(name = "pagingEnabled")
  public void setPagingEnabled(IFScrollView view, boolean pagingEnabled) {
    view.setPagingEnabled(pagingEnabled);
  }

  /**
   * When set, fills the rest of the scrollview with a color to avoid setting a background and
   * creating unnecessary overdraw.
   * @param view
   * @param color
   */
  @ReactProp(name = "endFillColor", defaultInt = Color.TRANSPARENT, customType = "Color")
  public void setBottomFillColor(IFScrollView view, int color) {
    view.setEndFillColor(color);
  }

  /**
   * Controls overScroll behaviour
   */
  @ReactProp(name = "overScrollMode")
  public void setOverScrollMode(IFScrollView view, String value) {
    view.setOverScrollMode(IFScrollViewHelper.parseOverScrollMode(value));
  }

  @ReactProp(name = "nestedScrollEnabled")
  public void setNestedScrollEnabled(IFScrollView view, boolean value) {
    ViewCompat.setNestedScrollingEnabled(view, value);
  }

  @Override
  public @Nullable Map<String, Integer> getCommandsMap() {
    return IFScrollViewCommandHelper.getCommandsMap();
  }

  @Override
  public void receiveCommand(
      IFScrollView scrollView,
      int commandId,
      @Nullable ReadableArray args) {
    IFScrollViewCommandHelper.receiveCommand(this, scrollView, commandId, args);
  }

  @Override
  public void flashScrollIndicators(IFScrollView scrollView) {
    scrollView.flashScrollIndicators();
  }

  @Override
  public void scrollTo(
      IFScrollView scrollView, IFScrollViewCommandHelper.ScrollToCommandData data) {
    if (data.mAnimated) {
      scrollView.smoothScrollTo(data.mDestX, data.mDestY);
    } else {
      scrollView.scrollTo(data.mDestX, data.mDestY);
    }
  }
  @ReactPropGroup(names = {
      ViewProps.BORDER_RADIUS,
      ViewProps.BORDER_TOP_LEFT_RADIUS,
      ViewProps.BORDER_TOP_RIGHT_RADIUS,
      ViewProps.BORDER_BOTTOM_RIGHT_RADIUS,
      ViewProps.BORDER_BOTTOM_LEFT_RADIUS
  }, defaultFloat = YogaConstants.UNDEFINED)
  public void setBorderRadius(IFScrollView view, int index, float borderRadius) {
    if (!YogaConstants.isUndefined(borderRadius)) {
      borderRadius = PixelUtil.toPixelFromDIP(borderRadius);
    }

    if (index == 0) {
      view.setBorderRadius(borderRadius);
    } else {
      view.setBorderRadius(borderRadius, index - 1);
    }
  }

  @ReactProp(name = "borderStyle")
  public void setBorderStyle(IFScrollView view, @Nullable String borderStyle) {
    view.setBorderStyle(borderStyle);
  }

  @ReactPropGroup(names = {
      ViewProps.BORDER_WIDTH,
      ViewProps.BORDER_LEFT_WIDTH,
      ViewProps.BORDER_RIGHT_WIDTH,
      ViewProps.BORDER_TOP_WIDTH,
      ViewProps.BORDER_BOTTOM_WIDTH,
  }, defaultFloat = YogaConstants.UNDEFINED)
  public void setBorderWidth(IFScrollView view, int index, float width) {
    if (!YogaConstants.isUndefined(width)) {
      width = PixelUtil.toPixelFromDIP(width);
    }
    view.setBorderWidth(SPACING_TYPES[index], width);
  }

  @ReactPropGroup(names = {
      "borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"
  }, customType = "Color")
  public void setBorderColor(IFScrollView view, int index, Integer color) {
    float rgbComponent =
        color == null ? YogaConstants.UNDEFINED : (float) (color & 0x00FFFFFF);
    float alphaComponent = color == null ? YogaConstants.UNDEFINED : (float) (color >>> 24);
    view.setBorderColor(SPACING_TYPES[index], rgbComponent, alphaComponent);
  }

  @Override
  public void scrollToEnd(
      IFScrollView scrollView,
      IFScrollViewCommandHelper.ScrollToEndCommandData data) {
    // ScrollView always has one child - the scrollable area
    int bottom =
      scrollView.getChildAt(0).getHeight() + scrollView.getPaddingBottom();
    if (data.mAnimated) {
      scrollView.smoothScrollTo(scrollView.getScrollX(), bottom);
    } else {
      scrollView.scrollTo(scrollView.getScrollX(), bottom);
    }
  }

  @Override
  public @Nullable Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return createExportedCustomDirectEventTypeConstants();
  }

  public static Map<String, Object> createExportedCustomDirectEventTypeConstants() {
    return MapBuilder.<String, Object>builder()
        .put(ScrollEventType.SCROLL.getJSEventName(), MapBuilder.of("registrationName", "onScroll"))
        .put(ScrollEventType.BEGIN_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollBeginDrag"))
        .put(ScrollEventType.END_DRAG.getJSEventName(), MapBuilder.of("registrationName", "onScrollEndDrag"))
        .put(ScrollEventType.MOMENTUM_BEGIN.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollBegin"))
        .put(ScrollEventType.MOMENTUM_END.getJSEventName(), MapBuilder.of("registrationName", "onMomentumScrollEnd"))
        .build();
  }
}
