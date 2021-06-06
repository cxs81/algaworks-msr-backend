package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
//        return clienteRepository.save(cliente);
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
//        cliente = clienteRepository.save(cliente);
        catalogoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            return ResponseEntity.notFound().build();
        }

//        clienteRepository.deleteById(clienteId);
        catalogoClienteService.excluir(clienteId);

        return ResponseEntity.noContent().build();
    }
}


// ************************************************************************************************************
// ************************************************************************************************************
//                                          Códigos comparativos
// ************************************************************************************************************
// ************************************************************************************************************

//    @Autowired
//    private ClienteRepository clienteRepository;

//    @PersistenceContext
//    private EntityManager manager;
//        return manager.createQuery("from Cliente", Cliente.class).getResultList();

//    @GetMapping("/clientes")
//    public List<Cliente> listar() {
//        var cliente1 = new Cliente();
//        cliente1.setId(1L);
//        cliente1.setNome("João0");
//        cliente1.setEmail("1111-1111");
//        cliente1.setTelefone("joao@teste.com");
//
//        var cliente2 = new Cliente();
//        cliente2.setId(2L);
//        cliente2.setNome("Maria45");
//        cliente2.setEmail("1111-2222");
//        cliente2.setTelefone("maria@teste.com");
//
//        return Arrays.asList(cliente1, cliente2);
//    }


// ************************************************************************************************************
// ************************************************************************************************************
//                              Forma de implementar uma busca no repositório
// ************************************************************************************************************
// ************************************************************************************************************

// Exemplo - Forma 1: Quando não existe volta nulo com Status=200 OK
//
//    @GetMapping("/clientes/{clienteId}")
//    public Cliente buscar(@PathVariable Long clienteId) {
//        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
//        return cliente.orElse(null);
//    }


// Exemplo - Forma 2: Quando não existe volta nulo com Status=404 Not Found (indicado em melhores práticas)
//
//    @GetMapping("/clientes/{clienteId}")
//    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
//        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
//        if (cliente.isPresent()) {
//            return ResponseEntity.ok(cliente.get());
//        }
//        return ResponseEntity.notFound().build();
//    }


// Exemplo - Forma 3: Código reduzido com programação funcional e expressão lambda ou method reference
//
//    @GetMapping("/clientes/{clienteId}")
//    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
//        return clienteRepository.findById(clienteId)
////                .map(cliente -> ResponseEntity.ok(cliente))
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

