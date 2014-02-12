package com.semijoias.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="estoque_vendedor")
public class EstoqueVendedor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EstoqueVendedorId id;
	private Long quantidade;

}
