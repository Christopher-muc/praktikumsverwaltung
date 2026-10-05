package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.GlobalExceptionHandler;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockCreationDTO;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TaetigkeitenblockControllerTest {

    @Mock
    private TaetigkeitenblockService service;

    @Mock
    private TaetigkeitenblockMapper mapper;

    private MockMvc createMockMvc() {
        final TaetigkeitenblockController controller = new TaetigkeitenblockController(service, mapper);

        return MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenValidDate_thenGetsTaetigkeitByDay() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(get("/taetigkeitenblock/1/2026-05-10"))
                .andExpect(status().isOk());

        verify(service).getTaetigkeitbyDay(
                1,
                LocalDate.of(2026, 5, 10));
    }

    @Test
    void givenGermanDateFormat_thenRejectsGetTaetigkeitByDay() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(get("/taetigkeitenblock/1/10.05.2026"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenValidDto_thenCreatesTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": true,
                    "studentId": 1,
                    "beginnZeit": "08:00",
                    "endeZeit": "16:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        verify(mapper).toEntity(
                new TaetigkeitenblockCreationDTO(
                        true,
                        1,
                        LocalTime.of(8, 0),
                        LocalTime.of(16, 0),
                        LocalDate.of(2026, 5, 10)));
    }

    @Test
    void givenTimeWithoutSeconds_thenCreatesTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:15",
                    "endeZeit": "16:47",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        verify(mapper).toEntity(
                new TaetigkeitenblockCreationDTO(
                        false,
                        1,
                        LocalTime.of(8, 15),
                        LocalTime.of(16, 47),
                        LocalDate.of(2026, 5, 10)));
    }

    @Test
    void givenTimeWithSeconds_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:15:32",
                    "endeZeit": "16:47:59",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.beginnZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.errors.endeZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.globalErrors").isEmpty());

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenEndBeforeStart_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "16:00",
                    "endeZeit": "08:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value("Endzeit muss nach der Anfangszeit liegen"));

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenEqualStartAndEndTime_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:00",
                    "endeZeit": "08:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value("Endzeit muss nach der Anfangszeit liegen"));

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenOneSecondDifference_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:00:00",
                    "endeZeit": "08:00:01",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.endeZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.globalErrors").isEmpty());

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenEndOneSecondBeforeStart_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:00:01",
                    "endeZeit": "08:00:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.beginnZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.globalErrors[0]")
                        .value("Endzeit muss nach der Anfangszeit liegen"));

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenInvalidTime_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "25:00:00",
                    "endeZeit": "16:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.globalErrors").isArray());

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenInvalidSeconds_thenRejectsCreateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": false,
                    "studentId": 1,
                    "beginnZeit": "08:00:60",
                    "endeZeit": "16:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/taetigkeitenblock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.globalErrors").isArray());

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenValidParameters_thenUpdatesTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": true,
                    "studentId": 1,
                    "beginnZeit": "09:00",
                    "endeZeit": "17:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(put("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:00")
                .param("endeZeit", "16:00")
                .param("tag", "2026-05-10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(mapper).toEntity(
                LocalDate.of(2026, 5, 10),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                1);

        verify(mapper).toEntity(
                any(TaetigkeitenblockCreationDTO.class));
    }

    @Test
    void givenBodyWithSeconds_thenRejectsUpdateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": true,
                    "studentId": 1,
                    "beginnZeit": "09:15:12",
                    "endeZeit": "17:30:48",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(put("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:15")
                .param("endeZeit", "16:47")
                .param("tag", "2026-05-10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.beginnZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.errors.endeZeit[0]")
                        .value("Die Zeit darf keine Sekunden enthalten."))
                .andExpect(jsonPath("$.globalErrors").isEmpty());

        verifyNoInteractions(service);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenInvalidDateParameter_thenRejectsUpdateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": true,
                    "studentId": 1,
                    "beginnZeit": "09:00",
                    "endeZeit": "17:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(put("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:00")
                .param("endeZeit", "16:00")
                .param("tag", "10.05.2026")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenInvalidTimeParameter_thenRejectsUpdateTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "homeoffice": true,
                    "studentId": 1,
                    "beginnZeit": "09:00",
                    "endeZeit": "17:00",
                    "tag": "2026-05-10"
                }
                """;

        mockMvc.perform(put("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "falscheZeit")
                .param("endeZeit", "16:00")
                .param("tag", "2026-05-10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenValidParameters_thenDeletesTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(delete("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:00")
                .param("endeZeit", "16:00")
                .param("tag", "2026-05-10"))
                .andExpect(status().isOk());

        verify(mapper).toEntity(
                LocalDate.of(2026, 5, 10),
                LocalTime.of(8, 0),
                LocalTime.of(16, 0),
                1);
    }

    @Test
    void givenTimesWithSeconds_thenDeletesTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(delete("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:15:32")
                .param("endeZeit", "16:47:59")
                .param("tag", "2026-05-10"))
                .andExpect(status().isOk());

        verify(mapper).toEntity(
                LocalDate.of(2026, 5, 10),
                LocalTime.of(8, 15, 32),
                LocalTime.of(16, 47, 59),
                1);
    }

    @Test
    void givenInvalidSeconds_thenRejectsDeleteTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(delete("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:00:60")
                .param("endeZeit", "16:00")
                .param("tag", "2026-05-10"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenInvalidDateFormat_thenRejectsDeleteTaetigkeitenblock() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(delete("/taetigkeitenblock")
                .param("studentId", "1")
                .param("beginnZeit", "08:00")
                .param("endeZeit", "16:00")
                .param("tag", "10.05.2026"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
