package com.example.finddoctor.Model;

public class DiagonsicTest {

    private String id,title,address,phone,item,price,diagnostic;
    private boolean isSelecting;

    public DiagonsicTest(String title, String item, String price) {
        this.title = title;
        this.item = item;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelecting() {
        return isSelecting;
    }

    public void setSelecting(boolean selecting) {
        isSelecting = selecting;
    }
}
