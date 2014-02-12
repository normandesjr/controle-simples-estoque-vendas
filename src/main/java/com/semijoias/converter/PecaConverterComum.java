package com.semijoias.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.semijoias.model.Peca;
import com.semijoias.repository.Pecas;
import com.semijoias.util.cdi.CDIServiceLocator;

public class PecaConverterComum implements Converter {

private Pecas pecas;
	
	public PecaConverterComum() {
		this.pecas = CDIServiceLocator.getBean(Pecas.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Peca peca = null;
		
		if (StringUtils.isNotBlank(value)) {
			Long codigo = new Long(value);
			peca = pecas.peloCodigo(codigo);
		}
		
		return peca;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Peca peca = (Peca) value;
			return String.valueOf(peca.getCodigo());
		}
		
		return null;
	}

}
