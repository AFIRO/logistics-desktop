package be.zetta.logisticsdesktop.domain.product.repository;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName(String name);
}
