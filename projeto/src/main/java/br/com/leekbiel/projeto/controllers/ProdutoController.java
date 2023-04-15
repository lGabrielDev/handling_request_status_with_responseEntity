package br.com.leekbiel.projeto.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.leekbiel.projeto.models.Produto;
import br.com.leekbiel.projeto.services.ProdutoService;

@RestController
public class ProdutoController {
    //attributes
    @Autowired
    private ProdutoService ps;


    // ################################################################################  READ  ################################################################################

    // ****************  READ ALL  ****************
    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> produtos =  this.ps.listarProdutos();
        return ResponseEntity.status(HttpStatus.OK).body(produtos); //como é um status "ok", precisamos passar um objeto. Nesse caso, passamos a lista de produtos.
    }
    

    
    // ****************  READ just one product ****************
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> buscarProduto(@PathVariable Long id){ // Perceba que... Esse method retorna um "Object", ou seja, pode retornar objetos de qualquer classe, já que toda class herda de "Object".
        Optional<Produto> pOptional = this.ps.buscarProduto(id); //esse method da service retorna um optional de produto

        if(pOptional.isPresent()){
            Produto p1 = pOptional.get(); // criamos um objeto de fato de "Produto"
            return ResponseEntity.status(HttpStatus.OK).body(p1); // retornamos o objeto procurado e o status ok.
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado. Passe um ID válido!");
        }
    }




    // ################################################################################  CREATE  ################################################################################
    @PostMapping("/produtos")
    public ResponseEntity<Object> cadastrar(@RequestBody Produto p){ // Perceba que... Esse method retorna um "Object", ou seja, pode retornar objetos de qualquer classe, já que toda class herda de "Object".
        Long id = p.getId(); // Criei essa variable só para pegar o id que o usuário passou no request body


        //tratando os erros caso o usuário deixe os campos em branco. O usuário precisa preencher todos os campos.
        if(p.getName() == null || p.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("O campo 'name' precisa ser preenchido."));
        }

        if(p.getDetails() == null || p.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("O campo 'details' precisa ser preenchido."));
        }
        
        if(p.getQuantity() == null || p.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("O campo 'quantity' precisa ser preenchido."));
        }

        if(p.getPrice() == null || p.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("O campo 'price' precisa ser preenchido."));
        }
        
        //verificamos se de fato existe um produto com esse id
        Optional<Produto> pOptional = this.ps.buscarProduto(id); 
        
        
        //se já exisitr um produto com o id informado, enviamos uma mensagem de erro.
        if(pOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não foi possível cadastar pois o id #" + id + " já existe." );
        }
        //se não existir no banco, fazemos o cadastro
        else if(p.getId() == null || pOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(this.ps.cadastrar(p));
        }
        else{
            return ResponseEntity.status(HttpStatus.CREATED).body(this.ps.cadastrar(p));
        }
    }
    
    


    // ################################################################################  UPDATE  ################################################################################
    @PutMapping("/produtos")
    public ResponseEntity<Object> editar(@RequestBody Produto pNovo){
        Long id = pNovo.getId(); // criamos essa variable para receber o "id" informado no request body.
        

        //verificamos se existe um registro com esse id no banco.
        Optional<Produto> pOptional = this.ps.buscarProduto(id);

        //se não existir um registro com o id informado, não dá para editar.
        if(pOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O id #" + id + " não existe.");
        }
        // se existir, aí sim vamos editar o registro
        else{
            Produto pAntigo = pOptional.get(); // Já que o produto existe e não é null, vamos criar de fato um objeto "Produto"

            //se o usuário nao preencher algum campo ou deixá-lo em branco, vamos setar para esse campo o mesmo valor do objeto anterior.
            if(pNovo.getName() == null || pNovo.getName().isBlank()){
                pNovo.setName(pAntigo.getName());
            }

            if(pNovo.getDetails() == null || pNovo.getDetails().isBlank()){
                pNovo.setDetails(pAntigo.getDetails());
            }

            if(pNovo.getPrice() == null){
                pNovo.setPrice(pAntigo.getPrice());
            }

            if(pNovo.getQuantity() == null){
                pNovo.setQuantity(null);
            }

            // Agora que todos os campos foram preenchidos, editamos o registro no banco.
            this.ps.editar(pNovo);
            return ResponseEntity.status(HttpStatus.OK).body(pNovo);
        }
    }
    



    // ################################################################################  DELETE  ################################################################################
    @DeleteMapping("/produtos/{id}") 
    public ResponseEntity<Object> deletar(@PathVariable Long id){ // Perceba que... Esse method retorna um "Object", ou seja, pode retornar objetos de qualquer classe, já que toda class herda de "Object".
        Boolean deletadoComSucesso = this.ps.deletar(id);

        if(deletadoComSucesso){
            return ResponseEntity.status(HttpStatus.OK).body("O registro #" + id + " foi deletado com sucesso");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O registro #" + id + " não existe");
        }
    }
}