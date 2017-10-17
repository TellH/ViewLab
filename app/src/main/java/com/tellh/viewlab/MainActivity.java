package com.tellh.viewlab;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
        // 基本绘制
        pageModels.add(new PageModel(R.string.title_draw_color, R.layout.practice_color));
        pageModels.add(new PageModel(R.string.title_draw_graphic, R.layout.practice_graphic));
        pageModels.add(new PageModel(R.string.title_draw_path, R.layout.practice_path));
//        pageModels.add(new PageModel(R.string.title_draw_histogram, R.layout.practice_histogram));
//        pageModels.add(new PageModel(R.string.title_draw_pie_chart, R.layout.practice_pie_chart));

        // 范围裁切
        pageModels.add(new PageModel(R.string.title_clip_rect, R.layout.practice_clip_rect));
        // 几何变换
        pageModels.add(new PageModel(R.string.title_canvas_change, R.layout.practice_canvas_change));
        // Matrix变换
        pageModels.add(new PageModel(R.string.title_canvas_Matrix, R.layout.practice_canvas_matrix));
        // Camera做三维变换
        pageModels.add(new PageModel(R.string.title_canvas_camera, R.layout.practice_canvas_camera));
        // 折叠翻转
        pageModels.add(new PageModel(R.string.title_flipboard, R.layout.practice_flipboard));

        // 属性动画
        pageModels.add(new PageModel(R.string.title_animator, R.layout.practice_animator));
        // 翻页动画
        pageModels.add(new PageModel(R.string.title_flip_animation, R.layout.practice_flip_animation));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class PageModel {
        @StringRes
        int titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(@StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
