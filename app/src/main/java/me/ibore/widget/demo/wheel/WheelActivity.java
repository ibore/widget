package me.ibore.widget.demo.wheel;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ibore.widget.WheelView;
import me.ibore.widget.demo.R;

public class WheelActivity extends AppCompatActivity {

    private WheelView<WheelView.IWheelEntity> wheel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        wheel = findViewById(R.id.wheel);
        List<WheelView.IWheelEntity> iWheelEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            iWheelEntityList.add(new WheelView.IWheelEntity() {
                @Override
                public String getWheelText() {
                    return String.valueOf(finalI + 1);
                }
            });
        }

        wheel.setData(iWheelEntityList);

    }
}
