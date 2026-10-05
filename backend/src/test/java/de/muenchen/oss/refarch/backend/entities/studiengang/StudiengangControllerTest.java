package de.muenchen.oss.refarch.backend.entities.studiengang;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.GlobalExceptionHandler;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangCreationDTO;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangDTO;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class StudiengangControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudiengangService service;

    @Mock
    private StudiengangMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        StudiengangController controller = new StudiengangController(service, mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenStudiengaengeExist_thenStudiengangDTOListIsReturned() throws Exception {
        List<StudiengangDTO> dtoList = List.of(
                new StudiengangDTO(1, "Informatik"),
                new StudiengangDTO(2, "Mathematik"));

        when(service.getStudiengaenge()).thenReturn(List.of()); // Service gibt beliebige Liste zurück
        when(mapper.toDTO(anyList())).thenReturn(dtoList);

        String expectedJson = """
                [
                  {"studiengangNr":1,"name":"Informatik"},
                  {"studiengangNr":2,"name":"Mathematik"}
                ]
                """;

        mockMvc.perform(get("/studiengaenge")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).getStudiengaenge();
        verify(mapper).toDTO(anyList());
    }

    @Test
    void givenValidId_thenServiceDeleteStudiengangIsCalled() throws Exception {
        int id = 1;

        mockMvc.perform(delete("/studiengaenge/{id}", id))
                .andExpect(status().isOk());

        verify(service).deleteStudiengang(id);
    }

    @Test
    void givenValidStudiengangCreationDTO_thenIdIsReturned() throws Exception {
        Studiengang entity = mock(Studiengang.class);

        when(mapper.toEntity(any(StudiengangCreationDTO.class))).thenReturn(entity);
        when(service.createStudiengang(entity)).thenReturn(5);

        String requestJson = """
                {
                  "name": "Informatik"
                }
                """;

        mockMvc.perform(post("/studiengaenge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(mapper).toEntity(any(StudiengangCreationDTO.class));
        verify(service).createStudiengang(entity);
    }

}
