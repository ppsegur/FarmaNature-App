package com.salesianostriana.dam.farma_app.query;


import com.salesianostriana.dam.farma_app.modelo.Producto;

import java.util.List;

public class ProductSpecificationBuilder
  extends GenericSpecificationBuilder<Producto>{

    public ProductSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }
}