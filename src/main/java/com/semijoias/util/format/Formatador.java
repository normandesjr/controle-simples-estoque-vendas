package com.semijoias.util.format;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Formatador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	
	public String moeda(BigDecimal valor) {
		return formatador.format(valor);
	}
	
}
