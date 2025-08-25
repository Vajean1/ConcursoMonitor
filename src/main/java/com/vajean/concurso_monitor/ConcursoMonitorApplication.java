package com.vajean.concurso_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vajean.concurso_monitor.ScrapeService;


@SpringBootApplication
public class ConcursoMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcursoMonitorApplication.class, args);
		ScrapeService scraper = new ScrapeService();
		scraper.scrapeConcursos();

	}
}
