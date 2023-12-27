package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorDTO> getAll(int page, int size){

        var authors = authorRepository.findAll(PageRequest.of(page - 1, size));
        return authors.stream().map(authorMapper::map).toList();
    }

    public AuthorDTO create (AuthorCreateDTO authorCreateDTO){

        var author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public AuthorDTO findById(Long id) {

        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        return authorMapper.map(author);
    }

    public AuthorDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {

        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        authorMapper.update(authorUpdateDTO,author);
        authorRepository.save(author);

        return authorMapper.map(author);
    }

    public void delete(Long id) {

        authorRepository.deleteById(id);
    }
    // END
}
