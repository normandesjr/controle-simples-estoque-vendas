package com.semijoias.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.semijoias.model.Pedido;
import com.semijoias.repository.filter.PedidoFilter;

public class Pedidos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Pedido salvar(Pedido pedido) {
		return manager.merge(pedido);
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Pedido.class);
        
        if (filtro.getCliente() != null && filtro.getCliente().getCodigo() != null) {
        	criteria.add(Restrictions.eq("cliente", filtro.getCliente()));
        }
        
        if (filtro.getDataVenda() != null) {
        	criteria.add(Restrictions.eq("data", filtro.getDataVenda()));
        }
        
        if (filtro.isNaoRecebido()) {
        	criteria.createAlias("pagamento", "p");
        	criteria.add(Restrictions.eq("p.completamenteRecebido", !filtro.isNaoRecebido()));
        }
        
        return criteria.addOrder(Order.desc("data")).list();
	}

	public Pedido peloCodigoTodoInicializado(Long codigo) {
		Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Pedido.class);
        
        criteria.add(Restrictions.eq("codigo", codigo));
        
        criteria.setFetchMode("cliente", FetchMode.JOIN);
        criteria.setFetchMode("itensDoPedido", FetchMode.JOIN);
        criteria.setFetchMode("pagamento", FetchMode.JOIN);
		
		return (Pedido) criteria.uniqueResult();
	}

}
