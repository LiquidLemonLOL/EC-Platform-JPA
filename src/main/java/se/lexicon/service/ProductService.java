package se.lexicon.service;

import se.lexicon.dto.ProductRequest;
import se.lexicon.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    List<ProductResponse> findAll();

    List<ProductResponse> searchByName(String name);

}
