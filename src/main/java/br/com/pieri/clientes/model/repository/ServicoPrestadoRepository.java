package br.com.pieri.clientes.model.repository;

import br.com.pieri.clientes.model.entity.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

	@Query("select s from ServicoPrestado s " +
			"JOIN s.cliente c " +
			"where upper(c.nome)  like upper(:nome) " +
			"and MONTH(s.data) = :mes")
	List<ServicoPrestado> find(
			@Param("nome") String nome,
			@Param("mes") Integer mes);
}
