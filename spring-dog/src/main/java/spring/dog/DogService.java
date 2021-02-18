package spring.dog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {

    private DogDao dao;

    public List<Dog> getDogs() {
        return dao.getAllDogs();
    }

    @Autowired
    public void setDao(DogDao dao) {
        System.out.println("setter called");
        this.dao = dao;
    }

    public DogService() {
        System.out.println("DogService Constructor called");
    }

    public DogService(DogDao dao) {
        System.out.println("DogService(Dao) Constructor called");
        this.dao = dao;
    }
}
