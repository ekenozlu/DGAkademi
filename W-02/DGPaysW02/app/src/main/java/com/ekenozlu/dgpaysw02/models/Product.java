package com.ekenozlu.dgpaysw02.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productID;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "productPrice")
    public Long productPrice;

    public Long productStock;

    @ColumnInfo(name = "quantitySelected")
    public int quantitySelected;

    @ColumnInfo(name = "productPhoto")
    public String productPhoto;

    public Product(String productName, Long productPrice, Long productStock,String productPhoto) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productPhoto = productPhoto;
        this.quantitySelected = 0;
    }
}
