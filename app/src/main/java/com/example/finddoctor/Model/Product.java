package com.example.finddoctor.Model;

public class Product {
   private String id,left_name,left_prize,left_image,right_name,right_prize,right_image,category,key;

   public Product() {
   }

   public Product(String id, String left_name, String left_prize, String left_image, String right_name, String right_prize, String right_image,String category) {
      this.id = id;
      this.left_name = left_name;
      this.left_prize = left_prize;
      this.left_image = left_image;
      this.right_name = right_name;
      this.right_prize = right_prize;
      this.right_image = right_image;
      this.category=category;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getLeft_name() {
      return left_name;
   }

   public void setLeft_name(String left_name) {
      this.left_name = left_name;
   }

   public String getLeft_prize() {
      return left_prize;
   }

   public void setLeft_prize(String left_prize) {
      this.left_prize = left_prize;
   }

   public String getLeft_image() {
      return left_image;
   }

   public void setLeft_image(String left_image) {
      this.left_image = left_image;
   }

   public String getRight_name() {
      return right_name;
   }

   public void setRight_name(String right_name) {
      this.right_name = right_name;
   }

   public String getRight_prize() {
      return right_prize;
   }

   public void setRight_prize(String right_prize) {
      this.right_prize = right_prize;
   }

   public String getRight_image() {
      return right_image;
   }

   public void setRight_image(String right_image) {
      this.right_image = right_image;
   }
}
