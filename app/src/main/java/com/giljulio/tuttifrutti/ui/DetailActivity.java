package com.giljulio.tuttifrutti.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.giljulio.tuttifrutti.R;
import com.giljulio.tuttifrutti.model.Fruit;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_FRUIT = "key_fruit";

    public static void startActivity(Activity activity, Fruit fruit, ImageView sharedImageView){
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(KEY_FRUIT, fruit);

        //Don't show shared element transition on pre lollipop
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String sharedElementName = activity.getString(R.string.transition_fruit_thumbnail);
            activity.startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedImageView, sharedElementName).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Fruit fruit = (Fruit) getIntent().getSerializableExtra(KEY_FRUIT);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, DetailActivityFragment.newInstance(fruit));
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
