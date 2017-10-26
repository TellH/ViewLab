package com.tellh.viewlab.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tellh.viewlab.R;

import static android.transition.TransitionSet.ORDERING_TOGETHER;
import static com.tellh.viewlab.transition.Constant.IMAGE_URLS;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setupWindowEnterTransition();
        setupWindowReturnTransition();
        Intent intent = getIntent();
        final ImageView backgroundImage = (ImageView) findViewById(R.id.details_background_image);
        int position = intent.getIntExtra(Constant.EXTRA_STRING_IMAGE_POSITION, 0);
        // 与上一个界面的共享元素关联
        backgroundImage.setTransitionName(IMAGE_URLS[position]);
        Picasso.with(DetailActivity.this).load(IMAGE_URLS[position])
                .into(backgroundImage);
    }

    private void setupWindowReturnTransition() {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.details_text_container);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        slide.setDuration(500);
        Fade fade = new Fade();
        fade.addTarget(R.id.details_text_container);
        TransitionSet set = new TransitionSet();
        set.addTransition(slide);
        set.addTransition(fade);
        set.setOrdering(ORDERING_TOGETHER);
        getWindow().setReturnTransition(set);
    }

    private void setupWindowEnterTransition() {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.details_text_container);
        slide.setDuration(500);
        slide.setStartDelay(500);
        getWindow().setEnterTransition(slide);
    }
}
