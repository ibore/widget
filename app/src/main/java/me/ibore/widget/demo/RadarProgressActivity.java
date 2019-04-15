package me.ibore.widget.demo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import me.ibore.widget.RadarProgressView;

public class RadarProgressActivity extends AppCompatActivity {

    private RadarProgressView radarProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radarprogress);


        radarProgressView = findViewById(R.id.radar);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radarProgressView.start();
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radarProgressView.stop();
            }
        });
    }
}
