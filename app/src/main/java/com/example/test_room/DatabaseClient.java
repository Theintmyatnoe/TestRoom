package com.example.test_room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private Context mctx;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;
    private DatabaseClient(Context mctx){
        this.mctx=mctx;
        appDatabase= Room.databaseBuilder(mctx,AppDatabase.class,"Record").build();
    }
    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
