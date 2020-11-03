package br.com.pieri.clientes.rest;

import br.com.pieri.clientes.model.entity.Cliente;
import br.com.pieri.clientes.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private ClienteRepository repository;

	@Autowired
	public ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody @Valid Cliente cliente){
		return repository.save(cliente);
	}

	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Integer id){
		return repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

//	Aqui um jeito diferente de fazer o mapeamento do parametro quando usar nome diferente
//	@GetMapping("{codigo}")
//	public Cliente acharPorId(@PathVariable("codigo") Integer id){
//	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Integer id){
		repository
				.findById(id)
				.map(cliente -> {
					repository.delete(cliente);
					return Void.TYPE;
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//		poderia usar repository.deleteById(id) mas não daria uma informacao adequada caso já não existisse no BD
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado){
		repository
				.findById(id)
				.map(cliente ->{
					clienteAtualizado.setId(cliente.getId());
					return repository.save(clienteAtualizado);
//					é importante colocar um return não nulo ou acaba caindo na implementacao do orElseThrow
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@GetMapping
	public List<Cliente> obterTodos(){
		return repository.findAll();
	}
}
