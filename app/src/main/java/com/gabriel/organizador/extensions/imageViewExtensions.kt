package com.gabriel.organizador.extensions

import android.widget.ImageView
import coil.load
import com.gabriel.organizador.R

fun ImageView.tentaCarregarImagem(url : String? =null){
    load(url){
        fallback(R.drawable.erro)
        error(R.drawable.erro)
        placeholder(R.drawable.ic_baseline_rectangle)
    }
}