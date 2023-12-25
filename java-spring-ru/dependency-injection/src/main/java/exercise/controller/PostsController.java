package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository repository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> getAll() {

        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post show(@PathVariable long id) {

        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return post;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return repository.save(post);
    }

    @PutMapping(path = "/{id}")
    public Post update(@PathVariable Long id, @RequestBody Post postData) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setBody(postData.getBody());
        post.setTitle(postData.getTitle());
        return repository.save(post);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}
// END
