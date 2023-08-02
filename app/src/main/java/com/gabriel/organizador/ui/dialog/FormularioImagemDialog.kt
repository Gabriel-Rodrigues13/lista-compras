package com.gabriel.organizador.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import coil.load
import com.gabriel.organizador.databinding.FormularioImagemBinding
import com.gabriel.organizador.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context : Context) {
    fun mostra(urlPadrao :String? = null
               ,quandoImagemCarregada: (imagem: String) -> Unit)
    {
        val binding = FormularioImagemBinding.inflate(LayoutInflater.from(context))

        urlPadrao?.let{
            binding.formularioImagemImageview.tentaCarregarImagem(it)
            binding.formularioImagemUrl.setText(it)
        }

        binding.formularioImagemBotaoCarregar.setOnClickListener {
            val url = binding.formularioImagemUrl.text.toString()
            binding.formularioImagemImageview.tentaCarregarImagem(url)

        }


        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirma") { _, _ ->
                val url = binding.formularioImagemUrl.text.toString()
                quandoImagemCarregada(url)
            }
            .setNegativeButton("Cancela") { _, _ -> }
            .show()
    }
}