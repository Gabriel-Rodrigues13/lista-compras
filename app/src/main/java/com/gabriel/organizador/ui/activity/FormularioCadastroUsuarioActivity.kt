package com.gabriel.organizador.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.gabriel.organizador.R
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.databinding.ActivityFormularioCadastroUsuarioBinding
import com.gabriel.organizador.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    private val dao by lazy{
        AppDatabase.instancia(this).usuarioDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()


    }

    private fun configuraBotaoCadastrar(){
        val botaoCadastrar = binding.activityFormularioCadastroBotaoCadastrar
        botaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    try {
                        dao.salva(novoUsuario)
                        finish()
                    }catch (e : Exception){
                        Log.e("BotaoCadastrarUsuario", "configuraBotaoCadastrar: $e", )
                        Toast.makeText(this@FormularioCadastroUsuarioActivity, "Falha ao cadastrar usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun criaUsuario() : Usuario{
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}