package com.semijoias.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.semijoias.model.Cliente;
import com.semijoias.model.FormaPagamento;
import com.semijoias.model.ItemDoPedido;
import com.semijoias.model.Peca;
import com.semijoias.model.Pedido;
import com.semijoias.repository.Clientes;
import com.semijoias.repository.MargensLucro;
import com.semijoias.repository.Pecas;
import com.semijoias.repository.filter.ClienteFilter;
import com.semijoias.repository.filter.PecaFilter;
import com.semijoias.service.PedidoService;
import com.semijoias.util.jsf.FacesUtil;

@Named
@ViewScoped
public class NovoPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Pecas pecas;
	
	@Inject
	private Clientes clientes;

	@Inject
	private MargensLucro margensLucro;
	
	@Inject
	private PedidoService pedidoService;
	
	private Pedido pedido;
	
	private ItemDoPedido itemPedido;
	
	private int tabAtiva = 0;
	
	@PostConstruct
	public void init() {
		this.novo();
	}
	
	public void novo() {
		this.pedido = new Pedido();
		this.itemPedido = new ItemDoPedido();
		this.itemPedido.setPedido(this.pedido);
		this.tabAtiva = 0;
	}
	
	public void salvar() {
		Pedido pedidoSalvo = this.pedidoService.salvar(this.pedido);
		String msg = isEditando() ? "editado" : "salvo";
		FacesUtil.addInfoMessage(String.format("Pedido de número [%d] %s com sucesso.", pedidoSalvo.getCodigo(), msg));
		
		this.novo();
		
		mudarTituloPagina();
	}
	
	private void mudarTituloPagina() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("document.title = 'Novo pedido';");
	}
	
	public void carregarValoresPeca() {
		BigDecimal fatorMultiplicacao = this.margensLucro.fatorMultiplicacaoPara(this.itemPedido.getPeca().getCustoUnitario());
		this.itemPedido.setValorUnitario(fatorMultiplicacao.multiply(this.itemPedido.getPeca().getCustoUnitario()));
	}
	
	public void adicionarItem() {
		this.pedido.getItensDoPedido().add(this.itemPedido);
		this.itemPedido = new ItemDoPedido();
		this.itemPedido.setPedido(this.pedido);
		this.pedido.recalcularValorTotal();
		this.pedido.recalcularParcelas();
	}
	
	public void atualizarQuantidadeItem() {
		this.pedido.recalcularValorTotal();
		this.pedido.recalcularParcelas();
	}
	
	public void atualizarDesconto() {
		this.pedido.recalcularValorTotal();
		this.pedido.recalcularParcelas();
	}
	
	public List<Peca> sugerirPecas(String consulta) {
		PecaFilter filtro = new PecaFilter();
		
		try {
			filtro.setCodigo(new Long(consulta));
		} catch (NumberFormatException e) {}
		
		return pecas.filtradas(filtro);
	}
	
	public List<Cliente> sugerirClientes(String consulta) {
		ClienteFilter filtro = new ClienteFilter();
		filtro.setNome(consulta);
		return clientes.filtrados(filtro);
	}
	
	public void excluirItem(int linha) {
		this.pedido.getItensDoPedido().remove(linha);
		
		this.pedido.recalcularValorTotal();
		this.pedido.recalcularParcelas();
	}
	
	public void alterarTab(TabChangeEvent event) {
		if ("tabItens".equals(event.getTab().getId())) {
			this.tabAtiva = 0;
		} else if ("tabPagamento".equals(event.getTab().getId())) {
			this.tabAtiva = 1;
		}
	}
	
	public void atualizarFormaPagamento() {
		if (FormaPagamento.A_VISTA.equals(this.pedido.getPagamento().getFormaPagamento())) {
			this.pedido.getPagamento().setNumeroParcelas(1);
		} else if (this.pedido.getPagamento().getFormaPagamento() == null) {
			this.pedido.getPagamento().setNumeroParcelas(0);
		}
		
		this.atualizarParcelas();
	}
	
	public void atualizarParcelas() {
		this.pedido.recalcularParcelas();
	}

	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public ItemDoPedido getItemPedido() {
		return itemPedido;
	}
	
	public List<FormaPagamento> getFormasPagamento() {
		return Arrays.asList(FormaPagamento.values());
	}

	public int getTabAtiva() {
		return tabAtiva;
	}
	public void setTabAtiva(int tabAtiva) {
		this.tabAtiva = tabAtiva;
	}
	
	public boolean isEditando() {
        return this.pedido.getCodigo() != null;
	}
}
