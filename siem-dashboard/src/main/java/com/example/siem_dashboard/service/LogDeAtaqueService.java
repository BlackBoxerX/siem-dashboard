package com.example.siem_dashboard.service;

import com.example.siem_dashboard.model.LogDeAtaque;
import com.example.siem_dashboard.repository.LogDeAtaqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
public class LogDeAtaqueService {

    @Autowired
    private LogDeAtaqueRepository repo;

    // Gera logs fake de ataque (quantidade definida)
    public void gerarLogsFake(int qtd) {
        String[] tipos = {"BRUTE_FORCE", "SQLI", "XSS", "PORT_SCAN", "DDOS"};
        String[] status = {"BLOQUEADO", "SUSPEITO", "SUCESSO"};
        Random rand = new Random();
        for (int i = 0; i < qtd; i++) {
            LogDeAtaque log = new LogDeAtaque();
            log.setTipoAtaque(tipos[rand.nextInt(tipos.length)]);
            log.setOrigemIp("192.168." + rand.nextInt(256) + "." + rand.nextInt(256));
            log.setDestinoIp("10.0.0." + rand.nextInt(256));
            log.setUserAgent("FakeBot/" + (100 + rand.nextInt(900)));
            log.setDataHora(LocalDateTime.now().minusMinutes(rand.nextInt(60)));
            log.setStatus(status[rand.nextInt(status.length)]);
            repo.save(log);
        }
    }

    public List<LogDeAtaque> listarLogs() {
        return repo.findAll();
    }

    // Ataques por tipo
    public Map<String, Long> ataquesPorTipo() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(LogDeAtaque::getTipoAtaque, Collectors.counting()));
    }

    // Ataques por status
    public Map<String, Long> ataquesPorStatus() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(LogDeAtaque::getStatus, Collectors.counting()));
    }

    // Top IPs de origem
    public Map<String, Long> topIpsOrigem(int limite) {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(LogDeAtaque::getOrigemIp, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limite)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b)->a,
                        LinkedHashMap::new
                ));
    }

    // Ataques por hora (só da data de hoje)
    public Map<Integer, Long> ataquesPorHora() {
        return repo.findAll().stream()
                .filter(log -> log.getDataHora().toLocalDate().equals(java.time.LocalDate.now()))
                .collect(Collectors.groupingBy(log -> log.getDataHora().getHour(), Collectors.counting()));
    }
}

