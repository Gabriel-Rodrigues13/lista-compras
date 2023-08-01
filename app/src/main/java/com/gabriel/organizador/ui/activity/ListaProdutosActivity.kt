package com.gabriel.organizador.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.gabriel.organizador.R
import com.gabriel.organizador.dao.ProdutosDao
import com.gabriel.organizador.databinding.ActivityListaProdutosActivityBinding
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter


class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }
    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(
        context = this, produtos = dao.buscaTodos()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFab() {
        val fab = binding.extendedFab
        fab.setOnClickListener {
            val intent = Intent(this, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
    }
}