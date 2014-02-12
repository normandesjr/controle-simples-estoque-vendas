package com.semijoias.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.semijoias.model.Pedido;
import com.semijoias.repository.Pedidos;
import com.semijoias.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Pedido.class)
public class PedidoConverter implements Converter {

	private Pedidos pedidos;
	
	public PedidoConverter() {
		this.pedidos = CDIServiceLocator.getBean(Pedidos.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pedido pedido = null;
		
		if (StringUtils.isNotBlank(value)) {
			Long codigo = new Long(value);
			pedido = pedidos.peloCodigoTodoInicializado(codigo);
		}
		
		return pedido;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object value) {
		if (value != null) {
			Pedido pedido = (Pedido) value;
			return String.valueOf(pedido.getCodigo());
		}
		
		return null;
	}

}
