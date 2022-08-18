package com.ekenozlu.dgpaysw02.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ekenozlu.dgpaysw02.models.Product;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM Product")
    List<Product> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addToCart(Product product);

    @Query("DELETE FROM Product")
    public void deleteAll();

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE productName = :productName)")
    public boolean isExist(String productName);

    @Query("UPDATE Product SET quantitySelected = quantitySelected+1 WHERE productName = :productName")
    public void incrementQuantity(String productName);

    @Query("SELECT quantitySelected FROM Product WHERE productName = :productName")
    public int getQuantity(String productName);

    @Query("SELECT SUM(quantitySelected * productPrice) FROM Product")
    public int getTotalPrice();

    @Query("SELECT COUNT(productName) FROM Product")
    public int countItems();

}
