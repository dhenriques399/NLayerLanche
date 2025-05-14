package com.snack.repositories;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ProductApplicationTest {
    ProductApplication productApplication;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();

        produtoXBurguer = new Product(1, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        produtoXSalada = new Product(2, "x-salada", 15, "C:\\Users\\DELL\\Pictures\\2.jpg");

        productApplication = new ProductApplication(productRepository, productService);
    }
    @Test
    public void VerificarAdicaoProdutoCorretamente() {
        //Arrange
        produtoXBurguer = new Product(1, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        productApplication.append(produtoXBurguer);
        boolean existe = productApplication.exists(1);
        assertEquals(true,existe);
    }

    @Test
    public void VerificarSeEPossivelRecuperarProdutoUsandoID(){
        productApplication.append(produtoXBurguer);
        produtoXBurguer.getId();
        assertEquals(1,produtoXBurguer.getId());

    }
    @Test
    public void ConfirmarExistenciaDoProdutoDentroDoRepositorio() {
        productApplication.append(produtoXBurguer);
        assertEquals(1,produtoXBurguer.getId());
    }
    @Test
    public void TestarSeUmProdutoERemovidoCorretamenteDoRepositorio(){
        productApplication.append(produtoXBurguer);
        productApplication.remove(produtoXBurguer.getId());
        Assertions.assertThrows(NoSuchElementException.class,()->{
            productApplication.getById(produtoXBurguer.getId());
        });

    }

    @Test
    public void  VerificarSeUmProdutoEatualizadoCorretamente(){
        productApplication.append(produtoXBurguer);
        Product novo = new Product(1, "x-salada", 15,"C:\\Users\\DELL\\Pictures\\2.jpg");
        productApplication.update(1,novo);
        Product produtoDoRepositorio = productApplication.getById(1);
        assertEquals("x-salada", produtoDoRepositorio.getDescription());
        assertEquals(15, produtoDoRepositorio.getPrice());
    }

    @Test
    public void TestarSeTodosOsProdutosArmazenadosSaoRecuperadosCorretamente(){
        productApplication.append(produtoXBurguer);
        List<Product> todosProdutos = productApplication.getAll();
        assertEquals(1,todosProdutos.size());
    }
    @Test
    public void tentarRemoverProdutoIdInexistente() {
        productApplication.append(produtoXBurguer);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productApplication.remove(3);
        });
    }

    @Test
    public void tentarRemoverProdutoExistente() {
        Product produtoXBurguerZ = new Product(100, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        productApplication.append(produtoXBurguerZ);
        productApplication.remove(100);
        Path path = Paths.get(produtoXBurguerZ.getImage());
        Assertions.assertFalse(Files.exists(path));
    }
    @Test
    public void VerificarSeoRepositorioAceitaaAdicaoDeProdutosComIDsDuplicados(){
        Product produtoXBurguerZ = new Product(10, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        Product produtoXSaladaZ = new Product(10,"x-salada",12,"C:\\Users\\DELL\\Pictures\\2.jpg");
        productApplication.append(produtoXBurguerZ);
        productApplication.append(produtoXSaladaZ);
        List<Product> todosProdutos = productApplication.getAll();
        assertTrue(todosProdutos.contains(produtoXBurguerZ));
        assertTrue(todosProdutos.contains(produtoXSaladaZ));
    }
    @Test
    public void  ConfirmarQueorepositorioRetornaUmaListaVaziaaoSerInicializado(){
        List<Product> todosProdutos = productApplication.getAll();
        assertEquals(0,todosProdutos.size());
    }

}
