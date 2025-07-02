package com.example.siem_dashboard.controller;


import com.example.siem_dashboard.model.LogDeAtaque;
import com.example.siem_dashboard.service.LogDeAtaqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogDeAtaqueController {

    @Autowired
    private LogDeAtaqueService service;

    // Endpoint pra gerar logs fake (ex: /api/logs/gerar?quantidade=200)
    @PostMapping("/gerar")
    public String gerarLogsFake(@RequestParam(defaultValue = "100") int quantidade) {
        service.gerarLogsFake(quantidade);
        return quantidade + " logs gerados!";
    }

    // Listar todos logs
    @GetMapping
    public List<LogDeAtaque> listarLogs() {
        return service.listarLogs();
    }

    @GetMapping("/estatisticas/tipo")
    public Map<String, Long> estatisticasPorTipo() {
        return service.ataquesPorTipo();
    }

    @GetMapping("/estatisticas/status")
    public Map<String, Long> estatisticasPorStatus() {
        return service.ataquesPorStatus();
    }

    @GetMapping("/estatisticas/top-origem")
    public Map<String, Long> topIpsDeOrigem(@RequestParam(defaultValue = "5") int limite) {
        return service.topIpsOrigem(limite);
    }

    @GetMapping("/estatisticas/hora")
    public Map<Integer, Long> ataquesPorHora() {
        return service.ataquesPorHora();
    }

}

