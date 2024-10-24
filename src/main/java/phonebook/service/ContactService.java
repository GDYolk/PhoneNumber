package phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phonebook.model.Contact;
import phonebook.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact findContact(String name, String number) {
        return contactRepository.findByNameAndNumber(name, number);
    }
    public boolean contactExists(String name, String number) {
        return contactRepository.existsByNameAndNumber(name, number);
    }
    public void saveContact(Contact contact) {
        if (!contactExists(contact.getName(), contact.getNumber()))
            contactRepository.save(contact);
    }
    public void deleteContact(String name, String number) {
        contactRepository.delete(contactRepository.findByNameAndNumber(name, number));
    }
    public List<Contact> getContacts() {
        return contactRepository.findAll().stream().toList();
    }
    public void editContact(Contact contact, String oldName, String oldNumber) {
        contact.setName(oldName);
        contact.setNumber(oldNumber);
    }
}
