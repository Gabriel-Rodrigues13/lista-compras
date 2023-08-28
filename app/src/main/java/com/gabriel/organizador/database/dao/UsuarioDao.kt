package com.gabriel.organizador.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gabriel.organizador.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    fun salva(usuario: Usuario)
    @Query("SELECT * FROM Usuario WHERE id = :usuarioId AND senha = :senha")
    suspend fun autentica(usuarioId : String, senha : String) : Usuario?

    @Query("SELECT * FROM usuario WHERE id = :usuarioId")
    fun buscaPorId(usuarioId :String) : Flow<Usuario>


}