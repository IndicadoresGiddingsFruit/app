package com.example.indicadoresapp.DBRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.android.gms.common.wrappers.InstantApps

@Database(entities=[DBRoom::class], version=1, exportSchema = false)
@TypeConverters(Converter::class)

abstract class DataBase: RoomDatabase ()
{
    abstract fun DBRoom():DBRoom

    companion object{
        @Volatile
        private var INSTANCE: DataBase?=null

        fun getDatabase(context: Context):DataBase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "DataBase"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}