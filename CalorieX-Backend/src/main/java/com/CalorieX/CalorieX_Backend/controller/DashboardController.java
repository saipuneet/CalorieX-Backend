package com.CalorieX.CalorieX_Backend.controller;

import com.CalorieX.CalorieX_Backend.dto.DashboardResponses;
import com.CalorieX.CalorieX_Backend.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

        private final DashboardService dashboardService;

        public DashboardController(DashboardService dashboardService) {
            this.dashboardService = dashboardService;
        }

        @GetMapping("/dashboard")
        public DashboardResponses getDashboard() {

            return dashboardService.getDashboard();






        }
    }
