package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabriel.organizador.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
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
            val intent = Intent(this, ListaProdutosActivity::class.java )
            startActivity(intent)
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