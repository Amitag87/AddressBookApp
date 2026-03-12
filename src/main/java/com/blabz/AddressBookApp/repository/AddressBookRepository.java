package com.blabz.AddressBookApp.repository;

import com.blabz.AddressBookApp.model.ContactPerson;

import java.util.*;
import java.util.stream.Collectors;

public class AddressBookRepository {
    // Map of address book name to list of contact persons
    private final Map<String, Set<ContactPerson>> addressBooks = new HashMap<>();

    public AddressBookRepository() {
    }

    public synchronized void createAddressBook(String name) {
        addressBooks.putIfAbsent(name, new LinkedHashSet<>());
    }

    public synchronized Set<ContactPerson> getAddressBook(String name) {
        return addressBooks.getOrDefault(name, Collections.emptySet());
    }

    public synchronized boolean addContact(String bookName, ContactPerson person) {
        createAddressBook(bookName);
        Set<ContactPerson> set = addressBooks.get(bookName);
        if (set.contains(person)) return false;
        set.add(person);
        return true;
    }

    public synchronized boolean editContact(String bookName, String firstName, String lastName, ContactPerson updated) {
        Set<ContactPerson> set = addressBooks.get(bookName);
        if (set == null) return false;
        Optional<ContactPerson> found = set.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
        if (found.isPresent()) {
            set.remove(found.get());
            set.add(updated);
            return true;
        }
        return false;
    }

    public synchronized boolean deleteContact(String bookName, String firstName, String lastName) {
        Set<ContactPerson> set = addressBooks.get(bookName);
        if (set == null) return false;
        return set.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName));
    }

    public synchronized List<ContactPerson> searchByCity(String city) {
        return addressBooks.values().stream()
                .flatMap(Set::stream)
                .filter(p -> p.getCity() != null && p.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public synchronized List<ContactPerson> searchByState(String state) {
        return addressBooks.values().stream()
                .flatMap(Set::stream)
                .filter(p -> p.getState() != null && p.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    public synchronized Map<String, List<ContactPerson>> viewByCity() {
        return addressBooks.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(p -> p.getCity() == null ? "" : p.getCity()));
    }

    public synchronized Map<String, List<ContactPerson>> viewByState() {
        return addressBooks.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(p -> p.getState() == null ? "" : p.getState()));
    }

    public synchronized long countByCity(String city) {
        return searchByCity(city).size();
    }

    public synchronized long countByState(String state) {
        return searchByState(state).size();
    }

    public synchronized List<ContactPerson> sortByName(String bookName) {
        return addressBooks.getOrDefault(bookName, Collections.emptySet()).stream()
                .sorted(Comparator.comparing(p -> normalize(p.getFirstName() + " " + p.getLastName())))
                .collect(Collectors.toList());
    }

    public synchronized List<ContactPerson> sortByCity(String bookName) {
        return addressBooks.getOrDefault(bookName, Collections.emptySet()).stream()
                .sorted(Comparator.comparing(p -> normalize(p.getCity())))
                .collect(Collectors.toList());
    }

    public synchronized List<ContactPerson> sortByState(String bookName) {
        return addressBooks.getOrDefault(bookName, Collections.emptySet()).stream()
                .sorted(Comparator.comparing(p -> normalize(p.getState())))
                .collect(Collectors.toList());
    }

    public synchronized List<ContactPerson> sortByZip(String bookName) {
        return addressBooks.getOrDefault(bookName, Collections.emptySet()).stream()
                .sorted(Comparator.comparing(p -> normalize(p.getZip())))
                .collect(Collectors.toList());
    }

    private String normalize(String s) {
        return s == null ? "" : s.toLowerCase();
    }

    public synchronized Set<String> listAddressBookNames() {
        return new LinkedHashSet<>(addressBooks.keySet());
    }
}
