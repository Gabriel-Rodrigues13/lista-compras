package com.gabriel.organizador.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabriel.organizador.database.converter.Converters
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.database.dao.UsuarioDao
import com.gabriel.organizador.model.Produto
import com.gabriel.organizador.model.Usuario


@Database(entities = [Produto::class, Usuario::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        private var db : AppDatabase? = null
        fun instancia(context: Context): AppDatabase {
            return db?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "orgs.db"
            ).build().also {
                db = it
            }
        }
    }
}