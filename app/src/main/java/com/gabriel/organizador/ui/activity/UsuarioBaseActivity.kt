package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.preferences.dataStore
import com.gabriel.organizador.database.preferences.usuarioLogadoPreferences
import com.gabriel.organizador.extensions.vaiPara
import com.gabriel.organizador.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class  UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)

    protected val usuario: MutableStateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()

        }
    }

    private suspend fun buscaUsuario(usuarioId: String) : Usuario? {
        return usuarioDao.buscaPorId(usuarioId).firstOrNull().also {
            _usuario.value = it
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java){
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }

    protected suspend fun deslogaUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(usuarioLogadoPreferences)
        }
    }
}