package me.ibore.widget.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import me.ibore.widget.text.html.HtmlImageLoader;
import me.ibore.widget.text.html.HtmlText;
import me.ibore.widget.text.html.OnTagClickListener;

public class HtmlTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmltext);
        TextView textView = findViewById(R.id.textView);
        HtmlText.from("<h2>Hello world</h2>\n" +
                "<p><font size='6' color='#FF0000'>Font size</font></p>\n" +
                "<ul>\n" +
                "    <li><a href=\"http://www.jianshu.com/users/3231579893ac\">Blog</a></li>\n" +
                "    <li><a href=\"https://github.com/wangchenyan\">Github</a>, welcome to star or fork,\n" +
                "        if you have issues, please tell me.\n" +
                "    </li>\n" +
                "</ul>\n" +
                "<br/>\n" +
                "<ol>\n" +
                "    <li>first</li>\n" +
                "    <li>second\n" +
                "        <ol>\n" +
                "            <li>second - first\n" +
                "                <br/>\n" +
                "                newline\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "</ol>\n" +
                "<br/>\n" +
                "<img width=\"200\" height=\"200\"\n" +
                "     src=\"http://bp.googleblog.cn/ggpt/Su3f9QSJx6CqXkjEiOvDzSAAML6RNq9YD6kSCIPov5eudHyou61mN2trSJfydldf067uImrYOPmyFBw7DDlvNSa65vCMSqJ7LLdfcDSgdteYZjE4YQo23vaNooXyhh7xcAkCGCmJ\">\n" +
                "<br/>\n" +
                "<p>\n" +
                "    With billions of Android devices around the world, Android has surpassed our wildest\n" +
                "    expectations. Today at Google I/O, we showcased a number of ways we’re pushing\n" +
                "    Android forward, with the\n" +
                "    <a href=\"https://developer.android.com/preview/index.html\">O Release</a>, new\n" +
                "    tools for developers to help create more performant apps, and an early preview of a\n" +
                "    project we call Android Go -- a new experience that we’re building for entry-level\n" +
                "    devices.\n" +
                "</p>")
                .setImageLoader(new HtmlImageLoader() {
                    @Override
                    public void loadImage(String url, final Callback callback) {
                        Glide.with(getApplicationContext())
                                .load(url)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        callback.onLoadComplete(resource);
                                    }

                                    @Override
                                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                        callback.onLoadFailed();
                                    }
                                });
                    }

                    @Override
                    public Drawable getDefaultDrawable() {
                        return null;
                    }

                    @Override
                    public Drawable getErrorDrawable() {
                        return null;
                    }

                    @Override
                    public int getMaxWidth() {
                        return 0;
                    }

                    @Override
                    public boolean fitWidth() {
                        return false;
                    }
                })
                .setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onImageClick(Context context, List<String> imageUrlList, int position) {
                        Toast.makeText(context, "image click, position: " + position + ", url: " + imageUrlList.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLinkClick(Context context, String url) {
                        Toast.makeText(context, "url click: " + url, Toast.LENGTH_SHORT).show();
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .into(textView);

    }
}
