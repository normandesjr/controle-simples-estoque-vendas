package com.semijoias.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.semijoias.model.MargemLucro;
import com.semijoias.repository.MargensLucro;
import com.semijoias.service.MargemLucroService;
import com.semijoias.util.format.Formatador;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroMargemLucroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private MargemLucro margemLucro;
	
	private List<MargemLucro> margensLucro;
	
	private MargemLucro margemLucroSelecionada;
	
	@Inject
	private MargemLucroService margemLucroService;
	
	@Inject
	private MargensLucro margensLucroRepository;
	
	@Inject
	private Formatador formatador;
	
	public CadastroMargemLucroBean() {
		limpar();
	}
	
	@PostConstruct
	public void init() {
		this.margensLucro = this.margensLucroRepository.buscarTodos();
	}

	public void salvar() {
		this.margemLucroService.salvar(margemLucro);
		this.limpar();
		init();
		FacesUtil.addInfoMessage("Margem lucro salva com sucesso.");
	}
	
	public void excluir() {
		this.margensLucro.remove(margemLucroSelecionada);
		this.margensLucroRepository.remover(this.margemLucroSelecionada);
		
		FacesUtil.addInfoMessage("Margem lucro com custo inicial " 
				+ formatador.moeda(margemLucroSelecionada.getCustoInicial())
				+ " e custo final "
				+ formatador.moeda(margemLucroSelecionada.getCustoFinal())
				+ " removida com sucesso.");
	}
	
	public void limpar() {
		this.margemLucro = new MargemLucro();
	}

	public MargemLucro getMargemLucro() {
		return margemLucro;
	}
	public void setMargemLucro(MargemLucro margemLucro) {
		this.margemLucro = margemLucro;
	}
	
	public List<MargemLucro> getMargensLucro() {
		return margensLucro;
	}

	public MargemLucro getMargemLucroSelecionada() {
		return margemLucroSelecionada;
	}

	public void setMargemLucroSelecionada(MargemLucro margemLucroSelecionada) {
		this.margemLucroSelecionada = margemLucroSelecionada;
	}
}
