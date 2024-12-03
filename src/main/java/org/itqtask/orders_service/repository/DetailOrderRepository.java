package org.itqtask.orders_service.repository;

import org.itqtask.orders_service.model.DetailOrder;
import org.springframework.data.repository.CrudRepository;

public interface DetailOrderRepository extends CrudRepository<DetailOrder, Long> {
}
