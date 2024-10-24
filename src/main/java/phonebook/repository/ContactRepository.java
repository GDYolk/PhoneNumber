package phonebook.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import phonebook.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
    //CRUD үйлдлүүдийг автоматаар Spring Data JPA хийж өгнө.
    //@Query("SELECT c FROM Contact c WHERE c.name = ?1 AND c.number = ?2")
    Contact findByNameAndNumber(String name, String number);

    boolean existsByNameAndNumber(String name, String number);
}
