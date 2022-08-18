package com.ekenozlu.dgpaysw02.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ekenozlu.dgpaysw02.models.Product;
import com.ekenozlu.dgpaysw02.ui.main.fragments.CartFragment;

@Database(entities = {Product.class},version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
}
