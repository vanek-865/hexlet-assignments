package exercise.controller;

import exercise.dto.GuestCreateDTO;
import exercise.dto.GuestDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.GuestMapper;
import exercise.model.Guest;
import exercise.repository.GuestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestsController {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    @GetMapping(path = "")
    public List<GuestDTO> index() {
        var products = guestRepository.findAll();
        return products.stream()
                .map(p -> guestMapper.map(p))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public GuestDTO show(@PathVariable long id) {

        var guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest with id " + id + " not found"));
        var guestDto = guestMapper.map(guest);
        return guestDto;
    }

    // BEGIN
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDTO create(@Valid @RequestBody GuestCreateDTO guestCreateDTO) {

        Guest guest = guestRepository.save(guestMapper.map(guestCreateDTO));
        return guestMapper.map(guest);
    }

    // END
}
