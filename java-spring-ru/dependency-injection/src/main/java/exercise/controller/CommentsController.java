package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
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
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentRepository repository;

    @GetMapping(path = "")
    public List<Comment> getAll() {

        return repository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@PathVariable long id) {

        var comment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

        return comment;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        return repository.save(comment);
    }

    @PutMapping(path = "/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment commentData) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

        comment.setBody(commentData.getBody());
        return repository.save(comment);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }
}

// END
