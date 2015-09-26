package io.errorlab.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LayeredCheckableFloatingActionButton extends FrameLayout implements Checkable {

	static final int[] CHECKED_ENABLED_STATE_SET = {
		android.R.attr.state_checked
	};

	/**
     * Interface definition for a callback to be invoked when the checked state of this View is
     * changed.
     */
    public static interface OnCheckedChangeListener {

        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param checkableView The view whose state has changed.
         * @param isChecked     The new checked state of checkableView.
         */
        void onCheckedChanged(View checkableView, boolean isChecked);
    }
	
	private Context context;
	private final Interpolator checkedInterpolator;
	private CheckableFloatingActionButton fab;
	private OnCheckedChangeListener onCheckedChangeListener;
	private ImageView checkedBackground;
	private int[] viewMargins = null;

	private boolean checked = false;

	public LayeredCheckableFloatingActionButton(Context ctx) {
		this(ctx, null);
	}

	public LayeredCheckableFloatingActionButton(Context ctx, AttributeSet attrs) {
		this(ctx, attrs, 0);
	}

	public LayeredCheckableFloatingActionButton(Context ctx, AttributeSet attrs, int defStyle) {
		super(ctx, attrs, defStyle);
		context = ctx;

		// load the animation interpolator
		checkedInterpolator = AnimationUtils.loadInterpolator(
                context, android.R.interpolator.fast_out_slow_in);

		// View hierarchy setup
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		// ImageView uncheckedBackground = (ImageView) inflater.inflate(R.layout.background_normal_unchecked, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		// addView(uncheckedBackground, params);

		checkedBackground = (ImageView) inflater.inflate(R.layout.background_normal_checked, null);
		addView(checkedBackground, params);

		fab = new CheckableFloatingActionButton(ctx, attrs, defStyle);
		fab.setElevation(0);
		fab.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.transparent_fab));
		params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(fab, params);

		// Set Layout attributes since we are about to 
		setClipToOutline(true);
		setWillNotDraw(false);
		setClickable(true);
		setFocusable(true);
		setPressedTranslationZ(40);
		setBackgroundResource(R.drawable.layout_background_state);

	}

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_ENABLED_STATE_SET);
		}		
		return drawableState;
	}

	public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean b) {
        if (b != checked) {
            checked = b;
            refreshDrawableState();

            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, checked);
            }
        }
    }

    public void toggle() {
        setChecked(!checked);
    }
	
	private void setPressedTranslationZ(float translationZ) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        // Animate translationZ to our value when pressed or focused
        stateListAnimator.addState(CHECKED_ENABLED_STATE_SET,
                setupAnimator(ObjectAnimator.ofFloat(this, "translationZ", translationZ)));
        // Animate translationZ to 0 otherwise
        stateListAnimator.addState(EMPTY_STATE_SET,
                setupAnimator(ObjectAnimator.ofFloat(this, "translationZ", 0f)));
        setStateListAnimator(stateListAnimator);
    }

	private Animator setupAnimator(Animator animator) {
        animator.setInterpolator(checkedInterpolator);
        return animator;
    }

	@Override
	public boolean dispatchTouchEvent (MotionEvent ev) {
		// previously invisible view
		int x = (int) ev.getX();
		int y = (int) ev.getY();

		// get the final radius for the clipping circle
		int finalRadius = checkedBackground.getWidth();

		// create the animator for this view (the start radius is zero)
		Animator revealAnimator;
		// Remember that here, the FAB has already changed state, so we need
		// to invert our logic.
		if (isChecked() == true) {
			revealAnimator = ViewAnimationUtils.createCircularReveal(checkedBackground, x, y, finalRadius, 0);
			revealAnimator.addListener(new Animator.AnimatorListener() {
					public void onAnimationEnd(Animator anim) {
						checkedBackground.setVisibility(View.INVISIBLE);
					}

					public void onAnimationStart(Animator anim) { };
					public void onAnimationRepeat(Animator anim) { };
					public void onAnimationCancel(Animator anim) { };
				});
			
		} else {
			revealAnimator = ViewAnimationUtils.createCircularReveal(checkedBackground, x, y, 0, finalRadius);
			checkedBackground.setVisibility(View.VISIBLE);
		}
		
		revealAnimator.setDuration(300);

		// make the view visible and start the animation
		revealAnimator.start();

		// Do your calcluations
		return super.dispatchTouchEvent(ev);
	}
	
	// @Override
	public void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
				@Override
				public void getOutline(View view, Outline outline) {
					// Or read size directly from the view's width/height
					int size = checkedBackground.getWidth();
					outline.setOval(0, 0, size, size);
				}
			};
		setOutlineProvider(viewOutlineProvider);
	}

	 /**
     * Register a callback to be invoked when the checked state of this view changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

}
