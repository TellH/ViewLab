package com.tellh.viewlab.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.tellh.viewlab.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewGroup container = findViewById(R.id.container);
        FabTransform.setup(this, container);
    }

    public void dismiss(View view) {
        finishAfterTransition();// 代替finish，让Activity做完Transition动画后在退出
    }
}
