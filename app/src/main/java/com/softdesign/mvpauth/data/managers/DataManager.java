
package com.softdesign.mvpauth.data.managers;

import com.softdesign.mvpauth.App;
import com.softdesign.mvpauth.data.network.RestService;
import com.softdesign.mvpauth.data.storage.dto.ProductDto;
import com.softdesign.mvpauth.di.DaggerService;
import com.softdesign.mvpauth.di.components.DaggerDataManagerComponent;
import com.softdesign.mvpauth.di.components.DataManagerComponent;
import com.softdesign.mvpauth.di.modules.LocalModule;
import com.softdesign.mvpauth.di.modules.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DataManager {

    @Inject
    PreferencesManager mPreferencesManager;

    @Inject
    RestService mRestService;

    private List<ProductDto> mMockProductList;

    public DataManager() {
        DataManagerComponent component = DaggerService.getComponent(DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);
        generateMockData();
    }


    public ProductDto getProductById(int productId) {
        // TODO: 16.11.2016 this is temp sample mock data fix me (may be load from db) 
        return mMockProductList.get(productId - 1);
    }


    public void updateProduct(ProductDto product) {
        // TODO: 16.11.2016 update product count or status (something in pruduct) save in DB
    }


    public List<ProductDto> getProductList() {
        // TODO: 16.11.2016 load product list from anywhere
        return mMockProductList;
    }


    private void generateMockData() {
        mMockProductList = new ArrayList<>();
        mMockProductList.add(new ProductDto(1, "test 1", "http://c.dns-shop.ru/thumb/st1/fit/wm/2000/1913/4e2cba88023b6f0ad4749609f3fc0e4b/759f7c38a97f54efb6f620f1425ff05c0d965380798527dff8bb8545a8b6c41c.jpg", "description 1 description 1 description 1 description 1description 1", 100, 10));
        mMockProductList.add(new ProductDto(2, "test 2", "imageUrl", "description 1 description 1 description 1 description 1description 1", 200, 10));
        mMockProductList.add(new ProductDto(3, "test 3", "imageUrl", "description 1 description 1 description 1 description 1description 1", 300, 10));
        mMockProductList.add(new ProductDto(4, "test 4", "imageUrl", "description 1 description 1 description 1 description 1description 1", 400, 10));
        mMockProductList.add(new ProductDto(5, "test 5", "imageUrl", "description 1 description 1 description 1 description 1description 1", 500, 10));
        mMockProductList.add(new ProductDto(6, "test 6", "imageUrl", "description 1 description 1 description 1 description 1description 1", 600, 10));
        mMockProductList.add(new ProductDto(7, "test 7", "imageUrl", "description 1 description 1 description 1 description 1description 1", 700, 10));
        mMockProductList.add(new ProductDto(8, "test 8", "imageUrl", "description 1 description 1 description 1 description 1description 1", 800, 10));
        mMockProductList.add(new ProductDto(9, "test 9", "imageUrl", "description 1 description 1 description 1 description 1description 1", 900, 10));
        mMockProductList.add(new ProductDto(10, "test 10", "imageUrl", "description 1 description 1 description 1 description 1description 1", 1000, 10));
    }

    public boolean isUserAuth() {
        // TODO: 17.11.2016 check user auth token in shared preferences
        return true;
    }
}
