package com.gabriel.organizador.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabriel.organizador.database.converter.Converters
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.model.Produto


@Database(entities = [Produto::class], version = 1, exportSchema = true )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun produtoDao() : ProdutoDao

    companion object {
        fun instancia(context : Context) : AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}