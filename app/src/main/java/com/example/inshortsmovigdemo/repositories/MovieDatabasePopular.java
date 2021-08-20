package com.example.inshortsmovigdemo.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.inshortsmovigdemo.models.MovieWrapperPopular;
import com.example.inshortsmovigdemo.utils.MovieCrudPopular;

@Database(entities = MovieWrapperPopular.class,version = 1,exportSchema = false)
public abstract class MovieDatabasePopular extends RoomDatabase {
        private static com.example.inshortsmovigdemo.repositories.MovieDatabasePopular instance;

        public abstract MovieCrudPopular movieCrudPopular();


        public static synchronized com.example.inshortsmovigdemo.repositories.MovieDatabasePopular getInstance(Context context){
            if(instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        com.example.inshortsmovigdemo.repositories.MovieDatabasePopular.class,"Stored_Movies_Popular")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build();
            }
            return instance;
        }
        private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // new PopulateDb(instance).execute();
            }
        };
}
