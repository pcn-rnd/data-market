package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.Category;
import co.kr.pcninc.data.marketcore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories() { return categoryRepository.findAll(); }
}
