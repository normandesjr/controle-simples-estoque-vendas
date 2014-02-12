package com.semijoias.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.semijoias.model.Peca;
import com.semijoias.repository.Pecas;
import com.semijoias.repository.filter.PecaFilter;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPecasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pecas pecas;
	
	private PecaFilter filtro;
	private List<Peca> pecasFiltradas;
	
	private Peca pecaSelecionada;
	
	public PesquisaPecasBean() {
		this.filtro = new PecaFilter();
	}
	
	public void pesquisar() {
		this.pecasFiltradas = this.pecas.filtradas(filtro);
	}
	
	public void excluir() {
		this.pecasFiltradas.remove(this.pecaSelecionada);
		this.pecas.remover(this.pecaSelecionada);
		
		FacesUtil.addInfoMessage("Peça [Cód. " + pecaSelecionada.getCodigo() + ", nome: " + pecaSelecionada.getNome() + "] excluída com sucesso.");
	}
	
	public PecaFilter getFiltro() {
		return filtro;
	}

	public List<Peca> getPecasFiltradas() {
		return pecasFiltradas;
	}

	public Peca getPecaSelecionada() {
		return pecaSelecionada;
	}
	public void setPecaSelecionada(Peca pecaSelecionada) {
		this.pecaSelecionada = pecaSelecionada;
	}

}
