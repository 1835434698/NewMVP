package com.tangzy.tzymvp.view.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.tangzy.tzymvp.R;



/**
 * Created by RickBerg on 2018/5/9 0009.
 */

public final class SnackbarCus extends BaseTransienTopBarCus<SnackbarCus> {

    /**
     * Show the Snackbar indefinitely. This means that the Snackbar will be displayed from the time
     * that is {@link #show() shown} until either it is dismissed, or another Snackbar is shown.
     *
     * @see #setDuration
     */
    public static final int LENGTH_INDEFINITE = BaseTransienTopBarCus.LENGTH_INDEFINITE;

    /**
     * Show the Snackbar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = BaseTransienTopBarCus.LENGTH_SHORT;

    /**
     * Show the Snackbar for a long period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = BaseTransienTopBarCus.LENGTH_LONG;

    /**
     * Callback class for {@link SnackbarCus} instances.
     * <p>
     * Note: this class is here to provide backwards-compatible way for apps written before
     * the existence of the base {@link BaseTransienTopBarCus} class.
     *
     * @see BaseTransienTopBarCus#addCallback(BaseCallback)
     */
    public static class Callback extends BaseCallback<SnackbarCus> {
        /**
         * Indicates that the Snackbar was dismissed via a swipe.
         */
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        /**
         * Indicates that the Snackbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        /**
         * Indicates that the Snackbar was dismissed via a timeout.
         */
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        /**
         * Indicates that the Snackbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        /**
         * Indicates that the Snackbar was dismissed from a new Snackbar being shown.
         */
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(SnackbarCus sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(SnackbarCus transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable
    private BaseCallback<SnackbarCus> mCallback;

    private SnackbarCus(ViewGroup parent, View content, ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static SnackbarCus make(@NonNull  Context context, @NonNull CharSequence text,
                                   @Duration int duration) {
        final ViewGroup parent = vreateView(context);

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final SnackbarContentLayoutCus content =
                (SnackbarContentLayoutCus) inflater.inflate(
                        R.layout.design_layout_snackbar_include_, parent, false);
        final SnackbarCus snackbarCus = new SnackbarCus(parent, content, content);
        snackbarCus.setText(text);
        snackbarCus.setDuration(duration);
        return snackbarCus;
    }

    @NonNull
    public static SnackbarCus make(@NonNull Activity activity, @NonNull CharSequence text,
                                   @Duration int duration) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content).getRootView();
        return make(viewGroup, text, duration);
    }
    @NonNull
    public static SnackbarCus make(@NonNull View view, @NonNull CharSequence text,
                                   @Duration int duration) {

        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. "
                    + "Please provide a valid view.");
        }

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final SnackbarContentLayoutCus content =
                (SnackbarContentLayoutCus) inflater.inflate(
                        R.layout.design_layout_snackbar_include_, parent, false);
        final SnackbarCus snackbarCus = new SnackbarCus(parent, content, content);
        snackbarCus.setText(text);
        snackbarCus.setTitle("测试");
        snackbarCus.setDuration(duration);
        return snackbarCus;
    }

    @SuppressLint("NewApi")
    private static ViewGroup vreateView(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            FloatWindowView mView = new FloatWindowView(context);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
//        int gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
////        mParams.x = 0;
////        mParams.y = 0;
////        mParams.verticalMargin = 1;
////        mParams.horizontalMargin = 1;
////        mParams.packageName = mView.getContext().getOpPackageName();
////        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
////            mParams.horizontalWeight = 1.0f;
////        }
////        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
////            mParams.verticalWeight = 1.0f;
////        }
////                    mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        mParams.format = PixelFormat.RGBA_8888;
//        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        mParams.gravity = gravity;


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
//                    https://blog.csdn.net/mai763727999/article/details/78983375/
                    mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
                mParams.format = PixelFormat.RGBA_8888;
                mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;

            windowManager.addView(mView, mParams);
            return mView;
    }

