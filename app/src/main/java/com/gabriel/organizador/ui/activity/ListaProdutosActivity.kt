package com.gabriel.organizador.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabriel.organizador.database.AppDatabase
import com.gabriel.organizador.database.dao.ProdutoDao
import com.gabriel.organizador.databinding.ActivityListaProdutosActivityBinding
import com.gabriel.organizador.ui.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

private const val TAG = "ListaProdutos"

class ListaProdutosActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }

    private val adapter = ListaProdutosAdapter(
        context = this
    )
    private val job = Job()

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
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "onResume: trowable $throwable")
            Toast.makeText(this@ListaProdutosActivity, "Ocorreu um problema", Toast.LENGTH_SHORT)
                .show()
        }



        val scope = MainScope()
        val firstJob = scope.launch(job) {
            repeat(1000) {
                Log.i(TAG, "onResume: coroutine esta em execucao $it")
                delay(1000)
            }
        }

        scope.launch(handler) {
            val produtos = withContext(Dispatchers.IO) {
                produtosDao.buscaTodos()
            }
            adapter.atualiza(produtos)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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