package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping("")
    public List<AuthorDTO> index(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        return authorService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public AuthorDTO show(@PathVariable Long id) {

        return authorService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {

        return authorService.create(authorCreateDTO);
    }

    @PutMapping("/{id}")
    public AuthorDTO update(@PathVariable Long id,
                            @Valid @RequestBody AuthorUpdateDTO authorUpdateDTO) {

        return authorService.update(id, authorUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        authorService.delete(id);
    }

    // END
}
