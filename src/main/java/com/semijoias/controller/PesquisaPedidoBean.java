package com.semijoias.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.semijoias.model.Cliente;
import com.semijoias.model.Pedido;
import com.semijoias.repository.Clientes;
import com.semijoias.repository.Pedidos;
import com.semijoias.repository.filter.ClienteFilter;
import com.semijoias.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes clientes;
	
	@Inject
	private Pedidos pedidos;
	
	private PedidoFilter filtro;
	
	private List<Pedido> pedidosFiltrados;
	
	@PostConstruct
	public void init() {
		this.filtro = new PedidoFilter();
	}
	
	public void pesquisar() {
		this.pedidosFiltrados = this.pedidos.filtrados(filtro);
	}
	
	public List<Cliente> sugerirClientes(String consulta) {
		ClienteFilter filtro = new ClienteFilter();
		filtro.setNome(consulta);
		return clientes.filtrados(filtro);
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}
	
}
