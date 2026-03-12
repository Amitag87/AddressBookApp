package com.blabz.AddressBookApp.controller;

import com.blabz.AddressBookApp.model.ContactPerson;
import com.blabz.AddressBookApp.service.AddressBookService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class AddressBookController {
    private final AddressBookService service;
    private final Scanner scanner;

    public AddressBookController(AddressBookService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void interactiveMenu() {
        String currentBook = "default";
        service.createAddressBook(currentBook);
        while (true) {
            printMenu(currentBook);
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    currentBook = selectOrCreateAddressBook();
                    break;
                case "2":
                    addContact(currentBook);
                    break;
                case "3":
                    editContact(currentBook);
                    break;
                case "4":
                    deleteContact(currentBook);
                    break;
                case "5":
                    listAddressBooks();
                    break;
                case "6":
                    searchByCityOrState();
                    break;
                case "7":
                    viewByCityOrState();
                    break;
                case "8":
                    countByCityOrState();
                    break;
                case "9":
                    sortContacts(currentBook);
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void printMenu(String currentBook) {
        System.out.println("\nCurrent Address Book: " + currentBook);
        System.out.println("1) Switch/Create Address Book");
        System.out.println("2) Add Contact");
        System.out.println("3) Edit Contact");
        System.out.println("4) Delete Contact");
        System.out.println("5) List Address Books");
        System.out.println("6) Search by City/State");
        System.out.println("7) View by City/State");
        System.out.println("8) Count by City/State");
        System.out.println("9) Sort Contacts");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private String selectOrCreateAddressBook() {
        System.out.print("Enter address book name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return "default";
        }
        service.createAddressBook(name);
        System.out.println("Switched to address book: " + name);
        return name;
    }

    private void addContact(String bookName) {
        ContactPerson p = readContactFromConsole();
        boolean added = service.addContact(bookName, p);
        System.out.println(added ? "Contact added" : "Duplicate contact; not added");
    }

    private ContactPerson readContactFromConsole() {
        ContactPerson p = new ContactPerson();
        System.out.print("First name: "); p.setFirstName(scanner.nextLine().trim());
        System.out.print("Last name: "); p.setLastName(scanner.nextLine().trim());
        System.out.print("Address: "); p.setAddress(scanner.nextLine().trim());
        System.out.print("City: "); p.setCity(scanner.nextLine().trim());
        System.out.print("State: "); p.setState(scanner.nextLine().trim());
        System.out.print("Zip: "); p.setZip(scanner.nextLine().trim());
        System.out.print("Phone: "); p.setPhone(scanner.nextLine().trim());
        System.out.print("Email: "); p.setEmail(scanner.nextLine().trim());
        return p;
    }

    private void editContact(String bookName) {
        System.out.print("First name of contact to edit: ");
        String first = scanner.nextLine().trim();
        System.out.print("Last name of contact to edit: ");
        String last = scanner.nextLine().trim();
        System.out.println("Enter new values (leave blank to keep existing)");
        ContactPerson updated = readContactFromConsole();
        // If blank fields provided, attempt to preserve existing details by searching
        boolean success = service.editContact(bookName, first, last, updated);
        System.out.println(success ? "Contact updated" : "Contact not found");
    }

    private void deleteContact(String bookName) {
        System.out.print("First name of contact to delete: ");
        String first = scanner.nextLine().trim();
        System.out.print("Last name of contact to delete: ");
        String last = scanner.nextLine().trim();
        boolean success = service.deleteContact(bookName, first, last);
        System.out.println(success ? "Contact deleted" : "Contact not found");
    }

    private void listAddressBooks() {
        Set<String> names = service.listAddressBookNames();
        System.out.println("Address Books:");
        names.forEach(System.out::println);
    }

    private void searchByCityOrState() {
        System.out.print("Search by (1) City or (2) State: ");
        String c = scanner.nextLine().trim();
        if ("1".equals(c)) {
            System.out.print("City: ");
            String city = scanner.nextLine().trim();
            List<ContactPerson> res = service.searchByCity(city);
            res.forEach(System.out::println);
        } else if ("2".equals(c)) {
            System.out.print("State: ");
            String state = scanner.nextLine().trim();
            List<ContactPerson> res = service.searchByState(state);
            res.forEach(System.out::println);
        } else {
            System.out.println("Invalid choice");
        }
    }

    private void viewByCityOrState() {
        System.out.print("View by (1) City or (2) State: ");
        String c = scanner.nextLine().trim();
        if ("1".equals(c)) {
            Map<String, List<ContactPerson>> map = service.viewByCity();
            map.forEach((k,v) -> {
                System.out.println("City: " + k);
                v.forEach(System.out::println);
            });
        } else if ("2".equals(c)) {
            Map<String, List<ContactPerson>> map = service.viewByState();
            map.forEach((k,v) -> {
                System.out.println("State: " + k);
                v.forEach(System.out::println);
            });
        } else {
            System.out.println("Invalid choice");
        }
    }

    private void countByCityOrState() {
        System.out.print("Count by (1) City or (2) State: ");
        String c = scanner.nextLine().trim();
        if ("1".equals(c)) {
            System.out.print("City: ");
            String city = scanner.nextLine().trim();
            System.out.println("Count: " + service.countByCity(city));
        } else if ("2".equals(c)) {
            System.out.print("State: ");
            String state = scanner.nextLine().trim();
            System.out.println("Count: " + service.countByState(state));
        } else {
            System.out.println("Invalid choice");
        }
    }

    private void sortContacts(String bookName) {
        System.out.println("Sort by: 1) Name 2) City 3) State 4) Zip");
        String c = scanner.nextLine().trim();
        List<ContactPerson> list;
        switch (c) {
            case "1": list = service.sortByName(bookName); break;
            case "2": list = service.sortByCity(bookName); break;
            case "3": list = service.sortByState(bookName); break;
            case "4": list = service.sortByZip(bookName); break;
            default: System.out.println("Invalid choice"); return;
        }
        list.forEach(System.out::println);
    }
}
