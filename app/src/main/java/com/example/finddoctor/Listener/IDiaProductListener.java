package com.example.finddoctor.Listener;

import com.example.finddoctor.Model.Diagnostic;

import java.util.List;

public interface IDiaProductListener {

    void onProductLoadSuccess(List<Diagnostic> diagnosticList);
    void onProductLoadFailed(String message);
}
