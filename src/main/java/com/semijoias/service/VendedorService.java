package com.semijoias.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.semijoias.model.Vendedor;
import com.semijoias.repository.Vendedores;
import com.semijoias.util.jpa.Transactional;

public class VendedorService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Vendedores vendedores;
	
	@Transactional
	public void salvar(Vendedor vendedor) {
		vendedor.setNome(vendedor.getNome().trim());
		
		this.vendedores.salvar(vendedor);
	}

}
