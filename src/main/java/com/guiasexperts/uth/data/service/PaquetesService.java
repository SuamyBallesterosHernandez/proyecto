package com.guiasexperts.uth.data.service;

import com.guiasexperts.uth.data.entity.Paquetes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PaquetesService {

    private final PaquetesRepository repository;

    public PaquetesService(PaquetesRepository repository) {
        this.repository = repository;
    }

    public Optional<Paquetes> get(Long id) {
        return repository.findById(id);
    }

    public Paquetes update(Paquetes entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Paquetes> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Paquetes> list(Pageable pageable, Specification<Paquetes> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
