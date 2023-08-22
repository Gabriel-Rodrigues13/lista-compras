package com.gabriel.organizador.database.dao

import androidx.room.*
import com.gabriel.organizador.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    suspend fun buscaTodos(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(vararg produto: Produto)

    @Delete
    suspend fun delete(vararg produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE id = :id")
    suspend fun buscaPorId(id: Long): Produto

}