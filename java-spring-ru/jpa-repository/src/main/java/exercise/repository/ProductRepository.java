package exercise.repository;

import exercise.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN

    List<Product> findByPriceBetween(int startPrice, int endPrice, Sort price);

    List<Product> findByPriceGreaterThan(int price, Sort by);

    List<Product> findByPriceIsLessThan(int price, Sort by);
    // END
}
