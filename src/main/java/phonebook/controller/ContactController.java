package phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phonebook.entity.Contact;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController {

    private final List<Contact> contacts;

    public ContactController() {
        this.contacts = new ArrayList<>();
        this.contacts.add(new Contact("Tuguldur", "94968969"));
        this.contacts.add(new Contact("Yolk", "94296331"));
    }

    private Contact findContact(String name, String number) {
        return contacts.stream()
                .filter(c -> c.getName().equals(name) && c.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    private boolean contactExists(String name, String number) {
        return contacts.stream()
                .anyMatch(c -> c.getName().equals(name) && c.getNumber().equals(number));
    }

    @PostMapping("/add")
    public String add(Contact contact) {
        if (!contactExists(contact.getName(), contact.getNumber())) {
            contacts.add(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", contacts);
        return "index";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String name, @RequestParam String number) {
        Contact contact = findContact(name, number);
        if (contact != null) {
            contacts.remove(contact);
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String name, @RequestParam String number, Model model) {
        Contact contact = findContact(name, number);
        if (contact != null) {
            model.addAttribute("contact", contact);
        }
        return "edit";
    }

    @PutMapping("/save")
    public String saveOrUpdate(
            @ModelAttribute Contact contact, // Шинэ контакт
            @RequestParam("oldName") String oldName, // Хуучин нэр
            @RequestParam("oldNumber") String oldNumber // Хуучин дугаар
    ) {
        // Жагсаалтад байгаа хуучин контактыг хайна
        for (Contact existingContact : contacts) {
            if (existingContact.getName().equals(oldName) &&
                    existingContact.getNumber().equals(oldNumber)) {
                // Хуучин контактыг шинэ утгаар шинэчилнэ
                existingContact.setName(contact.getName());
                existingContact.setNumber(contact.getNumber());
                break;
            }
        }

        return "redirect:/";
    }
}
