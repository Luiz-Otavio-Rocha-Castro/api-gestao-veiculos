package com.alugel.api_gestao_veiculos.modules.aluguel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AluguelRepository extends JpaRepository<Aluguel, Long> {

    List<Aluguel> findByVeiculoId(Long veiculoId);

    List<Aluguel> findByClienteId(Long clienteId);
}
