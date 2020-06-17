package unicesumar.segundoBimestre.conta;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mySpringBootApp.livro.Livro;
import mySpringBootApp.livro.NotFoundException;

@Service
@Transactional
public class ContaService {

	@Autowired
	private ContaRepository repo;
	
	public void deleteById(String id) {
		repo.deleteById(id);
	}

	public Conta getById(String id) { 
		return repo.findById(id).orElseThrow(NotFoundException::new);
	}
	
	public List<Conta> getAll() {
		return repo.findAll();
	}
	
	public String save(Conta conta) {
		return this.repo.save(conta).getId();
	}
	
}
