//package org.itqtask.orders_service.repository;
//
//import org.itqtask.orders_service.model.DetailOrder;
//import org.itqtask.orders_service.model.Order;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
//import org.springframework.dao.IncorrectResultSizeDataAccessException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.core.BatchPreparedStatementSetter;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.client.RestClient;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.time.Clock;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class JdbcOrderRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//
//    @Override
//    public Order createOrder(Order order) {
//        try {
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            KeyHolder keyHolder2 = new GeneratedKeyHolder();
//            order.setOrderDate(Date.valueOf(LocalDate.now()));
//            order.setTotalAmount(order.getDetailOrders().stream().mapToInt(detail -> detail.getProductPrice() * detail.getProductAmount()).sum());
//            order.setOrderNo(getOrderNumber());
//
//            String sqlForOrder = "INSERT INTO orders(" +
//                    "order_number, total_amount, customer_name, address, delivery_type, payment_type, order_date) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
//            String sqlForDetailOfOrder = "INSERT INTO Products(product_article, product_name, product_amount, product_price, order_id) VALUES (?, ?, ?, ?, ?)";
//
//            jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection
//                        .prepareStatement(sqlForOrder, new String[]{"id"});
//                ps.setString(1, order.getOrderNo());
//                ps.setLong(2, order.getTotalAmount());
//                ps.setString(3, order.getCustomerName());
//                ps.setString(4, order.getDeliveryAddress());
//                ps.setString(5, order.getDeliveryType());
//                ps.setString(6, order.getPaymentType());
//                ps.setDate(7, order.getOrderDate());
//                return ps;
//            }, keyHolder);
//            int generatedId = keyHolder.getKey().intValue();
//            System.out.println(generatedId);
//            order.setId(generatedId);
//            order.getDetailOrders().forEach(detailOrder -> detailOrder.setOrderId(generatedId));
////            order.getDetailOrders().forEach(detailOrder -> jdbcTemplate.update(sqlForDetailOfOrder,
////                    detailOrder.getProductArticle(),
////                    detailOrder.getProductName(),
////                    detailOrder.getProductAmount(),
////                    detailOrder.getProductPrice(),
////                    generatedId));
//
////            jdbcTemplate.update(connection -> {
////                PreparedStatement ps = connection
////                        .prepareStatement(sqlForDetailOfOrder, new String[] {"id"});
////                ps.setLong(1, detailOrder.getProductArticle());
////                ps.setString(2, detailOrder.getProductName());
////                ps.setInt(3, detailOrder.getProductAmount());
////                ps.setInt(4, detailOrder.getProductPrice());
////                ps.setLong(5, order.getId());
////                return ps;
////            }, keyHolder2);
//            jdbcTemplate.batchUpdate(sqlForDetailOfOrder,
//                    new BatchPreparedStatementSetter() {
//                        @Override
//                        public void setValues(PreparedStatement ps, int i) throws SQLException {
//                            DetailOrder detailOrder = order.getDetailOrders().get(i);
//                            ps.setLong(1, detailOrder.getProductArticle());
//                            ps.setString(2, detailOrder.getProductName());
//                            ps.setInt(3, detailOrder.getProductAmount());
//                            ps.setInt(4, detailOrder.getProductPrice());
//                            ps.setInt(5, detailOrder.getOrderId());
//                        }
//
//                        @Override
//                        public int getBatchSize() {
//                            return order.getDetailOrders().size();
//                        }
//                    });
//            return order;
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//
//    }
//
//    @Override
//    public Order findById(Long id) {
//        try {
//            Order order = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE id=?",
//                    new BeanPropertyRowMapper<>(Order.class), id);
//            return order;
//        } catch (IncorrectResultSizeDataAccessException e) {
//            return null;
//        }
//    }
//
//    @Override
//    public List<Order> findByDateAndSumMoreThanTotalAmount(Date date, int totalAmount) {
//        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE order_date=? AND total_amount > ?",
//                new BeanPropertyRowMapper<>(Order.class), date, totalAmount);
//        orders.forEach(order -> order.setDetailOrders(getDetailsForOrder(order.getId())));
//
//        return orders;
//    }
//
//    @Override
//    public List<Order> findByExcludeProductAndTimeInterval(long article, Date dateFrom, Date dateTo) {
////        List<Order> orders = jdbcTemplate.query("SELECT * FROM orders WHERE order_date > ? AND order_date < ? ORDER BY id",
////                new BeanPropertyRowMapper<>(Order.class), dateFrom, dateTo);
////        List<DetailOrder> allDetailsOrder = jdbcTemplate.query("SELECT * FROM products WHERE NOT product_article=? ORDER BY order_id",
////                new BeanPropertyRowMapper<>(DetailOrder.class), article);
////        List<Order> filteredOrders = excludeDetailAndCombineOrders(orders, allDetailsOrder)
//        return List.of();
//    }
//
//    public String getOrderNumber() {
//        RestClient restClient = RestClient.create();
//        return restClient.get().uri("http://localhost:3001/numbers").retrieve().body(String.class);
//    }
//
////    private List<Order> excludeDetailAndCombineOrders(List<Order> orders, List<DetailOrder> allDetailsOrder) {
////
////    }
//
//    public List<DetailOrder> getDetailsForOrder(long id) {
//        return jdbcTemplate.query("SELECT * FROM products WHERE order_id=?",
//                new BeanPropertyRowMapper<>(DetailOrder.class), id);
//    }
//}
