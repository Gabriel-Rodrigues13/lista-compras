package com.gabriel.organizador.database.dao

import androidx.room.*
import com.gabriel.organizador.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(vararg produto: Produto)

    @Delete
    suspend fun delete(vararg produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>

}