package com.project.productProject.controller;

import com.project.productProject.model.Product;
import com.project.productProject.repository.ProductRepository;
import com.project.productProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    ProductRepository productRepository;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Métodos para operaciones CRUD en la API REST

    // Obtener todos los productos
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> product = new ArrayList<>(productService.findAll());

            if (product.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener un producto por ID
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Optional<Product> productData = productService.findById(id);

        return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo producto
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody(required = false) Product product) {
        try {
            Product _product = productService.save(new Product(product.getNombre(), product.getDescripcion(), product.getPrecio()));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un producto por ID
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setNombre(product.getNombre());
            _product.setDescripcion(product.getDescripcion());
            _product.setPrecio(product.getPrecio());
            return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un producto por ID
    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar todos los productos
    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Métodos de búsqueda

    // Buscar productos por nombre
    @GetMapping("/products/nombre/{nombre}")
    public ResponseEntity<List<Product>> findByNombre(@PathVariable("nombre") String nombre) {
        try {
            List<Product> product = productService.findByNombre(nombre);

            if (nombre.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar productos por descripción
    @GetMapping("/products/descripcion/{descripcion}")
    public ResponseEntity<List<Product>> findByDescripcionContaining(@PathVariable("descripcion") String descripcion) {
        try {
            List<Product> product = productService.findByDescripcionContaining(descripcion);

            if (descripcion.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Métodos adicionales para la interfaz de usuario

    // Obtener una vista con la lista de productos
    @GetMapping("/list")
    public ModelAndView sayHello() {
        Product _product = new Product();
        List<Product> product = productService.findAll();
        ModelAndView mav = new ModelAndView("product/list-products");
        mav.addObject("theProduct", product);
        mav.addObject("newProduct", _product);
        return mav;
    }

    // Guardar un nuevo producto desde la interfaz de usuario
    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute("newProduct") Product product) {
        List<Product> newProduct = new ArrayList<>();
        try {
            productService.save(new Product(product.getNombre(), product.getDescripcion(), product.getPrecio()));
            newProduct.addAll(productService.findAll());
        } catch (Exception e) {
            // Log the exception if necessary
        }
        ModelAndView mav = new ModelAndView("product/list-products");
        mav.addObject("theProduct", newProduct);
        return mav;
    }

    // Eliminar un producto desde la interfaz de usuario
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("id") long theId) {
        productService.deleteById(theId);
        List<Product> product = productService.findAll();
        ModelAndView mav = new ModelAndView("product/list-products");
        mav.addObject("newProduct", new Product());
        mav.addObject("theProduct", product);
        return mav;
    }

    // Editar un producto desde la interfaz de usuario
    @GetMapping("/edit")
    public ModelAndView editProduct(@RequestParam("id") long id) {
        Optional<Product> productData = productService.findById(id);

        if (productData.isPresent()) {
            Product product = productData.get();
            ModelAndView mav = new ModelAndView("product/edit-product");
            mav.addObject("productToEdit", product);
            return mav;
        } else {
            // Manejar el caso en que el producto no se encuentra
            // Puedes redirigir a la página principal o mostrar un mensaje de error
            return new ModelAndView("redirect:/api/list");
        }
    }

    // Actualizar un producto por ID desde la interfaz de usuario
    @RequestMapping(value = "/products/update/{id}", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Product> updateProductDetails(
            @PathVariable("id") long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") int precio) {

        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setNombre(nombre);
            _product.setDescripcion(descripcion);
            _product.setPrecio(precio);
            productRepository.save(_product);
        }

        // Redirige a la página de lista de productos después de la actualización.
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create("/api/list"))
                .body(null);
    }

    // Redirigir a la página de lista de productos después de la actualización desde la interfaz de usuario
    @RequestMapping(value = "/products/update/{id}/redirect", method = {RequestMethod.PUT})
    public RedirectView redirectToProductList(@PathVariable("id") long id) {
        // Este método ahora se ejecutará solo para redirecciones después de la actualización.
        return new RedirectView("/api/list", true);
    }
}
