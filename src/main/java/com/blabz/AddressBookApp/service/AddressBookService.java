package com.blabz.AddressBookApp.service;

import com.blabz.AddressBookApp.model.ContactPerson;
import com.blabz.AddressBookApp.repository.AddressBookRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddressBookService {
    private final AddressBookRepository repository;

    public AddressBookService(AddressBookRepository repository) {
        this.repository = repository;
    }

    public void createAddressBook(String name) {
        repository.createAddressBook(name);
    }

    public boolean addContact(String bookName, ContactPerson person) {
        return repository.addContact(bookName, person);
    }

    public boolean editContact(String bookName, String firstName, String lastName, ContactPerson updated) {
        return repository.editContact(bookName, firstName, lastName, updated);
    }

    public boolean deleteContact(String bookName, String firstName, String lastName) {
        return repository.deleteContact(bookName, firstName, lastName);
    }

    public List<ContactPerson> searchByCity(String city) { return repository.searchByCity(city); }
    public List<ContactPerson> searchByState(String state) { return repository.searchByState(state); }

    public Map<String, List<ContactPerson>> viewByCity() { return repository.viewByCity(); }
    public Map<String, List<ContactPerson>> viewByState() { return repository.viewByState(); }

    public long countByCity(String city) { return repository.countByCity(city); }
    public long countByState(String state) { return repository.countByState(state); }

    public List<ContactPerson> sortByName(String bookName) { return repository.sortByName(bookName); }
    public List<ContactPerson> sortByCity(String bookName) { return repository.sortByCity(bookName); }
    public List<ContactPerson> sortByState(String bookName) { return repository.sortByState(bookName); }
    public List<ContactPerson> sortByZip(String bookName) { return repository.sortByZip(bookName); }

    public Set<String> listAddressBookNames() { return repository.listAddressBookNames(); }
}
