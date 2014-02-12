package com.semijoias.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.semijoias.model.Cliente;
import com.semijoias.service.ClienteService;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	
	@Inject
	private ClienteService clienteService;

	@PostConstruct
	public void init() {
		this.limpar();
	}
	
	private void limpar() {
		this.cliente = new Cliente();
	}
	
	public void salvar() {
		this.clienteService.salvar(cliente);
		String msg = isEditando() ? "editado" : "salvo";
		FacesUtil.addInfoMessage(String.format("Cliente \"%s\" %s com sucesso.", this.cliente.getNome(), msg));
		this.limpar();
		
		mudarTituloPagina();
	}

	private void mudarTituloPagina() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("document.title = 'Cadastro de cliente';");
	}

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public boolean isEditando() {
        return this.cliente.getCodigo() != null;
	}
	
}
