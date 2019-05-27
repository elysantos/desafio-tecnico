package br.com.ithappens.desafiocheckout;

import br.com.ithappens.desafiocheckout.controller.ClienteController;
import br.com.ithappens.desafiocheckout.controller.ProdutoController;
import br.com.ithappens.desafiocheckout.domain.Cliente;
import br.com.ithappens.desafiocheckout.domain.Produto;
import br.com.ithappens.desafiocheckout.repository.ClienteRepository;
import br.com.ithappens.desafiocheckout.repository.ProdutoRepository;
import br.com.ithappens.desafiocheckout.services.ClienteService;
import br.com.ithappens.desafiocheckout.services.ProdutoService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = {ProdutoController.class, ClienteController.class})
@AutoConfigureRestDocs(outputDir = "target/snippets")
@EnableSpringDataWebSupport
public class WebLayerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void getClientes() throws Exception {
        when(this.clienteRepository.findAll(PageRequest.of(1, 15))).thenReturn(new PageImpl<>(Collections.singletonList(new Cliente())));

        this.mockMvc.perform(get("/clientes?page=1&size=15"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProdutos() throws Exception {
        when(this.produtoRepository.findAll(PageRequest.of(1, 15))).thenReturn(new PageImpl<>(Collections.singletonList(new Produto())));

        this.mockMvc.perform(get("/produtos?page=1&size=15"))
                .andExpect(status().isOk());
    }

}
