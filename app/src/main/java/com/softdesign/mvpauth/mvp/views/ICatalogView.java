package com.softdesign.mvpauth.mvp.views;

import com.softdesign.mvpauth.data.storage.dto.ProductDto;

import java.util.List;

public interface ICatalogView extends IView {
    //void showAddToCartMessage(ProductDto productDto);
    void showCatalogView(List<ProductDto> productsList);
    void showAuthScreen();
    void updateProductCounter();
}
