package me.ibore.widget.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.ibore.widget.text.html.HtmlImageLoader;
import me.ibore.widget.text.html.HtmlText;
import me.ibore.widget.text.html.OnTagClickListener;

public class HtmlTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmltext);
        TextView textView = findViewById(R.id.textView);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        HtmlText
                    .from(
                    "<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
                    + "<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
                    + "</p><p><font color=\"#00bbaa\">颜色2</p><p><font color=\"#aabb00\">颜色1"
                    + "</p><p><font color=\"#00bbaa\">颜色2</p><p><font color=\"#aabb00\">颜色1"
                    + "</p><p><font color=\"#00bbaa\">颜色2</p><p><font color=\"#aabb00\">颜色1"
                    + "</p><p><font color=\"#00bbaa\">颜色2</p><p><font color=\"#aabb00\">颜色1"
                    + "</p><p><font color=\"#00bbaa\"><a href=\"tel:95583\">95583</a></p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>"
                    + "下面是网络图片</p><img src=\"http://h.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfad03ef192ba8f8c5494ee7b7f.jpg\"/></body></html>")

//                .from(
//                "<html><body><p>您所添加的证件号码已绑定其他凤凰知音账户，无法进行添加。如有疑问请拨打 <a href=\"tel:95583\"><font color=\"#0086FF\">95583</font></a> 咨询。</p></body></html>")
                .setImageLoader(new HtmlImageLoader() {
                    @Override
                    public void loadImage(String url, final Callback callback) {
                        Glide.with(getBaseContext())
                                .asDrawable()
                                .load(url)
                                .into(new CustomTarget<Drawable>() {

                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        callback.onLoadComplete(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        callback.onLoadComplete(placeholder);
                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        callback.onLoadFailed();
                                        super.onLoadFailed(errorDrawable);
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
                        return true;
                    }
                })
                .setOnTagClickListener(new OnTagClickListener() {
                    @Override
                    public void onImageClick(Context context, List<String> imageUrlList, int position) {
                        Log.d("----", "image click, position: " + position + ", url: " + imageUrlList.get(position));
                        Toast.makeText(getApplicationContext(), "image click, position: " + position + ", url: " + imageUrlList.get(position), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLinkClick(Context context, String url) {
                        Log.d("----", "url click: " + url);
                        Toast.makeText(getApplicationContext(), "url click: " + url, Toast.LENGTH_SHORT).show();
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
