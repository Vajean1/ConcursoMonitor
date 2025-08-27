package com.vajean.concurso_monitor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

@Service
public class ScrapeService {
    static public void scrapeConcursos() {

        List <Concursos> concursosList = new ArrayList<>();

        System.out.println("Scraping started...");
        
        try {
            Document doc = Jsoup.connect("https://concursosnobrasil.com/concursos/pe/").userAgent("Mozila").get();

            Elements concursos_body = doc.select("#conteudo > table:nth-child(3)");

            Elements concursos_elements = concursos_body.select("tbody > tr");
            
            if (concursos_elements.size() > 0) {
                for (int i = 0; i < concursos_elements.size(); i++){
                    Elements concurso = concursos_elements.get(i).select("td");
                    if (concurso.size() > 0) {
                        String status;
                        String name_concurso = concurso.select("a").text();
                        String link_concurso = concurso.select("a").attr("href");
                        String concurso_qnt = concurso.get(1).text();
                    
                        if (concurso.select("div").text().toLowerCase().contains("previsto")){
                            status = "previsto";
                        } else {
                            status = "aberto";
                        }

                        concursosList.add(new Concursos(name_concurso, link_concurso, status, concurso_qnt));
                    }else{
                        System.out.println("No concurso data found in this td.");
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

        ObjectMapper mapper = new ObjectMapper();
        try {
            File ConcursosJSON = new File("concursos.json");
            mapper.writeValue(ConcursosJSON, concursosList);
            System.out.println("File created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main() {
        scrapeConcursos();
    }
}
