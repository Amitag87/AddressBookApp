package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.service.AddressBookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
	@Autowired
	private AddressBookService service;
	
	@GetMapping
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }
	
	@GetMapping("/test")
	public String test() {
	    return "Controller Working";
	}

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return service.getContactById(id);
    }
    
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return service.addContact(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(
            @PathVariable Long id,
            @RequestBody Contact contact) {

        return service.updateContact(id, contact);
    }
    
    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        return service.deleteContact(id);
    }
}
