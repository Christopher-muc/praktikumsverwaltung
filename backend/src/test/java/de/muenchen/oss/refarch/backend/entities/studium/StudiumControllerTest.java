package de.muenchen.oss.refarch.backend.entities.studium;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.GlobalExceptionHandler;
import de.muenchen.oss.refarch.backend.entities.student.dto.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class StudiumControllerTest {

    @Mock
    private StudiumService service;

    @Mock
    private StudentMapper studentMapper;

    private MockMvc createMockMvc() {
        final StudiumController controller = new StudiumController(service, studentMapper);

        return MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenValidDto_thenAddsStudiumToStudent() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 1,
                    "studiengangId": 2
                }
                """;

        mockMvc.perform(put("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(service).addStudiumToStudent(
                new StudiumMappingDTO(1, 2));
    }

    @Test
    void givenInvalidStudentId_thenRejectsRequest() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 0,
                    "studiengangId": 2
                }
                """;

        mockMvc.perform(put("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenInvalidStudiengangId_thenRejectsRequest() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 1,
                    "studiengangId": 0
                }
                """;

        mockMvc.perform(put("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenBothInvalidIds_thenRejectsRequest() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 0,
                    "studiengangId": 0
                }
                """;

        mockMvc.perform(put("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenValidDto_thenRemovesStudiumFromStudent() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 1,
                    "studiengangId": 2
                }
                """;

        mockMvc.perform(delete("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(service).removeStudiumFromStudent(
                new StudiumMappingDTO(1, 2));
    }

    @Test
    void givenInvalidDto_thenRejectsRemoveRequest() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        final String json = """
                {
                    "studentId": 0,
                    "studiengangId": 0
                }
                """;

        mockMvc.perform(delete("/studium")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void givenStudiengangId_thenGetsStudents() throws Exception {
        final MockMvc mockMvc = createMockMvc();

        mockMvc.perform(get("/studium/1"))
                .andExpect(status().isOk());

        verify(service).getStudentenPerStudiengang(1);
    }
}
