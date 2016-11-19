package com.softdesign.mvpauth.mvp.views;

import com.softdesign.mvpauth.data.storage.dto.ProductDto;

public interface IProductView extends IView {
    void showProductView(ProductDto product);
    void updateProductCountView(ProductDto product);
}
