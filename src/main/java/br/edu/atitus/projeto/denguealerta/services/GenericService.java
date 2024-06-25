package br.edu.atitus.projeto.denguealerta.services;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.edu.atitus.projeto.denguealerta.entities.GenericEntity;
import br.edu.atitus.projeto.denguealerta.repositories.GenericRepository;

public abstract class GenericService<E extends GenericEntity> {
    
    protected abstract GenericRepository<E> getRepository();
    
    protected abstract void validate(E entidade) throws Exception;
    
    public final E save(E entidade) throws Exception {
        if (entidade == null) {
            throw new Exception("Objeto não pode ser null!");
        }
        this.validate(entidade);
        return this.getRepository().save(entidade);
    }
    
    public final E findById(UUID id) throws Exception {
        return this.getRepository().findById(id)
                .orElseThrow(() -> new Exception("Entidade não encontrada!"));
    }
    
    public final Page<E> findAll(Pageable pageable) throws Exception {
        return this.getRepository().findAll(pageable);
    }
    
    public final void deleteById(UUID id) throws Exception {
        this.getRepository().deleteById(id);
    }
}
