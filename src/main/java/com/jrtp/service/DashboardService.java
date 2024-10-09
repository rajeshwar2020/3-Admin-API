package com.jrtp.service;

import com.jrtp.dto.DashboardDTO;
import com.jrtp.entities.Order;
import com.jrtp.entities.Product;
import com.jrtp.repos.CustomerRepo;
import com.jrtp.repos.OrderRepo;
import com.jrtp.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;


    public DashboardDTO fetchDashboardValues() {
        DashboardDTO dashboardDTO = new DashboardDTO();

        populateOrderMetrics(dashboardDTO);
        populateCustomerCount(dashboardDTO);
        populateProductMetrics(dashboardDTO);

        return dashboardDTO;
    }

    private void populateOrderMetrics(DashboardDTO dashboardDTO) {
        List<Order> orderList = orderRepo.findAll();

        double totalAmount = orderList.stream()
                .filter(order -> "PAID".equalsIgnoreCase(order.getOrderStatus()))
                .mapToDouble(Order::getTotalPrice)
                .sum();

        long totalOrderCount = orderList.stream()
                .filter(order -> "PAID".equalsIgnoreCase(order.getOrderStatus()))
                .count();

        dashboardDTO.setAmountCollected(totalAmount);
        dashboardDTO.setOrdersCount(totalOrderCount);
    }


    private void populateCustomerCount(DashboardDTO dashboardDTO) {
        long totalCustomerCount = customerRepo.count();
        dashboardDTO.setCustomersCount(totalCustomerCount);
    }

    private void populateProductMetrics(DashboardDTO dashboardDTO) {
        List<Product> productList = productRepo.findAll();

        long productCountInStock = productList.stream()
                .filter(product -> product.getUnitsInStock() > 0)
                .count();
        dashboardDTO.setProductCount(productCountInStock);
    }

}
