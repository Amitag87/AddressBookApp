package com.example.demo.service;

import com.example.demo.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService {
	private List<Contact> contactList = new ArrayList<>();
    private Long idCounter = 1L;

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public Contact getContactById(Long id) {
        Optional<Contact> contact =
                contactList.stream()
                        .filter(c -> c.getId().equals(id))
                        .findFirst();

        return contact.orElse(null);
    }
    
    public Contact addContact(Contact contact) {

        contact.setId(idCounter++);
        contactList.add(contact);
        return contact;
    }
    
    public Contact updateContact(Long id, Contact updatedContact) {

        for (Contact contact : contactList) {

            if (contact.getId().equals(id)) {

                contact.setFirstName(updatedContact.getFirstName());
                contact.setLastName(updatedContact.getLastName());
                contact.setAddress(updatedContact.getAddress());
                contact.setCity(updatedContact.getCity());
                contact.setState(updatedContact.getState());
                contact.setZip(updatedContact.getZip());
                contact.setPhoneNumber(updatedContact.getPhoneNumber());
                contact.setEmail(updatedContact.getEmail());

                return contact;
            }
        }

        return null;
    }
    
    public String deleteContact(Long id) {

        contactList.removeIf(contact -> contact.getId().equals(id));

        return "Contact deleted successfully";
    }
}
