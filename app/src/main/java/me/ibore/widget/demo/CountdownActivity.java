package me.ibore.widget.demo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.ibore.widget.CountdownView;

public class CountdownActivity extends AppCompatActivity {

    private CountdownView countdown_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdown_view = findViewById(R.id.countdown_view1);
        countdown_view.start(1000);
    }
}
