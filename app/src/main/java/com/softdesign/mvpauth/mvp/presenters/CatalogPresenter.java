package com.softdesign.mvpauth.mvp.presenters;


import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.scopes.CatalogScope;
import com.softdesign.mvpauth.mvp.models.CatalogModel;
import com.softdesign.mvpauth.mvp.views.ICatalogView;
import com.softdesign.mvpauth.mvp.views.IRootView;
import com.softdesign.mvpauth.ui.activities.RootActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;

public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements ICatalogPresenter{

    @Inject
    RootPresenter mRootPresenter;

    @Inject
    CatalogModel mCatalogModel;
    private List<ProductDto> mProductList;


    public CatalogPresenter() {
        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);
    }


    @Override
    public void initView() {
        if (mProductList == null) {
            mProductList = mCatalogModel.getProductList();

            if (getView() != null) {
                getView().showCatalogView(mProductList);
            }
        }
    }


    @Override
    public void clickOnByButton(int position) {
        if (getView() != null) {
            if (checkUserAuth()) {
                getRootView().showMessage("Товар " + mProductList.get(position).getProductName() + " успешно добавлен в корзину");
            } else {
                getView().showAuthScreen();
            }
        }
    }

    private IRootView getRootView() {
        return mRootPresenter.getView();
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAuth();
    }


    //region ================================= DI =================================

    private Component createDaggerComponent() {
        return DaggerCatalogPresenter_Component.builder()
                .component(DaggerService.getComponent(RootActivity.Component.class))
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class, modules = Module.class)
    @CatalogScope
    interface Component {
       void inject(CatalogPresenter presenter);
    }

    //endregion
}
