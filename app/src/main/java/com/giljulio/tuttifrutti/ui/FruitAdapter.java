package com.giljulio.tuttifrutti.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giljulio.tuttifrutti.R;
import com.giljulio.tuttifrutti.model.Fruit;
import com.giljulio.tuttifrutti.ui.transformation.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Gil on 07/08/15.
 */
class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    ArrayList<Fruit> mFruits = new ArrayList<>();

    Activity mActivity;

    public FruitAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_fruit, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Fruit fruit = mFruits.get(position);
        holder.title.setText(fruit.name);

        if(fruit.media != null) {
            Picasso.with(mActivity)
                .load(fruit.media.getUrl())
                .transform(PaletteTransformation.instance())
                .into(holder.thumbnail, new PaletteTransformation.PaletteCallback(holder.thumbnail) {
                    @Override
                    public void onSuccess(Palette palette) {
                        int color = palette.getLightVibrantColor(
                                mActivity.getResources().getColor(R.color.light_gray));
                        holder.itemView.setBackgroundColor(color);
                        fruit.color = color;
                    }

                    @Override
                    public void onError() {

                    }
                });
            // Cache the full image in the cache
            Picasso.with(mActivity).load(fruit.media.getUrl()).fetch();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Attempt to load the image into the memory cache first sure the image is in loaded in the cache before animation
                Picasso.with(mActivity).load(fruit.media.getUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        startActivity();
                    }

                    @Override
                    public void onError() {
                        // Maybe slow internet still attempt to show the detail screen
                        startActivity();
                    }

                    void startActivity(){
                        holder.thumbnail.setTransitionName(mActivity.getString(R.string.transition_fruit_thumbnail));
                        DetailActivity.startActivity(mActivity, fruit, holder.thumbnail);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.fruit_thumbnail)
        ImageView thumbnail;

        @InjectView(R.id.fruit_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
