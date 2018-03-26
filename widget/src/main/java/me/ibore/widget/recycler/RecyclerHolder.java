package me.ibore.widget.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
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

/**
 * description:
 * author: Ibore Xie
 * date: 2017-11-26 00:22
 * website: ibore.me
 */

public class RecyclerHolder<VH extends RecyclerHolder> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    private RecyclerHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public static RecyclerHolder create(ViewGroup parent, @LayoutRes int layoutId) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, null));
    }

    public static RecyclerHolder create(View itemView) {
        return new RecyclerHolder(itemView);
    }

    public View getItemView() {
        return itemView;
    }

    public Context getContext() {
        return itemView.getContext();
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
            view = (TextView) itemView.findViewById(id);
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


}
