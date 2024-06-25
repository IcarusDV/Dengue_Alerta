package br.edu.atitus.projeto.denguealerta.services;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.atitus.projeto.denguealerta.components.TipoUsuario;
import br.edu.atitus.projeto.denguealerta.components.Validador;
import br.edu.atitus.projeto.denguealerta.entities.UsuarioEntity;
import br.edu.atitus.projeto.denguealerta.repositories.GenericRepository;
import br.edu.atitus.projeto.denguealerta.repositories.UsuarioRepository;

@SuppressWarnings("unused")
@Service
public class UsuarioService extends GenericService<UsuarioEntity> implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected GenericRepository<UsuarioEntity> getRepository() {
		return this.usuarioRepository;
	}

	@Override
	public void validate(UsuarioEntity usuario) throws Exception {
		if (usuario.getNome() == null || usuario.getNome().isEmpty())
			throw new Exception("Nome informado inválido");
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty())
			throw new Exception("E-mail informado inválido");
		if (usuario.getEndereco() == null || usuario.getEndereco().isEmpty())
			throw new Exception("Endereço informado inválido");
		if (usuario.getCpf() == null || usuario.getCpf().isEmpty())
			throw new Exception("CPF informado inválido");
		if (usuario.getSenha() == null || usuario.getSenha().isEmpty())
			throw new Exception("Senha informada inválida");

		// if (!Validador.validaCPF(usuario.getCpf()))
		// throw new Exception("CPF informado inválido");
		if (!Validador.validaEmail(usuario.getEmail()))
			throw new Exception("E-mail informado inválido");

		if (usuario.getId() != null) {
			if (usuarioRepository.existsByCpfAndIdNot(usuario.getCpf(), usuario.getId()))
				throw new Exception("Já existe usuário com este CPF");
			if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId()))
				throw new Exception("Já existe usuário com este e-mail");
		} else {
			if (usuarioRepository.existsByCpf(usuario.getCpf()))
				throw new Exception("Já existe usuário com este CPF");
			if (usuarioRepository.existsByEmail(usuario.getEmail()))
				throw new Exception("Já existe usuário com este e-mail");
		}

		String hashSenha = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(hashSenha);

		Object login = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (login instanceof String && login.equals("anonymousUser")) {
			if (usuario.getTipo() != TipoUsuario.Cidadao)
				throw new Exception("Você não tem permissão!");
		} else {
			UsuarioEntity usuarioLogado = (UsuarioEntity) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (usuarioLogado.getId().compareTo(usuario.getId()) == 0) {
				if (usuarioLogado.getTipo() != usuario.getTipo())
					throw new Exception("Você não tem permissão");
			} else {
				if (usuarioLogado.getTipo() != TipoUsuario.Admin)
					throw new Exception("Você não tem permissão!");
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var user = this.usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return user;
	}

}
