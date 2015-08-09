package com.giljulio.tuttifrutti;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;

import com.giljulio.tuttifrutti.ui.DetailActivity;
import com.giljulio.tuttifrutti.ui.FruitListFragment;
import com.giljulio.tuttifrutti.ui.MainActivity;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final long TIMEOUT_IN_MS = 10 * 1000;
    private static final String FRUIT_NAME = "banana";

    private MainActivity mMainActivity;
    private Instrumentation mInstrumentation;

    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mInstrumentation = getInstrumentation();
        mMainActivity = getActivity();
    }

    public void testLoadsDefaultFragment() {
        Fragment fragment = mMainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fruit_list_fragment);

        assertTrue(fragment instanceof FruitListFragment);
    }

    public void testClickAtPosition() throws InterruptedException {
        // Set up Activity Monitor
        Instrumentation.ActivityMonitor detailActivityMonitor =
                mInstrumentation.addMonitor(DetailActivity.class.getName(),
                        null, false);

        // Click Grid item with title {@link MainActivityTest#FRUIT_NAME}
        onView(withId(R.id.fruit_grid)).perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText(FRUIT_NAME)), click()));

        // Wait for the Activity to Load
        DetailActivity receiverActivity = (DetailActivity)
                detailActivityMonitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);

        // Check the Activity has exists
        assertNotNull("DetailActivity is null", receiverActivity);

        // Check the Activity has loaded
        assertEquals("Monitor for DetailActivity has not been called",
                1, detailActivityMonitor.getHits());

        // Remove the Activity Monitor
        getInstrumentation().removeMonitor(detailActivityMonitor);
    }

}