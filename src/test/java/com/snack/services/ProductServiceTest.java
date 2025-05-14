package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ProductServiceTest {
    ProductService productService;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        produtoXBurguer = new Product(50, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        produtoXSalada = new Product(50, "x-salada", 15, "C:\\Users\\DELL\\Pictures\\2.jpg");
        productService = new ProductService();
    }
    @Test
    public void SalvarUmProdutoComImagemValida() {
        boolean sucesso = productService.save(produtoXBurguer);
        Assertions.assertEquals("x-salada", produtoXSalada.getDescription());
        Assertions.assertEquals(15, produtoXSalada.getPrice());
        Assertions.assertTrue(sucesso);

    }
    @Test
    public void SalvarUmProdutoComImagemInexistente(){
        produtoXBurguer = new Product(51, "x-burguer", 10, "C:\\Users\\aluno.fsa\\Pictures\\IMG\\21.jpg");
        productService.save(produtoXBurguer);
        Assertions.assertFalse(false);
    }

    @Test
    public void atualizarUmProdutoExistente() {
        productService.save(produtoXBurguer);
        productService.update(produtoXSalada);
        Assertions.assertEquals("x-salada", produtoXSalada.getDescription());
        Assertions.assertEquals(15, produtoXSalada.getPrice());
        Path path = Paths.get(produtoXSalada.getImage());
        Assertions.assertTrue(Files.exists(path));
    }
    @Test
    public void RemoverUmProdutoExistente(){
        productService.save(produtoXBurguer);
        List<Product> todosProdutos = productService.remove(1);
        Assertions.assertFalse(false);
    }
    @Test
    public void ObterCaminhoDaImagemPorID(){
        productService.save(produtoXBurguer);
        Path path = Paths.get(produtoXBurguer.getImage());
        Assertions.assertEquals("50.jpg",path.toFile().getName());
    }

}
