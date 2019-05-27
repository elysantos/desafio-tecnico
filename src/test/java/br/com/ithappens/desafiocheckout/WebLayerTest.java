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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static capital.scalable.restdocs.AutoDocumentation.responseFields;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;


import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;



import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = {ProdutoController.class, ClienteController.class})
@SpringBootTest(classes ={ProdutoController.class, ClienteController.class})
@AutoConfigureRestDocs(outputDir = "target/snippets")
@EnableSpringDataWebSupport
public class WebLayerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }


    @Test
    public void getClientes() throws Exception {
        this.mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andDo(document("index-example", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()), links(linkWithRel("clientes").description("Clientes")),
                        responseFields(subsectionWithPath("_links").description("Links to other resources")),
                        responseHeaders(headerWithName("Content-Type")
                                .description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }


    @Test
    public void getProdutos() throws Exception {
        this.mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andDo(document("index-example", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()), links(linkWithRel("produtos").description("Produtos")),
                        responseFields(subsectionWithPath("_links").description("Links to other resources")),
                        responseHeaders(headerWithName("Content-Type")
                                .description("The Content-Type of the payload, e.g. `application/hal+json`"))));
    }

}
