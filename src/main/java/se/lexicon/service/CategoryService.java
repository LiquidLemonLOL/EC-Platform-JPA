package se.lexicon.service;

import se.lexicon.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(String name);

    List<CategoryResponse> findAll();

}
