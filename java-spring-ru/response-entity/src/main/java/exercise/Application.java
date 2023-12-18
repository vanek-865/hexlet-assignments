package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    ResponseEntity<List<Post>> getPosts(@RequestParam(defaultValue = "10") Integer limit,
                                        @RequestParam(defaultValue = "1") Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(posts.size()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(posts.stream().skip(page * limit).limit(limit).toList());
    }


    @GetMapping("/posts/{id}")
    ResponseEntity<Optional<Post>> getPost(@PathVariable String id) {
        Optional<Post> post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (post.isPresent())
            return ResponseEntity
                    .ok()
                    .body(posts.stream()
                            .filter(p -> p.getId().equals(id))
                            .findFirst());
        else
            return ResponseEntity
                    .notFound().build();
    }

    @PostMapping("/posts") // Создание страницы
    public ResponseEntity<Post> create(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.created(URI.create(post.getId()))
                .body(post);
    }

    @PutMapping("/posts/{id}") // Обновление страницы
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post data) {
        var maybePost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setId(data.getId());
            post.setBody(data.getBody());
            post.setTitle(data.getTitle());
            return ResponseEntity
                    .ok()
                    .body(data);
        }else
            return ResponseEntity
                    .noContent()
                    .build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
