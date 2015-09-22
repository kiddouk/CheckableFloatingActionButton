package io.errorlab.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.util.Log;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;
import android.os.Parcelable.Creator;
import android.view.View;

public class CheckableFloatingActionButton extends FloatingActionButton implements Checkable {

	private static final int[] CheckedStateSet = {
		android.R.attr.state_checked,
	};
	
	private boolean checked = false;

	public CheckableFloatingActionButton(Context ctx) {
		this(ctx, null);
	}

	public CheckableFloatingActionButton(Context ctx, AttributeSet attrs) {
		this(ctx, attrs, 0);
	}

	public CheckableFloatingActionButton(Context ctx, AttributeSet attrs, int defStyle) {
		super(ctx, attrs, defStyle);
	}

	@Override
	public int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			Log.e("TOTO", "Is checked ;)");
			mergeDrawableStates(drawableState, CheckedStateSet);
		}
		return drawableState;
	}
	
	@Override
	public void setChecked(boolean checked) {
		if (checked == this.checked) {
			return;
		}
		this.checked = checked;
	}

	@Override
	public boolean isChecked() { return this.checked; }

	@Override
	public void toggle() { setChecked(!this.checked); }

	@Override
	public boolean performClick() {
		Log.e("TOTO", "TOGGLE");
		toggle();
		return super.performClick();
	}

	@Override
    protected Parcelable onSaveInstanceState() {
        SavedState result = new SavedState(super.onSaveInstanceState());
        result.checked = checked;
        return result;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setChecked(ss.checked);
    }

    protected static class SavedState extends BaseSavedState {
        protected boolean checked;

        protected SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(checked ? 1 : 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        private SavedState(Parcel in) {
            super(in);
            checked = in.readInt() == 1;
        }
    }
}
	
	
	
	
