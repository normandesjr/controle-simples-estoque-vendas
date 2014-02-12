package com.semijoias.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.semijoias.model.Vendedor;
import com.semijoias.repository.Vendedores;
import com.semijoias.repository.filter.VendedorFilter;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaVendedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private VendedorFilter filtro;
	private List<Vendedor> vendedoresFiltrados;
	
	private Vendedor vendedorSelecionado;
	
	@Inject
	private Vendedores vendedores;
	
	public PesquisaVendedoresBean() {
		this.filtro = new VendedorFilter();
	}
	
	public void pesquisar() {
		this.vendedoresFiltrados = this.vendedores.filtrados(filtro);
	}
	
	public void excluir() {
		this.vendedores.remover(vendedorSelecionado);
		this.vendedoresFiltrados.remove(this.vendedorSelecionado);
		
		FacesUtil.addInfoMessage("Vendedor \"" + vendedorSelecionado.getNome() + "\" removido com sucesso.");
	}

	public VendedorFilter getFiltro() {
		return filtro;
	}

	public List<Vendedor> getVendedoresFiltrados() {
		return vendedoresFiltrados;
	}

	public Vendedor getVendedorSelecionado() {
		return vendedorSelecionado;
	}
	public void setVendedorSelecionado(Vendedor vendedorSelecionado) {
		this.vendedorSelecionado = vendedorSelecionado;
	}
	
}
