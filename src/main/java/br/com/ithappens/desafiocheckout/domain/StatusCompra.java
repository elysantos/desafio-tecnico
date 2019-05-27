package br.com.ithappens.desafiocheckout.domain;


public enum StatusCompra {

    STATUS_AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    STATUS_CANCELADO("Pagamento Cancelado"),
    STATUS_RETORNADO("Pagamento Estornado"),
    STATUS_ENTREGUE("Pedido Entregue"),
    STATUS_PAGO("Pagamento Efetuado");

    private String descricao;

    StatusCompra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
