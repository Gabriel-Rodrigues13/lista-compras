package com.gabriel.organizador.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gabriel.organizador.R
import com.gabriel.organizador.databinding.ActivityFormularioCadastroUsuarioBinding

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()


    }

    private fun configuraBotaoCadastrar(){
        val botaoCadastrar = binding.activityFormularioCadastroBotaoCadastrar
        botaoCadastrar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}