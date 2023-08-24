package com.gabriel.organizador.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.gabriel.organizador.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    fun salva(usuario: Usuario)
}