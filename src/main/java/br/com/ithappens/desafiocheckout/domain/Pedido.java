package br.com.ithappens.desafiocheckout.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cliente cliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm", timezone = "GMT-3")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataPedido;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="pedidos_produtos",
            joinColumns={@JoinColumn(name="pedido_id",
                    referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="produto_id",
                    referencedColumnName="id")})
    private List<Produto> produtos;

    @OneToOne
    private DocumentoFiscal documentoFiscal;

    private StatusCompra statusCompra;

}
