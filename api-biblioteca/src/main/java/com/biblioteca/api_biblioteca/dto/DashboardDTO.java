package com.biblioteca.api_biblioteca.dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private long totalLivros;
    private long totalLeitores;
    private long emprestimosAtivos;
    private long emprestimosAtrasados;
}
