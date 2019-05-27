package br.com.ithappens.desafiocheckout;

import br.com.ithappens.desafiocheckout.controller.ClienteController;
import br.com.ithappens.desafiocheckout.domain.Cliente;
import br.com.ithappens.desafiocheckout.repository.ClienteRepository;
import br.com.ithappens.desafiocheckout.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
@AutoConfigureRestDocs
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private ClienteService clienteService;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");


    @Test
    public void testCreateOneCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setCpf("03519427346");
        cliente.setNome("Ely Franklin Pontes dos Santos");
        cliente.setEmail("elyfranklin@outlook.com");

        when(this.clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        FieldDescriptor[] clienteDescriptor = getClienteFieldDescriptor();

        this.mockMvc.perform(post("/clientes").content(this.objectMapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value("03519427346"))
                .andExpect(jsonPath("$.nome").value("Ely Franklin Pontes dos Santos"))
                .andExpect(jsonPath("$.telefone").isEmpty())
                .andExpect(jsonPath("$.email").value("elyfranklin@outlook.com"))
                .andDo(document("shouldCreateCliente",
                        requestFields(clienteDescriptor),
                        responseFields(clienteDescriptor)));
    }

    private FieldDescriptor[] getClienteFieldDescriptor() {
        return new FieldDescriptor[]{fieldWithPath("cpf").description("Cpf do cliente").type(Integer.class.getSimpleName()),
                fieldWithPath("nome").description("Nome do cliente").type(String.class.getSimpleName()),
                fieldWithPath("telefone").description("Telefone do cliente").optional().type(String.class.getSimpleName()),
                fieldWithPath("email").description("E-mail do cliente").optional().type(String.class.getSimpleName()),
                fieldWithPath("dataCadastro").description("Data de cadastro do cliente").optional().type(Date.class.getSimpleName()),
                fieldWithPath("isAtivo").description("Informa se o cliente tem cadastro ativo").optional().type(Boolean.class.getSimpleName())};
    }
}
