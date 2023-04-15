package br.com.leekbiel.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leekbiel.projeto.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    //queries JPQL
}
