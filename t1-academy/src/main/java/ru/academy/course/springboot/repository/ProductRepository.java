package ru.academy.course.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.academy.course.springboot.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
