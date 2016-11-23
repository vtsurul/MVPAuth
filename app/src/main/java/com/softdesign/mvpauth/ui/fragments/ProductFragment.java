package com.softdesign.mvpauth.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.mvpauth.App;
import com.softdesign.mvpauth.R;
import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.components.DaggerPicassoComponent;
import com.softdesign.mvpauth.di.components.PicassoComponent;
import com.softdesign.mvpauth.di.modules.PicassoCacheModule;
import com.softdesign.mvpauth.di.scopes.ProductScope;
import com.softdesign.mvpauth.mvp.presenters.ProductPresenter;
import com.softdesign.mvpauth.mvp.views.IProductView;
import com.softdesign.mvpauth.ui.activities.RootActivity;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class ProductFragment extends Fragment implements IProductView, View.OnClickListener {

    private static final String TAG = "ProductFragment";

    @BindView(R.id.product_name_txt)
    TextView productNameTxt;

    @BindView(R.id.product_description_txt)
    TextView productDescriptionTxt;

    @BindView(R.id.product_image)
    ImageView productImage;

    @BindView(R.id.product_count_txt)
    TextView productCountTxt;

    @BindView(R.id.product_price_txt)
    TextView productPriceTxt;

    @BindView(R.id.plus_btn)
    ImageButton plusBtn;

    @BindView(R.id.minus_btn)
    ImageButton minusBtn;

    @Inject
    Picasso mPicasso;

    @Inject
    ProductPresenter mPresenter;


    public ProductFragment() {
    }


    public static ProductFragment newInstance(ProductDto product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("PRODUCT", product);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            ProductDto product = bundle.getParcelable("PRODUCT");
            Component component = createDaggerComponent(product);
            component.inject(this);
            // TODO: 18.11.2016 fix recreate component
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        ButterKnife.bind(this, view);

        readBundle(getArguments());

        mPresenter.takeView(this);
        mPresenter.initView();

        plusBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onDestroyView() {
        Log.e("CATALOG ADAPTER", "onDestroyView " + mPresenter.getProduct().getProductName());
        mPresenter.dropView();
        mPresenter = null;
        minusBtn.setOnClickListener(null);
        plusBtn.setOnClickListener(null);
        super.onDestroyView();
    }

    //region ============================== IProductView ==============================


    @Override
    public void showProductView(final ProductDto product) {
        productNameTxt.setText(product.getProductName());
        productDescriptionTxt.setText(product.getDescription());
        productCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount()>0) {
            productPriceTxt.setText(String.valueOf(product.getCount() * product.getPrice() + ".-"));
        } else {
            productPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }
        mPicasso.load(product.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerCrop()
                .into(productImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "onSuccess: load from cache");
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "onError: load from cache");
                        mPicasso.load(R.drawable.radio_image)
                                .fit()
                                .centerCrop()
                                .into(productImage);
                    }
                });
    }


    @Override
    public void updateProductCountView(ProductDto product) {
        productCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            productPriceTxt.setText(product.getCount() * product.getPrice() + ".-");
        }

    }

    //endregion


    private RootActivity getRootActivity() {
        return (RootActivity) getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus_btn:
                mPresenter.clickOnMinus();
                break;
            case R.id.plus_btn:
                mPresenter.clickOnPlus();
                break;
        }
    }

    //region ============================== DI ==============================

    private Component createDaggerComponent(ProductDto product) {

        PicassoComponent picassoComponent = DaggerService.getComponent(PicassoComponent.class);
        if (picassoComponent == null) {
            picassoComponent = DaggerPicassoComponent.builder()
                    .appComponent(App.getAppComponent())
                    .picassoCacheModule(new PicassoCacheModule())
                    .build();
            DaggerService.registerComponent(PicassoComponent.class, picassoComponent);
        }

        return DaggerProductFragment_Component.builder()
                .picassoComponent(picassoComponent)
                .module(new Module(product))
                .build();
    }

    @dagger.Module
    public class Module {

        ProductDto mProductDto;

        public Module(ProductDto productDto) {
            mProductDto = productDto;
        }

        @Provides
        @ProductScope
        ProductPresenter provideProductPresenter() {
            return new ProductPresenter(mProductDto);
        }
    }

    @dagger.Component(dependencies = PicassoComponent.class, modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductFragment fragment);
    }

    //endregion
}
