package com.semijoias.model;

public enum FormaPagamento {

	A_VISTA("Á Vista"),
	PARCELADO("Parcelado");
	
	private String descricao;
	
	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
