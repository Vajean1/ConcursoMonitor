# 🚀 ConcursoMonitor 🤖

O `ConcursoMonitor` é uma aplicação que monitora concursos públicos no estado de Pernambuco. 🎯 Ele utiliza a biblioteca **Jsoup** para extrair dados de um site especializado, armazena-os em um arquivo JSON e disponibiliza as informações por meio de um **bot do Discord**, criado com a biblioteca **Discord4J**. 🤖

O projeto foi arquitetado para ser executado de forma modular utilizando containers Docker. 🐳

## ✨ Funcionalidades

  * **Scraper de Concursos**: Um serviço que extrai dados de concursos públicos da internet, salvando-os em um arquivo JSON (`concursos.json`). 📁
  * **Discord Bot**: Um bot que se conecta ao Discord, lê os dados dos concursos do arquivo JSON e responde a comandos dos usuários. 💬
  * **Execução Modular**: As funcionalidades de scraping e do bot são executadas em containers Docker separados, permitindo gerenciamento independentes. 🧩

-----

## 🛠️ Pré-requisitos

Para executar este projeto, você precisará ter o seguinte instalado em sua máquina:

  * **Java JDK 21** ou superior
  * **Apache Maven 3.9** ou superior
  * **Docker**
  * **Docker Compose**

-----

## ⚙️ Configuração e Instalação

Para configurar e executar a aplicação.

### 1\. Obtenha o Token do Bot

1.  Acesse o [Portal de Desenvolvedores do Discord](https://www.google.com/search?q=https://discord.com/developers/applications).
2.  Crie ou selecione sua aplicação.
3.  Vá para a aba **Bot**.
4.  Clique em **"Reset Token"** e copie o token gerado.
5.  **⚠️ Não exponha este token no código-fonte.** Você o configurará como uma variável de ambiente.

### 2\. Compile o Projeto

Navegue até o diretório raiz do projeto e compile o código Java em um arquivo `.jar` executável com o Maven.

```bash
mvn clean package
```

### 3\. Execute com Docker Compose

O projeto é executado usando o Docker Compose, que orquestra dois serviços: `scraper` e `discord-bot`.

  * **`scraper`**: O serviço do scraper está configurado para rodar o scraping de hora em hora (agendamento com `cron`). ⏰
  * **`discord-bot`**: O serviço do bot é iniciado e fica online, pronto para responder aos comandos. 💬

Defina a variável de ambiente `DISCORD_BOT_TOKEN` no seu terminal e, em seguida, execute o seguinte comando para construir as imagens e iniciar ambos os serviços:

```bash
# Para Linux/macOS
export DISCORD_BOT_TOKEN="SEU_TOKEN_AQUI"
docker-compose up --build

# Para Windows (CMD)
set DISCORD_BOT_TOKEN="SEU_TOKEN_AQUI"
docker-compose up --build
```

-----

## 🕹️ Como Usar o Bot

Após executar o Docker Compose, seu bot ficará online no Discord. Use os seguintes comandos em qualquer canal que ele tenha acesso:

  * `!concursos`: Exibe a lista de todos os concursos. 📋
  * `!abertos`: Exibe apenas os concursos com status **"aberto"**. ✅
  * `!previstos`: Exibe apenas os concursos com status **"previsto"**. 🗓️

-----

## 📂 Estrutura do Projeto

```
.
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── vajean/
│                   └── concurso_monitor/
│                       ├── Concursos.java
│                       ├── ScrapeService.java
│                       └── DiscordBot.java
├── concursos.json
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── run_scraper.sh
```