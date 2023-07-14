package com.guiasexperts.uth.data.service;

import com.guiasexperts.uth.data.entity.Paquetes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaquetesRepository extends JpaRepository<Paquetes, Long>, JpaSpecificationExecutor<Paquetes> {

}
