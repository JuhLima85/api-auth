# API de autenticação

API para o gerenciamento de colaboradores, incluindo sua hierarquia organizacional e a gestão de suas credenciais de acesso.

### Inicializar Aplicação
```bash

## Inicie os serviços definidos no arquivo docker-compose.yaml
docker-compose up
```

### A API fornece os seguintes endpoints:
```bash
POST /auth/login

POST /auth/register

PUT /auth/update/{id}

DELETE /auth/delete/{id}

GET /auth

```


### Tecnologias Utilizadas
- Java 17
- Java Spring
- Maven
- Apache Tomcat
- Banco de Dados MySQL
- Banco de Dados em Memória H2 (utilizado para testes)
- Flyway Migrations
- JUnit 5 e Mockito
- Docker

<br>



## Autora
Juliana Lima

[![Linkedin](https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/feed/?trk=guest_homepage-basic_nav-header-signin)
[![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white)](https://contate.me/Juliana-Lima)
[![Portfólio](https://img.shields.io/badge/Portf%C3%B3lio-%E2%9C%88%EF%B8%8F-lightgrey?style=for-the-badge)](https://codedeving.netlify.app/)
