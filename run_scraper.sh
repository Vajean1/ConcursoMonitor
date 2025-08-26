#! bin/bash

mvn clean package

java -cp app.jar com.vajean.concurso_monitor.ScrapeService
