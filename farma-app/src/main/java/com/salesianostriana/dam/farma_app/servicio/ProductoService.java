package com.salesianostriana.dam.farma_app.servicio;

import com.salesianostriana.dam.farma_app.dto.EditProductDto;
import com.salesianostriana.dam.farma_app.dto.GetProductoDto;
import com.salesianostriana.dam.farma_app.error.ProductoNotFoundException;
import com.salesianostriana.dam.farma_app.modelo.Categoria;
import com.salesianostriana.dam.farma_app.modelo.Producto;
import com.salesianostriana.dam.farma_app.query.ProductSpecificationBuilder;
import com.salesianostriana.dam.farma_app.query.SearchCriteria;
import com.salesianostriana.dam.farma_app.repositorio.CategoriaRepo;
import com.salesianostriana.dam.farma_app.repositorio.ProductoRepo;
import com.salesianostriana.dam.farma_app.upload.FileMetadata;
import com.salesianostriana.dam.farma_app.upload.services.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepo repo;
    private final CategoriaRepo categoriaRepo;
    private final StorageService  storageService;

    @Transactional
    public Producto saveproducto(GetProductoDto nuevo, MultipartFile file) {
        FileMetadata fileMetadata = storageService.store(file);


        // Buscar la categoría por nombre
        Categoria categoria = categoriaRepo.findByNombre(nuevo.categoria().nombre());

        // Verificar si la categoría es nula
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría '" + nuevo.categoria().nombre() + "' no existe.");
        }

        // Construir y guardar el producto
        Producto producto =  Producto.builder()
                .nombre(nuevo.nombre())
                .descripcion(nuevo.descripcion())
                .precio(nuevo.precio())
                .stock(nuevo.stock())
                .imagen(fileMetadata.getFilename())
                .fechaPublicacion(nuevo.fechaPublicacion())
                .oferta(nuevo.oferta())
                .build();

                producto.setCategoria(categoria);

                categoria.addProducto(producto);

        categoria.addProducto(producto);
        return repo.save(producto);
    }
/*
    public Page<Producto> findAllProductos(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }
*/
public Page<Producto> findAllProductos(int page, int size, String[] sort) {
    String sortField = sort[0];
    Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
    return repo.findAll(pageable);
}


    public Producto findById(UUID id) {
        Optional<Producto> producto = repo.findById(id);
        if(producto.isEmpty()){
            throw new ProductoNotFoundException("No se han encontrado usuario cone ese id ", HttpStatus.NOT_FOUND);
        }
        return producto.get();
    }

@Transactional
    public Producto edit(EditProductDto dto, UUID id) {
        return repo.findById(id)
                .map(old -> {
                 old.setNombre(dto.nombre());
                 old.setDescripcion(dto.descripcion());
                 old.setPrecio(dto.precio());
                 old.setStock(dto.stock());
                 old.setImagen(dto.imagen());
                 old.setFechaPublicacion(dto.fechaPublicacion());
                 old.setOferta(dto.oferta());
                 old.setCategoria(categoriaRepo.findByNombre(dto.categoria().nombre()));

                 return repo.save(old);

                })
                .orElseThrow(() -> new ProductoNotFoundException("No se encontró la empresa con el id " + id, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void delete(UUID id) {
        Optional<Producto> productoOptional = repo.findById(id);
        if(productoOptional.isEmpty()) {
            throw new ProductoNotFoundException("No se encontró la empresa con el id " + id, HttpStatus.NOT_FOUND);
        }
        Producto p = productoOptional.get();
        Categoria categoria = p.getCategoria();
        if (categoria != null) {
            categoria.removeProducto(p);
        }

        repo.deleteById(id);
    }



    //Filtrado
    public List<GetProductoDto> search(List<SearchCriteria> searchCriteriaList) {
        ProductSpecificationBuilder builder = new ProductSpecificationBuilder(searchCriteriaList);
        Specification<Producto> spec = builder.build();

        List<Producto> productos = repo.findAll(spec);
        //Cambios por la subida de imágenes
        return  productos.stream()
                .map(producto -> GetProductoDto.of(producto, producto.getImagen())) // Usar una lambda
                .collect(Collectors.toList());
    }

    /**
    Busqueda de un listado de productos por categoría

    public List<GetProductoDto> buscarPorCategoria(String nombre){
        List<GetProductoDto> productos = new ArrayList<>();
        if(repo.findByCategoria(nombre)){
            return productos;
        }
        return null;
    }
    **/
    // Busqueda de un listado de productos por categoría
    public List<GetProductoDto> filtrarPorCategoria(String nombreCategoria) {
        List<Producto> productos = repo.findByCategoriaNombre(nombreCategoria);
        return productos.stream()
                .map(producto -> GetProductoDto.of(producto, producto.getImagen()))
                .collect(Collectors.toList());
    }

    //Función para obtener el producto mas vendido 
    
 public Optional<Producto> getProductoMasVendido() {
        // Solo el primero de la lista (más vendido)
        return repo.findProductosMasVendidos(PageRequest.of(0, 1))
                .stream().findFirst();
    }
//   Función para obtener la categoría mas vendida
    public Optional<String> getCategoriaMasVendida() {
    // Solo la primera (más vendida)
    return repo.findCategoriasMasVendidas(PageRequest.of(0, 1))
            .stream()
            .findFirst()
            .map(arr -> (String) arr[0]);
}
//   Función para obtener el producto estrella por categoría
public List<GetProductoDto> getProductoEstrellaPorCategoria() {
    return repo.findProductoEstrellaPorCategoria().stream()
            .map(arr -> {
                Producto producto = (Producto) arr[1];
                return GetProductoDto.of(producto, producto.getImagen());
            })
            .collect(Collectors.toList());
}
}



