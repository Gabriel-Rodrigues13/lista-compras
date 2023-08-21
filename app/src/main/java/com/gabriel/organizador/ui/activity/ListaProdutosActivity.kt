package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.databinding.ActivityListaProdutosActivityBinding
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*

private const val TAG = "ListaProdutos"

class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }

    private val adapter = ListaProdutosAdapter(
        context = this
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val produtosDao = db.produtoDao()
        val scope = MainScope()
        scope.launch {
            val produtos = withContext(Dispatchers.IO) {
                produtosDao.buscaTodos ()
            }
            adapter.atualiza(produtos)
        }

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
        adapter.quandoClicaNoItemListener = {
            val intent =
                Intent(this, DetalhesProdutosActivity::class.java).apply {
                    putExtra(
                        CHAVE_PRODUTO_ID,
                        it.id
                    )
                }
            startActivity(intent)
        }
        adapter.quandoClicaEmEditar = {
            Log.i("PopUp", "configuraRecyclerView: popUp editar")
        }
        adapter.quandoClicaEmRemover = {
            Log.i("PopUp", "configuraRecyclerView: popUp editar")
        }

    }
}