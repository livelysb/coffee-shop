package com.sample.coffeeshop.order.application;

import com.sample.coffeeshop.DatabaseCleanUpExecutor;
import com.sample.coffeeshop.common.CoffeeShopErrors;
import com.sample.coffeeshop.common.CoffeeShopException;
import com.sample.coffeeshop.menu.domain.Menu;
import com.sample.coffeeshop.menu.domain.MenuRepository;
import com.sample.coffeeshop.order.domain.Order;
import com.sample.coffeeshop.order.domain.OrderRepository;
import com.sample.coffeeshop.user.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@ActiveProfiles("test")
@SpringBootTest
class OrderCreateTest extends DatabaseCleanUpExecutor {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PointTransactionRepository pointTransactionRepository;

    private Menu savedMenu;
    private User savedUser;

    private User savedUser_insufficientPoint;

    @BeforeEach
    void setUp() {
        savedMenu = menuRepository.save(new Menu("아메리카노", 4200L));
        savedUser = userRepository.save(new User("livelysb", 10000L));
    }

    @DisplayName("주문 성공")
    @Test
    void order_success() {
        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());

        // when
        OrderDto orderDto = Assertions.assertDoesNotThrow(() -> orderService.createOrder(request));

        // then
        User user = userRepository.findByUserId(request.getUserId()).get();
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
        List<PointTransaction> pointTransactionList = pointTransactionRepository.findAllByUserUserId(request.getUserId());

        Assertions.assertEquals(10000L - 4200L, user.getUserPoint());
        Assertions.assertEquals(4200L, order.getOrderPrice());
        Assertions.assertEquals("아메리카노", order.getMenuName());
        Assertions.assertEquals(1, pointTransactionList.size());
        Assertions.assertEquals(TransactionType.PAYMENT, pointTransactionList.get(0).getTransactionType());
        Assertions.assertEquals(4200L, pointTransactionList.get(0).getPoint());

    }

    @DisplayName("동시에 두건의 주문")
    @Test
    void concurrent_2orders() throws ExecutionException, InterruptedException {

        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());


        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request))
        ).join();

        // then
        Long userPoint = userRepository.findByUserId(request.getUserId()).get().getUserPoint();
        Assertions.assertEquals(10000L - 4200L - 4200L, userPoint);
    }

    @DisplayName("동시에 세건의 주문")
    @Test
    void concurrent_3orders() throws ExecutionException, InterruptedException {

        // given
        OrderCreateRequest request = new OrderCreateRequest(savedMenu.getMenuId(), savedUser.getUserId());
        AtomicReference<Throwable> e = new AtomicReference<>();

        // when
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> orderService.createOrder(request)),
                CompletableFuture.runAsync(() -> orderService.createOrder(request))
        ).exceptionally(throwable -> {
            e.set(throwable);
            return null;
        }).join();

        // then
        Assertions.assertNotNull(e.get());
        Assertions.assertTrue(e.get() instanceof CoffeeShopException);
        Assertions.assertEquals(CoffeeShopErrors.INSUFFICIENT_USER_POINT, ((CoffeeShopException) e.get()).getError());

    }

}