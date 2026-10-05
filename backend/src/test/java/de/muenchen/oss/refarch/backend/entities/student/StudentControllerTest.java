package de.muenchen.oss.refarch.backend.entities.student;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
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
import de.muenchen.oss.refarch.backend.entities.student.dto.CreateUpdateStudentRequestDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.SimpleStudentDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.StudentDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.StudentMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService service;

    @Mock
    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        StudentController controller = new StudentController(service, mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenStudentsExist_thenSimpleStudentDTOListIsReturned() throws Exception {
        List<SimpleStudentDTO> dtoList = List.of(
                new SimpleStudentDTO(1, "Max", "Mustermann"),
                new SimpleStudentDTO(2, "Erika", "Musterfrau"));

        when(service.getAllStudents()).thenReturn(List.of()); // Service gibt beliebige Liste zurück
        when(mapper.toSimpleStudentDTO(anyList())).thenReturn(dtoList);

        String expectedJson = """
                [
                  {"studentId":1,"vorname":"Max","nachname":"Mustermann"},
                  {"studentId":2,"vorname":"Erika","nachname":"Musterfrau"}
                ]
                """;

        mockMvc.perform(get("/students")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).getAllStudents();
        verify(mapper).toSimpleStudentDTO(anyList());
    }

    @Test
    void givenValidId_thenStudentDTOIsReturned() throws Exception {
        int id = 1;
        Student studentEntity = mock(Student.class); // Mock-Objekt für das Service-Resultat
        StudentDTO dto = new StudentDTO(
                1,
                "Max",
                "Mustermann",
                List.of(), // leere Liste für Studiengänge
                null // kein Praktikum
        );

        when(service.getStudent(id)).thenReturn(studentEntity);
        when(mapper.toDTO(studentEntity)).thenReturn(dto);

        String expectedJson = """
                {
                  "studentId": 1,
                  "vorname": "Max",
                  "nachname": "Mustermann",
                  "studiengaenge": [],
                  "praktikum": null,
                  "url": "/praktikum?studentenId=1"
                }
                """;

        mockMvc.perform(get("/student/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).getStudent(id);
        verify(mapper).toDTO(studentEntity);
    }

    @Test
    void givenVornameUndNachname_thenSimpleStudentDTOListIsReturned() throws Exception {
        String vorname = "Max";
        String nachname = "Mustermann";
        List<SimpleStudentDTO> dtoList = List.of(
                new SimpleStudentDTO(1, "Max", "Mustermann"));

        when(service.findStudentsByName(vorname, nachname)).thenReturn(List.of());
        when(mapper.toSimpleStudentDTO(anyList())).thenReturn(dtoList);

        String expectedJson = """
                [
                  {"studentId":1,"vorname":"Max","nachname":"Mustermann"}
                ]
                """;

        mockMvc.perform(get("/studentByName")
                .param("vorname", vorname)
                .param("nachname", nachname)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).findStudentsByName(vorname, nachname);
        verify(mapper).toSimpleStudentDTO(anyList());
    }

    @Test
    void givenValidId_thenServiceDeleteStudentAndRelatedDataIsCalled() throws Exception {
        int id = 1;

        mockMvc.perform(delete("/student/{id}", id))
                .andExpect(status().isOk());

        verify(service).deleteStudentAndRelatedData(id);
    }

    @Test
    void givenValidIdAndBody_thenUpdatedStudentDTOIsReturned() throws Exception {
        int id = 1;
        CreateUpdateStudentRequestDTO body = new CreateUpdateStudentRequestDTO();
        body.setVorname("Max");
        body.setNachname("Mustermann");

        Student updatedStudent = mock(Student.class); // Mock-Objekt für das Service-Resultat

        StudentDTO dto = new StudentDTO(
                id,
                "Max",
                "Mustermann",
                List.of(), // leere Liste für Studiengänge
                null // kein Praktikum
        );

        when(service.updateStudent(eq(id), any(CreateUpdateStudentRequestDTO.class))).thenReturn(updatedStudent);
        when(mapper.toDTO(updatedStudent)).thenReturn(dto);

        String requestJson = """
                {
                  "vorname": "Max",
                  "nachname": "Mustermann"
                }
                """;

        String expectedJson = """
                {
                  "studentId": 1,
                  "vorname": "Max",
                  "nachname": "Mustermann",
                  "studiengaenge": [],
                  "praktikum": null,
                  "url": "/praktikum?studentenId=1"
                }
                """;

        mockMvc.perform(put("/student/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(service).updateStudent(eq(id), any(CreateUpdateStudentRequestDTO.class));
        verify(mapper).toDTO(updatedStudent);
    }

    @Test
    void givenValidCreateStudentDTO_thenIdIsReturned() throws Exception {
        CreateUpdateStudentRequestDTO dto = new CreateUpdateStudentRequestDTO();
        dto.setVorname("Max");
        dto.setNachname("Mustermann");

        Student studentEntity = mock(Student.class);

        when(mapper.toStudent(any(CreateUpdateStudentRequestDTO.class))).thenReturn(studentEntity);
        when(service.createStudent(studentEntity)).thenReturn(42);

        String requestJson = """
                {
                  "vorname": "Max",
                  "nachname": "Mustermann"
                }
                """;

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("42"));

        verify(mapper).toStudent(any(CreateUpdateStudentRequestDTO.class));
        verify(service).createStudent(studentEntity);
    }

}
