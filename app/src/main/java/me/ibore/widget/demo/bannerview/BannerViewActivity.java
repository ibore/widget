package me.ibore.widget.demo.bannerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.ibore.widget.BannerView;
import me.ibore.widget.demo.R;

public class BannerViewActivity extends AppCompatActivity implements View.OnClickListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannerview);

        BannerView bannerView1 = findViewById(R.id.banner1);
        BannerView bannerView2 = findViewById(R.id.banner2);


        final List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        bannerView1.setImageLoader(new GlideImageLoader());
        bannerView1.setViewUrls(urls);




    }


    @Override
    public void onClick(View v) {

    }

    public class GlideImageLoader implements BannerView.ImageLoader {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

}

