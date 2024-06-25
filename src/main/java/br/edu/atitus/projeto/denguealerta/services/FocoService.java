package br.edu.atitus.projeto.denguealerta.services;

import org.springframework.stereotype.Service;
import br.edu.atitus.projeto.denguealerta.entities.FocoEntity;
import br.edu.atitus.projeto.denguealerta.repositories.FocoRepository;
import br.edu.atitus.projeto.denguealerta.repositories.GenericRepository;

@Service
public class FocoService extends GenericService<FocoEntity> {

    private final FocoRepository focoRepository;

    public FocoService(FocoRepository focoRepository) {
        super();
        this.focoRepository = focoRepository;
    }

    @Override
    protected GenericRepository<FocoEntity> getRepository() {
        return this.focoRepository;
    }

    @Override
    protected void validate(FocoEntity entidade) throws Exception {
        // Validação da entidade FocoEntity
    }
}
