package com.example.inshortsmovigdemo.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.inshortsmovigdemo.models.MovieWrapperPlaying ;
import com.example.inshortsmovigdemo.utils.MovieCrudPlaying ;

@Database(entities = MovieWrapperPlaying .class,version = 1,exportSchema = false)
public abstract class MovieDatabasePlaying extends RoomDatabase{
    private static com.example.inshortsmovigdemo.repositories.MovieDatabasePlaying  instance;

    public abstract MovieCrudPlaying movieCrudPlaying();

    public static synchronized com.example.inshortsmovigdemo.repositories.MovieDatabasePlaying getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    com.example.inshortsmovigdemo.repositories.MovieDatabasePlaying.class,"Stored_Movies_Playing")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
