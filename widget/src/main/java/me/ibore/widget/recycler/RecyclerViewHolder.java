/*
package me.ibore.widget.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

*/
/**
 * description:
 * author: Ibore Xie
 * date: 2017-11-26 00:22
 * website: ibore.me
 *//*


public class RecyclerViewHolder<VH extends RecyclerViewHolder> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    private RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public static RecyclerViewHolder<RecyclerViewHolder> create(ViewGroup parent, @LayoutRes int layoutId) {
        return new RecyclerViewHolder<>(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public static RecyclerViewHolder<RecyclerViewHolder> create(View itemView) {
        return new RecyclerViewHolder<>(itemView);
    }

    public View getItemView() {
        return itemView;
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public <T extends View> T getView(@IdRes int id) {
        View view = mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return (T) view;
    }

    public TextView getTextView(@IdRes int id) {
        TextView view = (TextView) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public EditText getEditText(@IdRes int id) {
        EditText view = (EditText) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public ImageButton getImageButton(@IdRes int id) {
        ImageButton view = (ImageButton) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }


    public ProgressBar getProgressBar(@IdRes int id) {
        ProgressBar view = (ProgressBar) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public ImageView getImageView(@IdRes int id) {
        ImageView view = (ImageView) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }


    public Button getButton(@IdRes int id) {
        Button view = (Button) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public CheckBox getCheckBox(@IdRes int id) {
        CheckBox view = (CheckBox) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return view;
    }

    public RadioButton getRadioButton(@IdRes int id) {
        RadioButton view = (RadioButton) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return view;
    }

    public RadioGroup getRadioGroup(@IdRes int id) {
        RadioGroup view = (RadioGroup) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return view;
    }

    public Spinner getSpinner(@IdRes int id) {
        Spinner view = (Spinner) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return view;
    }

    public RatingBar getRatingBar(@IdRes int id) {
        RatingBar view = (RatingBar) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return view;
    }

    public LinearLayout getLinearLayout(@IdRes int id) {
        LinearLayout view = (LinearLayout) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public RelativeLayout getRelativeLayout(@IdRes int id) {
        RelativeLayout view = (RelativeLayout) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }

    public RecyclerView getRecyclerView(@IdRes int id) {
        RecyclerView view = (RecyclerView) mViews.get(id);
        if (null == view) {
            view = itemView.findViewById(id);
            mViews.append(id, view);
        }
        return  view;
    }


    public VH setOnClickListener(View.OnClickListener onClickListener) {
        if (null != onClickListener) {
            getItemView().setOnClickListener(onClickListener);
        }
        return (VH) this;
    }
    public VH setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        if (null != onClickListener) {
            getView(id).setOnClickListener(onClickListener);
        }
        return (VH) this;
    }

    public VH setText(@IdRes int id, @StringRes int res) {
        getTextView(id).setText(res);
        return (VH) this;
    }

    public VH setText(@IdRes int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
        return (VH) this;
    }

    public VH setVisibility(@IdRes int id, int visibility) {
        getView(id).setVisibility(visibility);
        return (VH) this;
    }

    public VH setBackgroundResource(@IdRes int id, int resid) {
        getView(id).setBackgroundResource(resid);
        return (VH) this;
    }


}
*/
