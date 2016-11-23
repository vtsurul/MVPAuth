package com.softdesign.mvpauth.ui.fragments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.ui.fragments.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends FragmentStatePagerAdapter{

    private List<ProductDto> mProductList = new ArrayList<>();

    public CatalogAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Log.e("CATALOG ADAPTER", "getItem: " + mProductList.get(position).getProductName());
        return ProductFragment.newInstance(mProductList.get(position));
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    public void addItem(ProductDto product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }
}
