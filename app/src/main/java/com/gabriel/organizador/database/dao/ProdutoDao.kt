package com.gabriel.organizador.database.dao

import androidx.room.*
import com.gabriel.organizador.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvar(vararg produto: Produto)

    @Delete
    fun delete(vararg produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE id = :id")
    fun buscaPorId(id: Long): Produto

}