package com.snack.applications;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
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
    public void  ListarTodosOsProdutosDoRepositorio(){
        productApplication.append(produtoXBurguer);
        productApplication.append(produtoXSalada);
        assertEquals(2,productApplication.getAll().size());

    }
    @Test
    public void   ObterUmProdutoPorIDValido(){
        productApplication.append(produtoXBurguer);
        assertEquals(produtoXBurguer,productApplication.getById(produtoXBurguer.getId()));
    }
    @Test
    public void RetornarErroAoTentarObterProdutoPorIDInvalido() {
        productApplication.append(produtoXBurguer);
        assertThrows(NoSuchElementException.class, () -> {
            productApplication.getById(9);
        });
    }
    @Test
    public void VerificarSeUmProdutoExistePorIDValido(){
        productApplication.append(produtoXBurguer);
        List<Product> produtos = productApplication.getAll();
        productApplication.exists(produtoXBurguer.getId());
        assertEquals(1,produtos.size());
    }
    @Test
    public void RetornarFalsoAoVerificaraExistenciaDeUmProdutoComIDInvalido(){
        productApplication.append(produtoXBurguer);
        List<Product> produtos = productApplication.getAll();
        assertFalse(produtos.contains(produtoXSalada));
    }
    @Test
    public void AdicionarUmNovoProdutoeSalvarSuaImagemCorretamente(){

        produtoXBurguer = new Product(1, "x-burguer", 10, "C:\\Users\\DELL\\Pictures\\1.jpg");
        productApplication.append(produtoXBurguer);
        String caminhoDaImagemSalva = produtoXBurguer.getImage();
        Path caminho = Paths.get(caminhoDaImagemSalva);
        Assertions.assertTrue(Files.exists(caminho));


    }
    @Test
    public void RemoverUmProdutoExistenteeDeletarSuaImagem(){
        productApplication.append(produtoXBurguer);
        productApplication.remove(produtoXBurguer.getId());
        Path imagemPath = Paths.get("C:\\Users\\DELL\\Pictures\\1.jpg"
                + produtoXBurguer.getId() + ".jpg");
        Assertions.assertFalse(Files.exists(imagemPath), "A imagem ainda existe após remoção.");

    }
@Test
    public void NaoAlteraroSistemaAoTentarRemoverUmProdutoComIDInexistente(){
        productApplication.append(produtoXBurguer);
            productApplication.remove(1);
            assertEquals(0,productApplication.getAll().size());

    }
    @Test
    public void AtualizarUmProdutoExistenteESubstituirSuaImagem() {
        productApplication.append(produtoXBurguer);
        productApplication.update(1, produtoXSalada);
        Product produtoAtualizado = productApplication.getById(1);
        Assertions.assertEquals(produtoXSalada.getDescription(), produtoAtualizado.getDescription());
        Assertions.assertEquals(produtoXSalada.getPrice(), produtoAtualizado.getPrice());

    }


}

