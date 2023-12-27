package exercise.controller;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
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
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping("")
    public List<BookDTO> index(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {

        return bookService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public BookDTO show(@PathVariable Long id) {

        return bookService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookCreateDTO bookCreateDTO) {

        return bookService.create(bookCreateDTO);
    }

    @PutMapping("/{id}")
    public BookDTO update(@PathVariable Long id,
                          @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {

        return bookService.update(id, bookUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        bookService.delete(id);
    }
    // END
}
