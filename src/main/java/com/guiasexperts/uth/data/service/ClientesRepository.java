package com.guiasexperts.uth.data.service;

import com.guiasexperts.uth.data.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientesRepository extends JpaRepository<Clientes, Long>, JpaSpecificationExecutor<Clientes> {

}
