package phonebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phonebook.model.Contact;
import phonebook.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/add")
    public String add(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Contact> contacts = contactService.getContacts();
        model.addAttribute("contacts", contacts);
        return "index";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String name,
                         @RequestParam String number
    ) {
        contactService.deleteContact(name, number);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String name,
                       @RequestParam String number,
                       Model model
    ) {
        Contact contact = contactService.findContact(name, number);
        if (contact != null) {
            model.addAttribute("contact", contact);
        }
        return "edit";
    }

    @PutMapping("/save")
    public String saveOrUpdate(
            @ModelAttribute Contact oldContact, // Шинэ контакт
            @RequestParam("oldName") String oldName, // Хуучин нэр
            @RequestParam("oldNumber") String oldNumber // Хуучин дугаар
    ) {
       contactService.editContact(oldContact, oldName, oldNumber);
        return "redirect:/";
    }
}