    @NonNull
    public static SnackbarCus make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
//            if (view instanceof CoordinatorLayout) {
//                // We've found a CoordinatorLayout, use it
//                return (ViewGroup) view;
//            } else
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    /**
     * Update the text in this {@link SnackbarCus}.
     *
     * @param message The new text for this {@link BaseTransienTopBarCus}.
     */
    @NonNull
    public SnackbarCus setText(@NonNull CharSequence message) {
        final SnackbarContentLayoutCus contentLayout = (SnackbarContentLayoutCus) mView.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setText(message);
        return this;
    }
    /**
     * Update the text in this {@link SnackbarCus}.
     *
     * @param title The new text for this {@link BaseTransienTopBarCus}.
     */
    @NonNull
    public SnackbarCus setTitle(@NonNull CharSequence title) {
        final SnackbarContentLayoutCus contentLayout = (SnackbarContentLayoutCus) mView.getChildAt(0);
        final TextView tv = contentLayout.getTitleView();
        tv.setText(title);
        return this;
    }

    /**
     * Update the text in this {@link SnackbarCus}.
     *
     * @param resId The new text for this {@link BaseTransienTopBarCus}.
     */
    @NonNull
    public SnackbarCus setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    /**
     * Set the action to be displayed in this {@link BaseTransienTopBarCus}.
     *
     * @param resId    String resource to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public SnackbarCus setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    /**
     * Set the action to be displayed in this {@link BaseTransienTopBarCus}.
     *
     * @param text     Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public SnackbarCus setAction(CharSequence text, final View.OnClickListener listener) {
        final SnackbarContentLayoutCus contentLayout = (SnackbarContentLayoutCus) mView.getChildAt(0);
        final TextView tv = contentLayout.getTitleView();

        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                    // Now dismiss the Snackbar
                    dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    /**
     * Sets the text color of the action specified in
     * {@link #setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public SnackbarCus setActionTextColor(ColorStateList colors) {
        final SnackbarContentLayoutCus contentLayout = (SnackbarContentLayoutCus) mView.getChildAt(0);
        final TextView tv = contentLayout.getTitleView();
        tv.setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     * {@link #setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public SnackbarCus setActionTextColor(@ColorInt int color) {
        final SnackbarContentLayoutCus contentLayout = (SnackbarContentLayoutCus) mView.getChildAt(0);
        final TextView tv = contentLayout.getTitleView();
        tv.setTextColor(color);
        return this;
    }

    /**
     * Set a callback to be called when this the visibility of this {@link SnackbarCus}
     * changes. Note that this method is deprecated
     * and you should use {@link #addCallback(BaseCallback)} to add a callback and
     * {@link #removeCallback(BaseCallback)} to remove a registered callback.
     *
     * @param callback Callback to notify when transient bottom bar events occur.
     * @see Callback
     * @see #addCallback(BaseCallback)
     * @see #removeCallback(BaseCallback)
     * @deprecated Use {@link #addCallback(BaseCallback)}
     */
    @Deprecated
    @NonNull
    public SnackbarCus setCallback(Callback callback) {
        // The logic in this method emulates what we had before support for multiple
        // registered callbacks.
        if (mCallback != null) {
            removeCallback(mCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        // Update the deprecated field so that we can remove the passed callback the next
        // time we're called
        mCallback = callback;
        return this;
    }

    /**
     * @hide Note: this class is here to provide backwards-compatible way for apps written before
     * the existence of the base {@link BaseTransienTopBarCus} class.
     */
    public static final class SnackbarLayout extends BaseTransienTopBarCus.SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            // Work around our backwards-compatible refactoring of Snackbar and inner content
            // being inflated against snackbar's parent (instead of against the snackbar itself).
            // Every child that is width=MATCH_PARENT is remeasured again and given the full width
            // minus the paddings.
            int childCount = getChildCount();
            int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    child.measure(View.MeasureSpec.makeMeasureSpec(availableWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(),
                                    View.MeasureSpec.EXACTLY));
                }
            }
        }
    }
}
