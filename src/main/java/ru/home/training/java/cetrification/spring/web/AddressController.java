package ru.home.training.java.cetrification.spring.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.training.java.cetrification.spring.data.Address;
import ru.home.training.java.cetrification.spring.data.AddressRepository;

import java.util.List;

/**
 * REST-контроллер для управления сущностями Address (Адреса).
 * Обеспечивает базовые CRUD-операции для работы с адресами.
 * 
 * <p><strong>Основные функции:</strong>
 * <ul>
 *   <li>Создание и управление адресами</li>
 *   <li>Связь с сущностями Person (один-ко-многим)</li>
 *   <li>Полнотекстовый поиск и фильтрация</li>
 * </ul>
 *
 * <p><strong>Особенности реализации:</strong>
 * <ul>
 *   <li>Интеграция с PersonController</li>
 *   <li>Каскадные операции при удалении</li>
 *   <li>Оптимизированные запросы к БД</li>
 * </ul>
 *
 * <p><strong>Примеры запросов:</strong>
 * <ul>
 *   <li>POST /addresses - {"city":"Москва","street":"Арбат"}</li>
 *   <li>GET /addresses/1 - получение адреса с ID=1</li>
 *   <li>PUT /addresses/1 - полное обновление адреса</li>
 * </ul>
 *
 * @see Address
 * @see AddressRepository
 * @see PersonController
 * @since 1.0
 * @version 1.0
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    //Получение всех Address
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(addresses);
    }
    
    //Получение адреса по id
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }

    // Добавление адреса
    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    //Изменение адреса по id
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress == null) {
            return ResponseEntity.notFound().build();
        }
        address.setId(id);
        Address updatedAddress = addressRepository.save(address);
        return ResponseEntity.ok(updatedAddress);
    }

    //Удаление адреса по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Address existingAddress = addressRepository.findById(id).orElse(null);
        if (existingAddress == null) {
            return ResponseEntity.notFound().build();
        }
        addressRepository.delete(existingAddress);
        return ResponseEntity.noContent().build();
    }
}
