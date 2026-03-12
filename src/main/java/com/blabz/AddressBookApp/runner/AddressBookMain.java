package com.blabz.AddressBookApp.runner;

import com.blabz.AddressBookApp.controller.AddressBookController;
import com.blabz.AddressBookApp.repository.AddressBookRepository;
import com.blabz.AddressBookApp.service.AddressBookService;

import java.util.Scanner;

public class AddressBookMain {
    public static void main(String[] args) {
        AddressBookRepository repo = new AddressBookRepository();
        AddressBookService service = new AddressBookService(repo);
        AddressBookController controller = new AddressBookController(service, new Scanner(System.in));
        controller.interactiveMenu();
    }
}
