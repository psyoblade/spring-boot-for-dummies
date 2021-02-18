package spring.dog;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DogDao {

    public DogDao() {
        System.out.println("Dog Constructor called.");
    }

    public List<Dog> getAllDogs() {
        System.out.println("DogDao.getAllDogs called.");
        return null;
    }
}
