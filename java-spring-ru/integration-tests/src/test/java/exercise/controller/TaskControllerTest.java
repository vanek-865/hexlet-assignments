package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    // BEGIN
    @Test
    public void showTest() throws Exception {
        Task generatedTask = generateTask();
        taskRepository.save(generatedTask);

        var request = get("/tasks/" + generatedTask.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Task task = taskRepository.findById(generatedTask.getId()).get();
        assertThat(task.getTitle()).isEqualTo((generatedTask.getTitle()));
        assertThat(task.getDescription()).isEqualTo((generatedTask.getDescription()));
    }

    @Test
    public void createTest() throws Exception {

        Task generatedTask = generateTask();

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(generatedTask));

        var result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isObject().containsEntry("description", generatedTask.getDescription());
        assertThatJson(body).isObject().containsEntry("title", generatedTask.getTitle());
    }

    @Test
    public void updateTest() throws Exception {

        Task generatedTask = generateTask();
        taskRepository.save(generatedTask);

        var data = new HashMap<>();
        data.put("description", "New description");
        data.put("title", "New title");

        var request = put("/tasks/" + generatedTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                // ObjectMapper конвертирует Map в JSON
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Optional<Task> task = taskRepository.findById(generatedTask.getId());
        assertThat(task.get().getDescription()).isEqualTo(("New description"));
        assertThat(task.get().getTitle()).isEqualTo(("New title"));
    }

    @Test
    public void deleteTest() throws Exception {

        Task generatedTask = generateTask();
        taskRepository.save(generatedTask);

        var request = delete("/tasks/" + generatedTask.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(generatedTask.getId())).isEmpty();
    }
    // END
}
