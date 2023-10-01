package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.TeachersModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class JsonParsingTest {

    private String pathToJson = "src/test/resources/teachers.json";


    @Test
    @DisplayName("Проверка содержимого JSON-файла")
    void checkingJsonContent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TeachersModel teacher = objectMapper.readValue(new File(pathToJson), TeachersModel.class);

        Assertions.assertEquals("example json", teacher.getTitle());
        Assertions.assertEquals(1, teacher.getTeacher().getId());
        Assertions.assertEquals("Linda", teacher.getTeacher().getFirstName());
        Assertions.assertEquals("Smith", teacher.getTeacher().getLastName());
        Assertions.assertEquals("English", teacher.getTeacher().getSubject());
    }
}