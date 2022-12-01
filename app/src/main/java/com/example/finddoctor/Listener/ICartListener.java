package com.example.finddoctor.Listener;


import com.example.finddoctor.Model.CartModel;

import java.util.List;

public interface ICartListener {

    void onCartLoadSuccess(List<CartModel> cartModelList);
    void  onCartLoadFailed(String message);
}
