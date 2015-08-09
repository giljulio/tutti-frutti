package com.giljulio.tuttifrutti.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giljulio.tuttifrutti.R;
import com.giljulio.tuttifrutti.model.Fruit;
import com.giljulio.tuttifrutti.model.FruitList;
import com.giljulio.tuttifrutti.model.FruitMedia;
import com.giljulio.tuttifrutti.net.API;
import com.giljulio.tuttifrutti.ui.views.EmptyRecyclerView;
import com.giljulio.tuttifrutti.utils.Logger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.giljulio.tuttifrutti.R.string.app_name;

/**
 * Fruit list fragment
 */
public class FruitListFragment extends Fragment implements Callback<FruitList> {

    private static final Logger LOG = Logger.create();

    private static int COLUMN_COUNT;

    @InjectView(R.id.fruit_grid)
    EmptyRecyclerView mFruitGrid;

    @InjectView(R.id.fruit_grid_empty_view)
    View mEmptyView;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    private FruitAdapter mFruitAdapter;

    public FruitListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        COLUMN_COUNT = getResources().getInteger(R.integer.grid_cols_count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fruit_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        //setup toolbar
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mCollapsingToolbar.setTitle(getString(app_name));

        //Sets the empty view of the grid, show it can show a progress spinner
        mFruitGrid.setEmptyView(mEmptyView);

        mFruitGrid.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN_COUNT));
        mFruitAdapter = new FruitAdapter(getActivity());
        mFruitGrid.setAdapter(mFruitAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        mFruitGrid.addItemDecoration(new SpacesItemDecoration(COLUMN_COUNT, spacingInPixels, true));

        API.getBBCServiceInstance().getFruitList(this);
    }

    @Override
    public void success(FruitList fruitList, Response response) {
        attachImages(fruitList);
        int size = mFruitAdapter.mFruits.size();
        mFruitAdapter.mFruits.addAll(fruitList.fruits);
        mFruitAdapter.notifyItemRangeInserted(size, fruitList.fruits.size());
    }

    @Override
    public void failure(RetrofitError error) {
        //TODO: Handle failure
        LOG.e(error.toString());
    }

    /**
     * Helper method that attaches media to the fruits
     * @param fruitList with media
     */
    private void attachImages(FruitList fruitList){
        for(Fruit fruit : fruitList.fruits){
            fruit.media = FruitMedia.valueOf(fruit.name.toUpperCase());
        }
    }


}
