package se.systementor.javasecstart.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DogRepository extends CrudRepository<Dog, Long> {

    List<Dog> findAllBySoldToIsNull();
    List<Dog> findAll(Sort sort);
    List<Dog> findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContainsOrPrice
            (String name, String breed, String age, String size, int price, Sort sort);
    List<Dog> findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContains
            (String name, String breed, String age, String size, Sort sort);
    Dog findById(long id);
}