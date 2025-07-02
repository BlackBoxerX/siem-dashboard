package com.example.siem_dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class LogDeAtaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoAtaque;      // Ex: BRUTE_FORCE, SQLI, XSS, PORT_SCAN
    private String origemIp;
    private String destinoIp;
    private String userAgent;
    private LocalDateTime dataHora;
    private String status;          // BLOQUEADO, SUSPEITO, SUCESSO
}
