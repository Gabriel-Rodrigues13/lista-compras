package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.preferences.dataStore
import com.gabriel.organizador.database.preferences.usuarioLogadoPreferences
import com.gabriel.organizador.databinding.ActivityLoginBinding
import com.gabriel.organizador.extensions.vaiPara
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDatabase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoEntrar()
        configuraBotaoCadastrarUsuario()
    }

    private fun configuraBotaoEntrar(){
        val botaoEntrar = binding.activityLoginBotaoEntrar

        botaoEntrar.setOnClickListener{

            val senha = binding.activityLoginSenha.text.toString()
            val usuario = binding.activityLoginUsuario.text.toString()

            lifecycleScope.launch {
                Log.i("Variaveis", "configuraBotaoEntrar: $senha $usuario ")
                usuarioDao.autentica(usuario, senha)?.let { usuario ->
                    dataStore.edit { settings->
                        settings[usuarioLogadoPreferences] = usuario.id
                    }
                    vaiPara(ListaProdutosActivity::class.java){

                    }
                }?:Toast.makeText(this@LoginActivity, "Falha na autenticação", Toast.LENGTH_SHORT).show()

            }

        }
    }
    private fun configuraBotaoCadastrarUsuario(){
        val botaoCadastrar = binding.activityLoginBotaoCadastrar
        botaoCadastrar.setOnClickListener{
            val intent = Intent(this, FormularioCadastroUsuarioActivity::class.java )
            startActivity(intent)
        }
    }

}