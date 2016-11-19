package com.softdesign.mvpauth.mvp.models;

import com.softdesign.mvpauth.data.managers.DataManager;
import com.softdesign.mvpauth.data.storage.dto.ProductDto;

public class ProductModel extends AbstractModel {


    public ProductDto getProductById(int productId) {
        // TODO: 16.11.2016 get product from datamanager;
        return mDataManager.getProductById(productId);
    }


    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }
}
