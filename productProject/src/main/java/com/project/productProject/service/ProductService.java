package com.project.productProject.service;

import com.project.productProject.model.Product;
import com.project.productProject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /**
     * Actualiza un producto por ID y lo guarda en la base de datos.
     *
     * @param id Identificador del producto a actualizar.
     * @return El producto actualizado.
     */
    public Product update(long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product product = productData.get();

            return productRepository.save(product);
        }
        return null;
    }

    /**
     * Obtiene todos los productos de la base de datos.
     *
     * @return Lista de todos los productos.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Elimina un producto por ID de la base de datos.
     *
     * @param theId Identificador del producto a eliminar.
     */
    public void deleteById(long theId) {
        productRepository.deleteById(theId);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param product Producto a guardar.
     * @return El producto guardado.
     */
    public Product save(Product product) {

        return productRepository.save(new Product(product.getNombre(), product.getDescripcion(), product.getPrecio()));
    }

    /**
     * Busca productos por nombre en la base de datos.
     *
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos que coinciden con el nombre.
     */
    public List<Product> findByNombre(String nombre) {
        return productRepository.findByNombre(nombre);
    }

    /**
     * Elimina todos los productos de la base de datos.
     */
    public void deleteAll() {
        productRepository.deleteAll();
    }

    /**
     * Obtiene un producto por ID de la base de datos.
     *
     * @param id Identificador del producto.
     * @return El producto encontrado, o null si no existe.
     */
    public Optional<Product> findById(long id) {

        return productRepository.findById(id);
    }

    /**
     * Obtiene un producto por descripci{on} de la base de datos.
     *
     * @param descripcion Descripci{on} del producto.
     * @return El producto encontrado, o null si no existe.
     */
    public List<Product> findByDescripcionContaining(String descripcion) {

        return productRepository.findByDescripcionContaining(descripcion);
    }
}
