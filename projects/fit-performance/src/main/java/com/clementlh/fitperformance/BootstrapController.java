package com.clementlh.fitperformance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BootstrapController {

    @GetMapping("/api/v1/ping")
    String ping() {
        return "pong";
    }
}
