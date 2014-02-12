package com.semijoias.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.semijoias.model.Cliente;

public class PedidoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataVenda;
	private Cliente cliente;
	private boolean naoRecebido = true;

	public Date getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public boolean isNaoRecebido() {
		return naoRecebido;
	}
	public void setNaoRecebido(boolean naoRecebido) {
		this.naoRecebido = naoRecebido;
	}

}
