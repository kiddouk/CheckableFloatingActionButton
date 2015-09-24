package io.errorlab.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LayeredCheckableFloatingActionButton extends FrameLayout {

	private Context context;
	private CheckableFloatingActionButton fab;
	private ImageView checkedBackground;
	private int[] viewMargins = null;

	public LayeredCheckableFloatingActionButton(Context ctx) {
		this(ctx, null);
	}

	public LayeredCheckableFloatingActionButton(Context ctx, AttributeSet attrs) {
		this(ctx, attrs, 0);
	}

	public LayeredCheckableFloatingActionButton(Context ctx, AttributeSet attrs, int defStyle) {
		super(ctx, attrs, defStyle);
		context = ctx;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		ImageView uncheckedBackground = (ImageView) inflater.inflate(R.layout.background_normal_unchecked, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(uncheckedBackground, params);

		checkedBackground = (ImageView) inflater.inflate(R.layout.background_normal_checked, null);
		addView(checkedBackground, params);

		fab = new CheckableFloatingActionButton(ctx, attrs, defStyle);
		// Let's make our image transparent
		fab.setElevation(0);
		fab.setChecked(true);
		// fab.setImageResource(R.drawable.fab_switch);
		fab.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.transparent_fab));
		params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		addView(fab, params);
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
		if (fab.isChecked() != true) {
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
	// public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
	// 	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// 	fab.measure(widthMeasureSpec, heightMeasureSpec);
	// 	checkedFab.measure(widthMeasureSpec, heightMeasureSpec);
	// 	Log.e("TOTO", Integer.toString(checkedFab.getMeasuredWidth()));
	// 	setMeasuredDimension(checkedFab.getMeasuredWidth() + 96, checkedFab.getMeasuredHeight() + 96);
	// }

	// @Override
	// public void onLayout(boolean changed, int l, int t, int r, int b) {
	// 	if (viewMargins == null) {
	// 		ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) getLayoutParams();
	// 		viewMargins = new int[4];
	// 		viewMargins[0] = p.leftMargin;
	// 		viewMargins[1] = p.topMargin;
	// 		viewMargins[2] = p.rightMargin;
	// 		viewMargins[3] = p.bottomMargin;
	// 		p.setMargins(0, 0, 0, 0);
	// 	}

	// 	super.onLayout(changed, l, t, r, b);

	// 	RelativeLayout.LayoutParams m = (RelativeLayout.LayoutParams) fab.getLayoutParams();
	// 	Log.e("MARGIN", Integer.toString(m.topMargin));
	// 	if (m.leftMargin == 0) {
	// 		m.setMargins(
	// 			viewMargins[0],
	// 			viewMargins[1],
	// 			viewMargins[2],
	// 			viewMargins[3]);
	// 	}
	// 	RelativeLayout.LayoutParams n = (RelativeLayout.LayoutParams) checkedFab.getLayoutParams();
	// 	Log.e("MARGIN", Integer.toString(n.topMargin));
	// 	if (n.leftMargin == 0) {
	// 		n.setMargins(
	// 			viewMargins[0],
	// 			viewMargins[1],
	// 			viewMargins[2],
	// 			viewMargins[3]);
	// 	}
	// }

	// public static float dipToPixels(Context context, float dipValue) {
	// 	DisplayMetrics metrics = context.getResources().getDisplayMetrics();
	// 	return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
	// }

}
