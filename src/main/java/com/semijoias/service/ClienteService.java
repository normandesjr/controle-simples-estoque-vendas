package com.semijoias.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.semijoias.model.Cliente;
import com.semijoias.repository.Clientes;
import com.semijoias.util.jpa.Transactional;

public class ClienteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Clientes clientes;
	
	@Transactional
	public void salvar(Cliente cliente) {
		cliente.setNome(cliente.getNome().trim());
		
		this.clientes.salvar(cliente);
	}

}
