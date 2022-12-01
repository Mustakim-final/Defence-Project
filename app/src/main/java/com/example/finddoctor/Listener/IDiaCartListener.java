package com.example.finddoctor.Listener;

import com.example.finddoctor.Model.DiaCart;
import com.example.finddoctor.Model.Diagnostic;

import java.util.List;

public interface IDiaCartListener {
    void onCartLoadSuccess(List<DiaCart> diaCartList);
    void onCartLoadFailed(String message);
}
