package com.ssafy.snuggle.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.snuggle.model.dao.CategoryDao;
import com.ssafy.snuggle.model.dto.Category;
import com.ssafy.snuggle.model.dto.Product;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao cDao;
	
	@Override
	public Category select(int cId) {
		return cDao.select(cId);
	}

	@Override
	public List<Product> selectProductsByCategory(int cId) {
		return cDao.selectProductsByCategory(cId);
	}

	@Override
	public List<Category> selectAll() {

		return cDao.selectAll();
	}

}
