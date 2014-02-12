package com.semijoias.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.semijoias.model.Peca;
import com.semijoias.repository.Pecas;
import com.semijoias.util.jpa.Transactional;

public class PecaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pecas pecas;
	
	@Transactional
	public Peca salvar(Peca peca) {
		return pecas.salvar(peca);
	}
	
}
