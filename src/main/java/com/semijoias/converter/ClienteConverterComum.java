package com.semijoias.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.semijoias.model.Cliente;
import com.semijoias.repository.Clientes;
import com.semijoias.util.cdi.CDIServiceLocator;

public class ClienteConverterComum implements Converter {

	private Clientes clientes;
	
	public ClienteConverterComum() {
		this.clientes = CDIServiceLocator.getBean(Clientes.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente cliente = null;
		
		if (StringUtils.isNotBlank(value)) {
			Long codigo = new Long(value);
			cliente = clientes.peloCodigo(codigo);
		}
		
		return cliente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Cliente cliente = (Cliente) value;
			return String.valueOf(cliente.getCodigo());
		}
		
		return null;
	}

	
}
