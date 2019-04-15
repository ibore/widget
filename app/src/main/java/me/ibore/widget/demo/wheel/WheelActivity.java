package me.ibore.widget.demo.wheel;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.ibore.widget.WheelView;
import me.ibore.widget.demo.R;

public class WheelActivity extends AppCompatActivity {

    private WheelView wheel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        wheel = findViewById(R.id.wheel);


    }
}
