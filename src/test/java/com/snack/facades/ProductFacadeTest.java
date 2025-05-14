package com.snack.facades;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductFacadeTest {
    ProductFacade productFacade;
    Product produtoXBurguer;
    Product produtoXSalada;

    @BeforeEach
    public void setUp() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService();
        ProductApplication productApplication = new ProductApplication(productRepository, productService);
        productFacade = new ProductFacade(productApplication);

        produtoXBurguer = new Product(10, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        produtoXSalada = new Product(20, "x-salada", 15, "C:\\Users\\DELL\\Pictures\\2.jpg");
    }

    @Test
    public void testarListaCompletaProdutosChamarMetodoGetAll() {
        productFacade.append(produtoXBurguer);
        productFacade.append(produtoXSalada);
        List<Product> produtos = productFacade.getAll();

        // Assert
        Assertions.assertEquals(2, produtos.size());
    }
    @Test
    public void  RetornarOProdutoCorretoAoFornecerUmIDValidoNoMetodoGetById(){

        productFacade.append(produtoXBurguer);
        Product produto = productFacade.getById(10);

        Assertions.assertTrue(produto != null);
    }
    @Test
    public void RetornarTrueParaUmIDExistenteeFalseParaUmIDInexistenteNoMetodoExists(){
        productFacade.append(produtoXBurguer);
        Product produto = productFacade.getById(10);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Product produtos = productFacade.getById(555);
        });
        Assertions.assertTrue(produto != null);


    }
    @Test
    public void AdicionarUmNovoProdutoCorretamenteaoChamaroMetodoAppend(){
        productFacade.append(produtoXBurguer);

        Assertions.assertEquals(10,produtoXBurguer.getId());
    }
    @Test
    public void RemoverUmProdutoExistenteAoFornecerUmIDValidoNoMetodoRemove(){
        productFacade.append(produtoXBurguer);
        productFacade.remove(produtoXBurguer.getId());
        Assertions.assertFalse(productFacade.exists(produtoXBurguer.getId()));
    }

}
