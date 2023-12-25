package exercise.controller;

import exercise.dto.ContactCreateDTO;
import exercise.dto.ContactDTO;
import exercise.model.Contact;
import exercise.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@ResponseStatus(HttpStatus.CREATED)
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping(path = "")
    public ContactDTO create(@RequestBody ContactCreateDTO contactData) {

        Contact contact = contactRepository.save(toEntity(contactData));
        return toContactDTO(contact);
    }

    private Contact toEntity(ContactCreateDTO contactCreateDTO) {

        Contact contact = new Contact();
        contact.setPhone(contactCreateDTO.getPhone());
        contact.setFirstName(contactCreateDTO.getFirstName());
        contact.setLastName(contactCreateDTO.getLastName());

        return contact;
    }

    private ContactDTO toContactDTO(Contact contact) {

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setId(contact.getId());
        contactDTO.setUpdatedAt(contact.getUpdatedAt());
        contactDTO.setCreatedAt(contact.getCreatedAt());

        return contactDTO;
    }
    // END
}
