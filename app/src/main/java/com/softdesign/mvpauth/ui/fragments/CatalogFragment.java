package com.softdesign.mvpauth.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softdesign.mvpauth.R;
import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.CatalogScope;
import com.softdesign.mvpauth.mvp.presenters.CatalogPresenter;
import com.softdesign.mvpauth.mvp.views.ICatalogView;
import com.softdesign.mvpauth.ui.activities.RootActivity;
import com.softdesign.mvpauth.ui.fragments.adapters.CatalogAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class CatalogFragment extends Fragment implements ICatalogView, View.OnClickListener {

    private static final String TAG = "CatalogFragment";

    @Inject
    CatalogPresenter mPresenter;

    @BindView(R.id.add_to_card_btn)
    Button addToCardBtn;

    @BindView(R.id.product_pager)
    ViewPager productPager;


    public CatalogFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        mPresenter.takeView(this);
        mPresenter.initView();
        addToCardBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_to_card_btn) {
            mPresenter.clickOnByButton(productPager.getCurrentItem());
        }

    }


    //region ============================== ICatalogView ==============================

    @Override
    public void showCatalogView(List<ProductDto> productsList) {
        CatalogAdapter adapter = new CatalogAdapter(getChildFragmentManager());
        for (ProductDto product : productsList) {
            adapter.addItem(product);
        }
        productPager.setAdapter(adapter);
    }

    @Override
    public void showAuthScreen() {
        // TODO: 17.11.2016 show auth screen if user not auth
    }

    @Override
    public void updateProductCounter() {
        // TODO: 17.11.2016 update product on cart icon 
    }


    //endregion

    //region ============================== DI ==============================

    private Component createDaggerComponent() {
        return DaggerCatalogFragment_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(modules = Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogFragment fragment);
    }

    //endregion

}
