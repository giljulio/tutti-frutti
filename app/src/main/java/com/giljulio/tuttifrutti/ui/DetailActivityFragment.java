package com.giljulio.tuttifrutti.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giljulio.tuttifrutti.R;
import com.giljulio.tuttifrutti.model.Article;
import com.giljulio.tuttifrutti.model.Fruit;
import com.giljulio.tuttifrutti.net.API;
import com.giljulio.tuttifrutti.utils.ColorUtils;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements Callback<Article> {

    private static final String KEY_FRUIT = "key_fruit";

    @InjectView(R.id.backdrop)
    ImageView mBackdrop;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.detail_text)
    TextView mDetailTextView;

    @InjectView(R.id.detail_price)
    TextView mDetailPriceView;

    @InjectView(R.id.detail_weight)
    TextView mDetailWeightView;

    Fruit mFruit;

    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(Fruit fruit) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_FRUIT, fruit);
        DetailActivityFragment fragment = new DetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mFruit = (Fruit) bundle.getSerializable(KEY_FRUIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, view);
        if(mFruit.media != null) {
            Picasso.with(getActivity()).load(mFruit.media.getUrl()).into(mBackdrop);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Toolbar setup
        mCollapsingToolbarLayout.setTitle(mFruit.name);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setContentScrimColor(mFruit.color);
        mCollapsingToolbarLayout.setStatusBarScrimColor(mFruit.color);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDetailPriceView.setText("£" + mFruit.price);
        mDetailWeightView.setText(mFruit.weight + "kg");

        API.getWikipediaInstance().getArticleSnippet(mFruit.name, this);
    }

    @Override
    public void success(Article article, Response response) {
        mDetailTextView.setText(Html.fromHtml(article.extract));
    }

    @Override
    public void failure(RetrofitError error) {

    }
}
