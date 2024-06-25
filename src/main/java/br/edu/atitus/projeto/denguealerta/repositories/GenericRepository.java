package br.edu.atitus.projeto.denguealerta.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.projeto.denguealerta.entities.GenericEntity;

public interface GenericRepository<E extends GenericEntity> extends JpaRepository<E, UUID> {

}
