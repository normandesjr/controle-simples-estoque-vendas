package com.semijoias.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.semijoias.model.Vendedor;
import com.semijoias.service.VendedorService;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Vendedor vendedor;
	
	@Inject
	private VendedorService vendedorService;

	@PostConstruct
	public void init() {
		this.limpar();
	}
	
	private void limpar() {
		this.vendedor = new Vendedor();
	}
	
	public void salvar() {
		this.vendedorService.salvar(vendedor);
		String msg = isEditando() ? "editado" : "salvo";
		FacesUtil.addInfoMessage(String.format("Vendedor \"%s\" %s com sucesso.", this.vendedor.getNome(), msg));
		this.limpar();
		
		mudarTituloPagina();
	}

	private void mudarTituloPagina() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("document.title = 'Cadastro de vendedor';");
	}

	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public boolean isEditando() {
        return this.vendedor.getCodigo() != null;
	}
	
}
