package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.Category;
import co.kr.pcninc.data.marketcore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories() { return categoryRepository.findAll(); }

    public Optional<Category> getCategory(int catId)  { return categoryRepository.findById(catId); }

    public void createCategory(Category category) { categoryRepository.save(category); }

    public void deleteCategory(int catId) { categoryRepository.deleteById(catId); }
}
