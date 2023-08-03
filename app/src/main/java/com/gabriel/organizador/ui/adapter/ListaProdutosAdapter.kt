package com.gabriel.organizador.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabriel.organizador.databinding.ProdutoItemBinding
import com.gabriel.organizador.extensions.tentaCarregarImagem

import com.gabriel.organizador.model.Produto
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>,
    var quandoClicaNoItemListener: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {
    private val produtos = produtos.toMutableList()


    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                Log.i("ListaProdutoAdapter", "Clicando no item")
                if (::produto.isInitialized) {
                    quandoClicaNoItemListener(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.nome
            val descricao = binding.descricao
            val valor = binding.valor
            val valorEmMoeda = formataParaMoedaReal(produto)
            nome.text = produto.nome
            descricao.text = produto.descricao
            valor.text = valorEmMoeda

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibilidade

            binding.imageView.tentaCarregarImagem(produto.imagem)

        }

        private fun formataParaMoedaReal(produto: Produto): String? {
            val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(produto.valor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}