package br.com.ithappens.desafiocheckout.controller;

import br.com.ithappens.desafiocheckout.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "pedidos", value = "pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


}
