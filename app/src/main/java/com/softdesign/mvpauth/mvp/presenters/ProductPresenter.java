package com.softdesign.mvpauth.mvp.presenters;

import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.ProductScope;
import com.softdesign.mvpauth.mvp.models.ProductModel;
import com.softdesign.mvpauth.mvp.views.IProductView;

import javax.inject.Inject;

import dagger.Provides;

public class ProductPresenter extends AbstractPresenter<IProductView> implements IProductPresenter {
    private static final String TAG = "ProductPresenter";
    @Inject
    ProductModel mProductModel;
    private ProductDto mProduct;


    public ProductPresenter(ProductDto product) {
        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ProductPresenter.Component.class, component);
        }
        component.inject(this);
        mProduct = product;
    }


    @Override
    public void initView() {
        if (getView() != null) {
            getView().showProductView(mProduct);
        }
    }


    @Override
    public void clickOnPlus() {
        mProduct.addProduct();
        mProductModel.updateProduct(mProduct);
        if (getView() != null) {
            getView().updateProductCountView(mProduct);
        }
    }


    @Override
    public void clickOnMinus() {
        if (mProduct.getCount() > 0) {
            mProduct.deleteProduct();
            mProductModel.updateProduct(mProduct);
            if (getView() != null) {
                getView().updateProductCountView(mProduct);
            }
        }
    }

    //region ================================= DI =================================

    private Component createDaggerComponent() {
        return DaggerProductPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @ProductScope
        ProductModel provideProductModel() {
            return new ProductModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductPresenter presenter);
    }

    //endregion
}
