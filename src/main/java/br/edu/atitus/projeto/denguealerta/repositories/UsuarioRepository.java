package br.edu.atitus.projeto.denguealerta.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.edu.atitus.projeto.denguealerta.entities.UsuarioEntity;


@Repository
public interface UsuarioRepository extends GenericRepository<UsuarioEntity> {
	
	boolean existsByEmail(String email);
	
	boolean existsByCpf(String cpf);
	
	boolean existsByEmailAndIdNot(String email, UUID id);
	
	boolean existsByCpfAndIdNot(String cpf, UUID id);
	
	Optional<UsuarioEntity> findByEmail(String email);
	
}
