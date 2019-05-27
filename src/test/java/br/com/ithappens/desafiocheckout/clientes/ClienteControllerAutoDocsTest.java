package br.com.ithappens.desafiocheckout.clientes;
import br.com.ithappens.desafiocheckout.controller.ClienteController;
import br.com.ithappens.desafiocheckout.domain.Cliente;
import br.com.ithappens.desafiocheckout.repository.ClienteRepository;
import br.com.ithappens.desafiocheckout.services.ClienteService;
import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(ClienteController.class)
@EnableSpringDataWebSupport
public class ClienteControllerAutoDocsTest {
    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("desafiocheckout.ithappens.com.br")
                        .withPort(9292)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .alwaysDo(document("clientes",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void createCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setCpf("03519427346");
        cliente.setNome("Ely Franklin Pontes dos Santos");
        cliente.setEmail("elyfranklin@outlook.com");

        when(this.clienteService.salvar(any(Cliente.class))).then((Answer<Cliente>) invocationOnMock -> {
            if (invocationOnMock.getArguments().length > 0 && invocationOnMock.getArguments()[0] instanceof Cliente) {
                Cliente mockCustomer = (Cliente) invocationOnMock.getArguments()[0];
                mockCustomer.setId(34L);
                return mockCustomer;
            }
            return null;
        });

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/clientes}", 330).content(this.objectMapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

    }

    @Test
    void testUpdate() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId(344L);
        cliente.setCpf("03547599300");
        cliente.setNome("Luana Ferreira");
        cliente.setEmail("luanaf@outlook.com");

        this.mockMvc.perform(put("/clientes")
                .content(this.objectMapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    void testFindOne() throws Exception {

        this.mockMvc.perform(get("/clientes/30"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {

        when(this.clienteRepository.findAll(PageRequest.of(1, 15))).thenReturn(new PageImpl<>(Collections.singletonList(new Cliente())));

        this.mockMvc.perform(get("/clientes?page=1&size=15"))
                .andExpect(status().isOk());
    }

}
