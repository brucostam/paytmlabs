package com.example.bmoreira.paytmchallenge;

import android.view.View;
import android.widget.AdapterView;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bruco on 2017-12-16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    MainActivity activity = null;
    @Mock
    MainPresenter presenter;

    @Mock
    ExchangeAdapter adapter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        activity = Robolectric.setupActivity(MainActivity.class);
        activity.setPresenter(presenter);
        when(adapter.getViewTypeCount()).thenReturn(1);
        activity.setExchangeAdapter(adapter);
    }

    @Test
    public void checkIfOnInstantiationAdapterIsSetToGridView() throws Exception {
        //check if adapter is set to gridview
        assertEquals(activity.gridView.getAdapter(), adapter);
    }

    @Test
    public void checkIfonDestroyMainViewBecomesNull() throws Exception {
        activity.onDestroy();
        //check if presenter is destroyed
        assertNull(presenter.getMainView());
    }

    @Test
    public void checkIfUpdateExchangeRatesListNotifiesAdapterToRefresh() throws Exception {
        //check if adapter was notified
        activity.updateExchangeRatesList();
        verify(adapter, times(1)).notifyDataSetChanged();
    }

    @Test
    public void checkIfHideEmptyListStateMakesViewInvisible() throws Throwable {
        //check if empty list state is invisible
        activity.hideEmptyListState();
        assertEquals(View.INVISIBLE, activity.findViewById(R.id.img_empty_state).getVisibility());
    }

    @Test
    public void checkIfShowEmptyListStateMakesViewVisible() throws Throwable {
        //check if empty list state is visible
        activity.showEmptyListState();
        assertEquals(View.VISIBLE, activity.findViewById(R.id.img_empty_state).getVisibility());
    }

    @Test
    public void checkIfHideGridMakesGridInvisible() throws Throwable {
        //check if grid list is invisible
        activity.hideGrid();
        assertEquals(View.INVISIBLE, activity.findViewById(R.id.gl_exchanges).getVisibility());
    }

    @Test
    public void checkIfShowGridMakesGridVisible() throws Throwable {
        //check if grid list is visible
        activity.showGrid();
        assertEquals(View.VISIBLE, activity.findViewById(R.id.gl_exchanges).getVisibility());
    }

    @Test
    public void checkIfUserChangingValueNotifiesPresenter() throws Throwable {
        // Check that user can change value and presenter is notified
        activity.cleanValueChanged(123);
        verify(presenter, times(1)).onAmountChange(123);
    }

    @Test
    public void checkIfUserChangingTheSpinnerNotifiesPresenter() throws Throwable {
        // Check user can change spinner value and presenter is notified

        AdapterView adapterView = mock(AdapterView.class);
        when(adapterView.getItemAtPosition(2)).thenReturn("CAD");
        activity.onItemSelected(adapterView, null,2, 0);
        verify(presenter,times(1)).onBaseCurrencyChange("CAD");
    }

}