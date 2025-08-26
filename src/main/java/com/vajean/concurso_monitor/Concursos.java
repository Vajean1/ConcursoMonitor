package com.vajean.concurso_monitor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Concursos {
    private String concurso_name;
    private String concurso_link;
    private String concurso_status;
    private String concurso_qnt;

    public Concursos(String concurso_name, String concurso_link, String concurso_status, String concurso_qnt) {
        this.concurso_name = concurso_name;
        this.concurso_link = concurso_link;
        this.concurso_status = concurso_status;
        this.concurso_qnt = concurso_qnt;
    }
}
