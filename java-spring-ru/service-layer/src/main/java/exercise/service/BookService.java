package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAll(int page, int size) {
        var books = bookRepository.findAll(PageRequest.of(page - 1, size));
        return books.stream().map(bookMapper::map).toList();
    }

    public BookDTO findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found " + id));
        return bookMapper.map(book);
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {

        var book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public BookDTO update(Long id, BookUpdateDTO bookUpdateDTO) {

        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);

        return bookMapper.map(book);
    }

    public void delete(Long id) {

        bookRepository.deleteById(id);
    }
    // END
}
