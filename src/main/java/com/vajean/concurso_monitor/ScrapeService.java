package com.vajean.concurso_monitor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ScrapeService {
    static public void scrapeConcursos() {
        System.out.println("Scraping started...");
        
        try {
            //Get the HTML document from the website
            Document doc = Jsoup.connect("https://concursosnobrasil.com/concursos/pe/").userAgent("Mozila").get();

            Elements concursos_body = doc.select("#conteudo > table:nth-child(3)");

            Elements concursos_elements = concursos_body.select("tbody > tr");
            
            if (concursos_elements.size() > 0) {
                for (int i = 0; i < concursos_elements.size(); i++){
                    Elements concurso = concursos_elements.get(i).select("td");
                    if (concurso.size() > 0) {
                        
                }
                }
            } else {
                System.out.println("No concursos found.");
            }
            
            Thread.sleep(2000);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Scraping finished.");

        System.out.println("--------------------------------------------------");
        System.out.println("Adding to JSON file...");
    }
}