package com.tellh.viewlab.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tellh.viewlab.R;

import static com.tellh.viewlab.transition.Constant.IMAGE_URLS;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new ListAdapter());
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imge, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return IMAGE_URLS.length;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImage;
        private int mPosition;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImage = (ImageView) itemView.findViewById(R.id.iv);
        }

        void bind(int position) {
            Picasso.with(ListActivity.this).load(IMAGE_URLS[position])
                    .into(mImage);
            mPosition = position;
            mImage.setTransitionName(IMAGE_URLS[position]);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra(Constant.EXTRA_STRING_IMAGE_POSITION, mPosition);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ListActivity.this,
                    mImage, mImage.getTransitionName()).toBundle());
        }
    }


}
