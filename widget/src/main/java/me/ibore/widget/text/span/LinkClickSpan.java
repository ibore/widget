package me.ibore.widget.text.span;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

import me.ibore.widget.text.html.OnTagClickListener;

public class LinkClickSpan extends ClickableSpan {

    private OnTagClickListener listener;
    private Context context;
    private String url;

    public LinkClickSpan(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public void setListener(OnTagClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View widget) {
        if (listener != null) {
            listener.onLinkClick(context, url);
        }
    }
}
