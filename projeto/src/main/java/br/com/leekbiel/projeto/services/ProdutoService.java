package br.com.leekbiel.projeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leekbiel.projeto.models.Produto;
import br.com.leekbiel.projeto.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    //attributes
    @Autowired
    private ProdutoRepository pr;


    // ################################################################################  READ  ################################################################################

    // ****************  READ de todos os produtos  ****************
    public List<Produto> listarProdutos() {
        List<Produto> produtos = this.pr.findAll();
        return produtos;
    }


    // ****************  READ de apenas 1 registro ****************
    public Optional<Produto> buscarProduto(Long id) {
        Optional<Produto> pOptional = this.pr.findById(id);
        return pOptional;

    }




    // ################################################################################  CREATE  ################################################################################
    public Produto cadastrar(Produto p) {
        this.pr.save(p); // cadastrarmos no banco de dados
        return p;
    }




    // ################################################################################  UPDATE  ################################################################################
    public Produto editar(Produto pNovo) {
        this.pr.save(pNovo);
        return pNovo;
    }




    // ################################################################################  DELETE  ################################################################################
    public Boolean deletar(Long id) {
        Optional<Produto> pOptional = this.pr.findById(id);

        if(pOptional.isPresent()){
            this.pr.deleteById(id); // deletamos no banco
            return true;
        }
        else{
            return false;
        }
    }
}
