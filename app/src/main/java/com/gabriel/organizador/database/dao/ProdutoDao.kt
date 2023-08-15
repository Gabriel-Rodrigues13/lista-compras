package com.gabriel.organizador.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gabriel.organizador.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto" )
    fun buscaTodos() : List<Produto>

    @Insert
    fun salvar(vararg produto:Produto)

    @Delete
    fun delete(vararg  produto:Produto)

}