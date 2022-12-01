package com.example.finddoctor.Listener;


import com.example.finddoctor.Model.Product;

import java.util.List;

public interface IProductListener {
    void onDrinkLoadSuccess(List<Product> productList);
    void  onDrinkLoadFailed(String message);
}
