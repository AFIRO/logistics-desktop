package be.zetta.logisticsdesktop.domain.order.repository;

import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, String> {

    Optional<List<CustomerOrder>> findCustomerOrdersByCustomerNameIgnoreCaseContaining(String name);

    Optional<List<CustomerOrder>> findCustomerOrderByStatus(OrderStatus status);

    Optional<CustomerOrder> findCustomerOrderByTrackTraceCode(String code);

    List<CustomerOrder> findCustomerOrdersByPurchaser_PersonId(String personId);

}
