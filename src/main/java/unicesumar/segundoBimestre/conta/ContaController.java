package unicesumar.segundoBimestre.conta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mySpringBootApp.livro.Livro;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
	
	@Autowired
	private ContaService service;
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Conta> getAll(){
		return service.getAll();
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/{id}")
	public Conta getById(@PathVariable("id") String id) {
		Conta conta = service.getById(id);
		return conta;
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public String post(@RequestBody Conta nova) {
		return service.save(nova);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") String id) {
		service.deleteById(id);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping("/{id}")
	public void put(@PathVariable("id") String id, @RequestBody Conta modificada) {
		if (!id.equals(modificada.getId())) {
			throw new RuntimeException("Id do recurso não confere com Id do body!");
		}
		service.save(modificada);
	}
}
