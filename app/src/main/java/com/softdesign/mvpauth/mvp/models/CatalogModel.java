package com.softdesign.mvpauth.mvp.models;

import com.softdesign.mvpauth.data.managers.DataManager;
import com.softdesign.mvpauth.data.storage.dto.ProductDto;

import java.util.List;

public class CatalogModel extends AbstractModel{

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }


    public boolean isUserAuth() {
        return mDataManager.isUserAuth();
    }
}
