package me.ibore.widget.demo.wheel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.ibore.widget.WheelView;
import me.ibore.widget.demo.R;

public class WheelActivity extends AppCompatActivity {

    private WheelView wheel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        wheel = findViewById(R.id.wheel);

        wheel.setMode(WheelView.Mode.MODE_2D);

    }
}
