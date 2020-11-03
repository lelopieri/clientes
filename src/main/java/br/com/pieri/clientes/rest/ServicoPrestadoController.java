package br.com.pieri.clientes.rest;

import br.com.pieri.clientes.model.entity.Cliente;
import br.com.pieri.clientes.model.entity.ServicoPrestado;
import br.com.pieri.clientes.model.repository.ClienteRepository;
import br.com.pieri.clientes.model.repository.ServicoPrestadoRepository;
import br.com.pieri.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.pieri.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

	private final ClienteRepository clienteRepository;
	private final ServicoPrestadoRepository servicoPrestadoRepository;
	private final BigDecimalConverter bigDecimalConverter;

//	// construtor que inicializa as propriedades final, ou pode-se utilizar uma annotation do Lombok '@RequiredArgsConstructor'
//	public ServicoPrestadoController(ClienteRepository clienteRepository, ServicoPrestadoRepository servicoPrestadoRepository) {
//		this.clienteRepository = clienteRepository;
//		this.servicoPrestadoRepository = servicoPrestadoRepository;
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto){

		LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer idCliente = dto.getIdCliente();
		Cliente cliente =
				clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente Inexistente"));

		ServicoPrestado servicoPrestado = new ServicoPrestado();
		servicoPrestado.setDescricao(dto.getDescricao());
		servicoPrestado.setData(data);
		servicoPrestado.setCliente(cliente);
		servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));

		return servicoPrestadoRepository.save(servicoPrestado);
	}

	@GetMapping
	public List<ServicoPrestado> pesquisar(
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
			@RequestParam(value = "mes", required = false) Integer mes){
		return servicoPrestadoRepository.find("%"+nome+"%", mes);
	}
}
