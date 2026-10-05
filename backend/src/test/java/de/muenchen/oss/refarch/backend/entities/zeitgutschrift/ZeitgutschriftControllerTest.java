package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.CHECK_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.GlobalExceptionHandler;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftCreateDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ZeitgutschriftControllerTest {

    @Mock
    private ZeitgutschriftService service;

    private MockMvc createMockMvc() {
        final ZeitgutschriftController controller = new ZeitgutschriftController(service);

        return MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenValidLocalDateOnCreate_thenCreatesZeitgutschrift()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        when(service.createZeitgutschriftFromCreationDto(
                any(ZeitgutschriftCreateDTO.class)))
                .thenReturn(42);

        final String json = """
                {
                    "tag": "2026-05-10",
                    "mengeMinuten": 60,
                    "grund": "Test",
                    "praktikumID": 1
                }
                """;

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        final ArgumentCaptor<ZeitgutschriftCreateDTO> captor = ArgumentCaptor.forClass(ZeitgutschriftCreateDTO.class);

        verify(service)
                .createZeitgutschriftFromCreationDto(captor.capture());

        final ZeitgutschriftCreateDTO dto = captor.getValue();

        assertEquals(LocalDate.of(2026, 5, 10), dto.tag());
        assertEquals(60, dto.mengeMinuten());
        assertEquals("Test", dto.grund());
        assertEquals(1, dto.praktikumID());
    }

    @Test
    void givenDateTimeOnCreate_thenCutsOffTime()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "2026-05-10T12:30:45",
                    "mengeMinuten": 60,
                    "grund": "Test",
                    "praktikumID": 1
                }
                """;

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        final ArgumentCaptor<ZeitgutschriftCreateDTO> captor = ArgumentCaptor.forClass(ZeitgutschriftCreateDTO.class);

        verify(service)
                .createZeitgutschriftFromCreationDto(captor.capture());

        assertEquals(
                LocalDate.of(2026, 5, 10),
                captor.getValue().tag());
    }

    @Test
    void givenInvalidFieldsOnCreate_thenRejectsRequest()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "2026-05-10",
                    "mengeMinuten": 0,
                    "grund": "%s",
                    "praktikumID": 1
                }
                """.formatted("a".repeat(256));

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenInvalidFieldsOnCreate_thenReturnsValidationErrors()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "2026-05-10",
                    "mengeMinuten": 0,
                    "grund": "%s",
                    "praktikumID": 1
                }
                """.formatted("a".repeat(256));

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.errors.mengeMinuten[0]")
                        .value("Zeitgutschrift muss ein positiver Wert sein"))

                .andExpect(jsonPath("$.errors.grund[0]")
                        .value(
                                "Angabe des Grundes muss zwischen 0 und 250 Zeichen enthalten"))

                .andExpect(jsonPath("$.globalErrors").isEmpty());

        verifyNoInteractions(service);
    }

    @Test
    void givenGermanDateFormatOnCreate_thenRejectsRequest()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "10.05.2026",
                    "mengeMinuten": 60,
                    "grund": "Test",
                    "praktikumID": 1
                }
                """;

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value(CHECK_FORMAT));

        verifyNoInteractions(service);
    }

    @Test
    void givenSlashDateFormatOnCreate_thenRejectsRequest()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "2026/05/10",
                    "mengeMinuten": 60,
                    "grund": "Test",
                    "praktikumID": 1
                }
                """;

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value(CHECK_FORMAT));

        verifyNoInteractions(service);
    }

    @Test
    void givenInvalidDateOnCreate_thenRejectsRequest()
            throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "tag": "2026-02-31",
                    "mengeMinuten": 60,
                    "grund": "Test",
                    "praktikumID": 1
                }
                """;

        mockMvc.perform(post("/zeitgutschrift")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value(CHECK_FORMAT));

        verifyNoInteractions(service);
    }
}
