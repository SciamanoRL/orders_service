package org.itqtask.orders_service.services;

import org.itqtask.orders_service.model.DetailOrder;
import org.itqtask.orders_service.model.Order;
import org.itqtask.orders_service.repository.OrderRepository;
import org.itqtask.orders_service.utils.ProductsList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    NumberGeneratorServiceImpl numberGeneratorService;

    @InjectMocks
    OrderServiceImpl orderService;

    @DisplayName("Сохранение нового заказа с деталями")
    @Test
    public void createAndReturnOrderWithDetails() {
        orderService = new OrderServiceImpl(orderRepository, numberGeneratorService, new ProductsList());
        Date date = Date.valueOf(LocalDate.now());
        String generatedNumber = "9335820241209";
        when(numberGeneratorService.getGeneratedOrderNumber()).thenReturn(generatedNumber);
        when(orderRepository.save(any())).then(returnsFirstArg());
        Order order = new Order(1L,
                "",
                0L,
                Date.valueOf(LocalDate.now()),
                "Mike Petrov",
                "Moscow",
                "Наличные",
                "Самовывоз",
                Set.of(new DetailOrder(null,
                                9100322L,
                                null,
                                4,
                                0,
                                null),
                        new DetailOrder(null,
                                7670154L,
                                null,
                                7,
                                0,
                                null)));


        Order savedOrder = orderService.createOrder(order);
        Assertions.assertNotNull(savedOrder);
        savedOrder.getDetailOrders().forEach(detailOrder -> Assertions.assertNotNull(detailOrder.getProductName()));
        savedOrder.getDetailOrders().forEach(detailOrder -> Assertions.assertNotEquals(0, detailOrder.getProductPrice()));
        Assertions.assertEquals(52400L, savedOrder.getTotalAmount());
        Assertions.assertEquals(date, savedOrder.getOrderDate());
        Assertions.assertEquals(generatedNumber, savedOrder.getOrderNumber());
    }

    @DisplayName("Тест получения всех заказов")
    @Test
    public void OrderService_findAllTest() {
        when(orderRepository.findAll()).thenReturn(getOrders());

        List<Order> orderList = orderService.findAll();

        Assertions.assertNotNull(orderList);
        Assertions.assertEquals(5, orderList.size());
        Assertions.assertEquals("Сахаров Тимофей", orderList.get(1).getCustomerName());
    }

    @DisplayName("Тест получения заказа по ID")
    @Test
    public void OrderService_getOrderByIdTest() {
        Long id = 4L;
        when(orderRepository.findById(id)).thenReturn(getOrders().stream().filter(order -> Objects.equals(order.getId(),id)).findFirst());

        Optional<Order> orderById = orderService.getOrder(id);

        Assertions.assertNotNull(orderById);
        Assertions.assertEquals(19700L,
                orderById.get().getTotalAmount());
        Assertions.assertEquals(4L, orderById.get().getId());
    }

    @DisplayName("Тест получения всех заказов по дате и общая сумма выше указанного значения")
    @Test
    public void OrderService_findByOrderDateAndTotalAmountGreaterThanTest() {
        Long totalAmount = 15000L;
        Date date = Date.valueOf("2024-10-01");
        when(orderRepository.findByOrderDateAndTotalAmountGreaterThan(date, totalAmount)).thenReturn(getOrders()
                .stream()
                .filter(order -> date.equals(order.getOrderDate()))
                .filter(order -> totalAmount < order.getTotalAmount()).toList());

        List<Order> orderByDateAndTotalAmountGreaterThan = orderService.findByOrderDateAndTotalAmountGreaterThan(date, totalAmount);

        Assertions.assertNotNull(orderByDateAndTotalAmountGreaterThan);
        Assertions.assertEquals(1, orderByDateAndTotalAmountGreaterThan.size());
        Assertions.assertEquals(38400L, orderByDateAndTotalAmountGreaterThan.get(0).getDetailOrders()
                .stream()
                .mapToLong(detail -> detail.getProductAmount() * detail.getProductPrice()).sum());
    }

    @DisplayName("Тест получения всех заказов в диапазоне дат, исключив определенный товар")
    @Test
    public void OrderService_findOrdersByOrderDateBetweenTest() {
        Long article = 7670154L;
        Date dateFrom = Date.valueOf("2024-09-01");
        Date dateTo = Date.valueOf("2024-10-02");
        when(orderRepository.findOrdersByOrderDateBetween(dateFrom, dateTo)).thenReturn(getOrders()
                .stream()
                .filter(order -> dateFrom.before(order.getOrderDate()))
                .filter(order -> dateTo.after(order.getOrderDate())).toList());

        List<Order> ordersByOrderDateBetween = orderService.findOrdersByOrderDateBetween(dateFrom, dateTo, article);

        Assertions.assertNotNull(ordersByOrderDateBetween);
        Assertions.assertEquals(1, ordersByOrderDateBetween.size());
        Assertions.assertEquals(2, ordersByOrderDateBetween.get(0).getDetailOrders().size());
        Assertions.assertEquals("2938420241209", ordersByOrderDateBetween.get(0).getOrderNumber());
    }



    private List<Order> getOrders() {
        Order order1 = new Order(1L,
                "9277020241209",
                12200L,
                Date.valueOf("2024-08-12"),
                "Акимов Леонид",
                "г. Москва, Дружная ул., д. 1 кв.2",
                "Наличные",
                "Самовывоз",
                Set.of(new DetailOrder(1L,
                                9100322L,
                                "Зеркало 100х80мм",
                                2,
                                3300,
                                1L),
                        new DetailOrder(2L,
                                7670154L,
                                "Раковина 80х45мм",
                                1,
                                5600,
                                1L)));

        Order order2 = new Order(2L,
                "3800820241209",
                38400L,
                Date.valueOf("2024-10-01"),
                "Сахаров Тимофей",
                "г. Великий Новгород, Лесной пер., д. 3 кв.33",
                "Наличные",
                "Доставка до двери",
                Set.of(new DetailOrder(3L,
                                9100322L,
                                "Зеркало 100х80мм",
                                2,
                                3300,
                                2L),
                        new DetailOrder(4L,
                                7670154L,
                                "Раковина 80х45мм",
                                2,
                                4700,
                                2L),
                        new DetailOrder(5L,
                                1958148L,
                                "Смеситель цвет хром",
                                4,
                                5600,
                                2L)));

        Order order3 = new Order(3L,
                "2938420241209",
                14500L,
                Date.valueOf("2024-10-01"),
                "Ильина Виктория",
                "г. Москва, Первомайский пер., д. 22 кв.190",
                "Карта",
                "Доставка до двери",
                Set.of(new DetailOrder(6L,
                                9100322L,
                                "Зеркало 100х80мм",
                                1,
                                3300,
                                3L),
                        new DetailOrder(7L,
                                1958148L,
                                "Смеситель цвет хром",
                                2,
                                5600,
                                3L)));

        Order order4 = new Order(4L,
                "1557120241209",
                19700L,
                Date.valueOf("2024-09-03"),
                "Афанасьева Дарья",
                "г. Москва, Строителей ул., д. 12 кв.34",
                "Наличные",
                "Самовывоз",
                Set.of(new DetailOrder(8L,
                                7670154L,
                                "Раковина 80х45мм",
                                3,
                                4700,
                                4L),
                        new DetailOrder(9L,
                                1958148L,
                                "Смеситель цвет хром",
                                1,
                                5600,
                                4L)));

        Order order5 = new Order(5L,
                "6916920241209",
                21100L,
                Date.valueOf("2024-10-15"),
                "Титов Константин",
                "г. Новокуйбышевск, Советская ул., д. 9 кв.120",
                "Карта",
                "Доставка до двери",
                Set.of(new DetailOrder(10L,
                                9100322L,
                                "Зеркало 100х80мм",
                                3,
                                3300,
                                5L),
                        new DetailOrder(11L,
                                1958148L,
                                "Смеситель цвет хром",
                                2,
                                5600,
                                5L)));
        return List.of(order1, order2, order3, order4, order5);
    }
}
