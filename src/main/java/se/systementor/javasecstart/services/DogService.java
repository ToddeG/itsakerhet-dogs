package se.systementor.javasecstart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.model.DogRepository;

import java.util.List;

@Service
public class DogService {
    @Autowired
    DogRepository dogRepository;

    public List<Dog> getPublicDogs(){
        return dogRepository.findAllBySoldToIsNull();
    }

    public List<Dog> findAllSorted(Sort sort){
        return dogRepository.findAll(sort);
    }
    public String sanitizeSearchQuery(String searchQuery) {
        return searchQuery.replaceAll("[^a-zA-Z0-9\\s'\\-åÅäÄöÖ]", "").trim();
    }
    public List<Dog> findAllByStringQuery(String searchQuery, Sort sort){
        searchQuery = sanitizeSearchQuery(searchQuery);
        System.out.println(searchQuery);
        try {
            int priceQuery = Integer.parseInt(searchQuery);
            return dogRepository.findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContainsOrPrice
                    (searchQuery, searchQuery, searchQuery, searchQuery, priceQuery, sort);
        } catch (NumberFormatException e) {
            return dogRepository.findAllByNameContainsOrBreedContainsOrAgeContainsOrSizeContains
                    (searchQuery, searchQuery, searchQuery, searchQuery, sort);
        }
    }
    public Dog findDogById(int id){
        return dogRepository.findById(id);
    }
    public void updateDog(Dog dog){
        dogRepository.save(dog);
    }
}
