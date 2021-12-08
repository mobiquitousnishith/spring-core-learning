package com.nishith.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.models.Brand;
import com.nishith.repository.BrandRepository;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Optional<Brand> getBrandByName(String brandName) {
        return brandRepository.findByName(brandName);
    }

    public Brand addBrand(Brand brand) {
        return brandRepository.addBrand(brand);
    }

    public void addBrands(Collection<Brand> brands) {
        brandRepository.addBrands(brands);
    }

    public List<Brand> getBrandsByName(List<String> brandNames) {
        return brandRepository.findBrands(brandNames);
    }
}
