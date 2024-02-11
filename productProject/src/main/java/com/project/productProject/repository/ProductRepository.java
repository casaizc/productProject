package com.project.productProject.repository;

import java.util.List;

import com.project.productProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca productos por nombre.
     *
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos encontrados.
     */
    List<Product> findByNombre(String nombre);

    /**
     * Busca productos por descripción que contiene una cadena específica.
     *
     * @param descripcion Cadena a buscar dentro de las descripciones de los productos.
     * @return Lista de productos encontrados.
     */
    List<Product> findByDescripcionContaining(String descripcion);
}
