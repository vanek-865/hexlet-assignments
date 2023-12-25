package exercise.controller;

import exercise.dto.CommentDTO;
import exercise.dto.PostDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// BEGIN
@RestController
@RequestMapping(path = "/posts")

public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<PostDTO> showAll() {

        List<Post> posts =  postRepository.findAll();
        List<PostDTO> postDTOS = posts.stream()
                .map(this::toDTO)
                .toList();

        return postDTOS;
    }

    @GetMapping(path = "/{id}")
    public PostDTO showAll(@PathVariable Long id) {

        return toDTO(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found")));
    }

    private PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setBody(post.getBody());
        dto.setTitle(post.getTitle());
        dto.setComments(getComments(post.getId()));
        return dto;
    }

    private List<CommentDTO> getComments(Long postId){

        return commentRepository.findByPostId(postId).stream().map(this::commentToDTO).toList();
    }

    private CommentDTO commentToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());

        return commentDTO;
    }

}

// END
