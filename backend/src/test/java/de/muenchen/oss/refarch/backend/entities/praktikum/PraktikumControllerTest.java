package de.muenchen.oss.refarch.backend.entities.praktikum;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.GlobalExceptionHandler;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.FullPraktikumAggregat;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.FullPraktikumDTO;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumDTO;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumMapper;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumUpdateDTO;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class PraktikumControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PraktikumService service;

    @Mock
    private PraktikumMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PraktikumController controller = new PraktikumController(service, mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenValidId_thenFullPraktikumDTOIsReturned() throws Exception {
        int id = 1;
        FullPraktikumAggregat aggregat = mock(FullPraktikumAggregat.class);
        FullPraktikumDTO dto = new FullPraktikumDTO(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                10,
                40,
                4,
                List.of(),
                List.of());

        when(service.getPraktikum(id)).thenReturn(aggregat);
        when(mapper.toFullPraktikumDTO(aggregat)).thenReturn(dto);

        String expectedJson = """
                {
                  "beginnDatum": "2024-01-01",
                  "endeDatum": "2024-02-01",
                  "studentId": 10,
                  "wochenarbeitszeit": 40,
                  "benoetigteWochen": 4,
                  "taetigkeiten": [],
                  "zeitgutschriften": []
                }
                """;

        mockMvc.perform(get("/praktikum/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).getPraktikum(id);
        verify(mapper).toFullPraktikumDTO(aggregat);
    }

    @Test
    void givenValidCreateDTO_thenIdIsReturned() throws Exception {
        PraktikumDTO dto = new PraktikumDTO(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                10,
                40,
                4);
        Praktikum entity = mock(Praktikum.class);

        when(mapper.toPraktikum(dto)).thenReturn(entity);
        when(service.createPraktikum(entity)).thenReturn(123);

        String requestJson = """
                {
                  "beginnDatum": "2024-01-01",
                  "endeDatum": "2024-02-01",
                  "studentId": 10,
                  "wochenarbeitszeit": 40,
                  "benoetigteWochen": 4
                }
                """;

        mockMvc.perform(post("/praktikum")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("123"));

        verify(mapper).toPraktikum(dto);
        verify(service).createPraktikum(entity);
    }

    @Test
    void givenValidUpdateDTO_thenServiceIsCalled() throws Exception {
        int id = 42;
        PraktikumUpdateDTO dto = new PraktikumUpdateDTO(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 1),
                40,
                4);
        Praktikum entity = mock(Praktikum.class);

        when(mapper.toPraktikum(dto)).thenReturn(entity);

        String requestJson = """
                {
                  "beginnDatum": "2024-01-01",
                  "endeDatum": "2024-02-01",
                  "wochenarbeitszeit": 40,
                  "benoetigteWochen": 4
                }
                """;

        mockMvc.perform(put("/praktikum/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(mapper).toPraktikum(dto);
        verify(service).updatePraktikum(id, entity);
    }

    @Test
    void givenValidId_thenServiceIsCalled() throws Exception {
        int id = 42;

        mockMvc.perform(delete("/praktikum/{id}", id))
                .andExpect(status().isOk());

        verify(service).deletePraktikum(id);
    }

}
