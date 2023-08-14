package com.gabriel.organizador.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabriel.organizador.database.converter.Converters
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.model.Produto


@Database(entities = [Produto::class], version = 1 )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun produtoDao() : ProdutoDao
}