package org.example.springbootdemo.cars;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.model.EngineDTO;
import org.example.springbootdemo.cars.model.EngineRequest;
import org.example.springbootdemo.cars.persistance.Engine;
import org.example.springbootdemo.cars.persistance.EngineRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EngineService {

    private final EngineRepository engineRepository;


    public Page<EngineDTO> getEngines(int page, int pageSize, double capacity){
        return engineRepository.findEngines(capacity,PageRequest.of(page, pageSize));
    }




    public EngineDTO getEngine(Long Id){
        Engine engine=engineRepository.findById(Id).orElseThrow(() -> buildNotFoundException(Id));
        return mapEngine(engine);
    }
    public void createEngine(EngineRequest engineRequest) {
        Engine engine=new Engine();
        engine.setCapacity(engineRequest.getCapacity());
        engine.setHorsePower(engineRequest.getHorsePower());
        engineRepository.save(engine);
    }

    private EngineDTO mapEngine(Engine engine) {
        return new EngineDTO(engine.getId(),engine.getHorsePower(), engine.getCapacity());
    }


    public EngineDTO updateEngine(Long id, EngineRequest request) {
        Engine engine=engineRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
        engine.setHorsePower(request.getHorsePower());
        engine.setCapacity(request.getCapacity());
        engineRepository.save(engine);
        return mapEngine(engine);
    }

    public void deleteEngine(Long id) {
        engineRepository.deleteById(id);
    }


    public Engine findEngine(Long id){
        return engineRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
    }

    private NotFoundException buildNotFoundException(Long id) {
        return new NotFoundException("Engine with " + id + " not found");
    }



}
