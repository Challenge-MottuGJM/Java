
# ☕ Mottu - Projeto Java (EasyFinder)

### 🧑‍🤝‍🧑 Integrantes do Projeto

- **Gustavo de Aguiar Lima Silva** - RM: 557707  
- **Julio Cesar Conceição Rodrigues** - RM: 557298  
- **Matheus de Freitas Silva** - RM: 552602  

---

### 💡 Descrição da Solução

O projeto **Mottu** é uma aplicação Java baseada em **Spring Boot**, estruturada com **Maven** para gerenciamento de dependências. A solução foi criada com o objetivo de fornecer uma API robusta para manipulação e consulta de dados no contexto de mobilidade urbana, com funcionalidades que podem incluir cadastro de usuários, localização de veículos, controle de serviços, entre outras operações comuns em sistemas de logística e entrega.

---

### ⚙️ Como Executar o Projeto Localmente

#### ✅ Pré-requisitos

Certifique-se de ter instalado:

- **Java JDK 17** ou superior  
- **Maven** (versão 3.8+)
- **IDE** como IntelliJ IDEA, Eclipse ou VS Code (com suporte a Java)

---

#### 📦 Instalação

1. Clone o repositório ou extraia o projeto:

   ```bash
   git clone https://github.com/Challenge-MottuGJM/Java.git
   ```

2. Acesse a pasta raiz do projeto:

   ```bash
   cd Java-master/Java-master
   ```

3. Compile o projeto e baixe as dependências:

   ```bash
   mvn clean install
   ```

---

#### 🚀 Executando a Aplicação

1. Rode o projeto com:

   ```bash
   mvn spring-boot:run
   ```

2. A aplicação estará disponível por padrão em:  
   [http://localhost:8080](http://localhost:8080)

---

### 📁 Estrutura do Projeto

```
Java-master/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── pom.xml
├── mvnw
├── ...
```

---

### 🔧 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Maven
- REST APIs
- (Outros frameworks/libraries, se aplicável)

---

### 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e educacionais.

---

### 📬 Como Usar a API Localmente

Com o servidor rodando em `http://localhost:8080`, você pode acessar os seguintes endpoints para interagir com a API.

# 📋 Tabela de Endpoints da API

| Entidade  | Método HTTP | Rota                                | Descrição                              |
|-----------|-------------|-------------------------------------|----------------------------------------|
| Galpões   | GET         | /galpoes/todas                      | Retorna todos os galpões               |
| Galpões   | GET         | /galpoes/todas_cacheable            | Retorna galpões com cache              |
| Galpões   | GET         | /galpoes/{id}                       | Retorna um galpão por ID               |
| Galpões   | GET         | /galpoes/paginadas                  | Retorna galpões paginados              |
| Galpões   | POST        | /galpoes/inserir                    | Insere um novo galpão                  |
| Galpões   | DELETE      | /galpoes/excluir/{id}               | Exclui um galpão pelo ID               |
| Galpões   | PUT         | /galpoes/atualizar/{id}              | Atualiza um galpão                     |
| Andares   | GET         | /andares/todas                      | Retorna todos os andares               |
| Andares   | GET         | /andares/todas_cacheable            | Retorna andares com cache              |
| Andares   | GET         | /andares/{id}                       | Retorna um andar por ID                |
| Andares   | GET         | /andares/paginadas                  | Retorna andares paginados              |
| Andares   | POST        | /andares/inserir                    | Insere um novo andar                   |
| Andares   | DELETE      | /andares/excluir/{id}               | Exclui um andar pelo ID                |
| Andares   | PUT         | /andares/atualizar/{id}              | Atualiza um andar                      |
| Patios    | GET         | /patios/todas                       | Retorna todos os pátios                |
| Patios    | GET         | /patios/todas_cacheable             | Retorna pátios com cache               |
| Patios    | GET         | /patios/{id}                        | Retorna um pátio por ID                |
| Patios    | GET         | /patios/paginadas                   | Retorna pátios paginados               |
| Patios    | POST        | /patios/inserir                     | Insere um novo pátio                   |
| Patios    | DELETE      | /patios/excluir/{id}                | Exclui um pátio pelo ID                |
| Patios    | PUT         | /patios/atualizar/{id}               | Atualiza um pátio                      |
| Blocos    | GET         | /blocos/todas                       | Retorna todos os blocos                |
| Blocos    | GET         | /blocos/todas_cacheable             | Retorna blocos com cache               |
| Blocos    | GET         | /blocos/{id}                        | Retorna um bloco por ID                |
| Blocos    | GET         | /blocos/paginadas                   | Retorna blocos paginados               |
| Blocos    | POST        | /blocos/inserir                     | Insere um novo bloco                   |
| Blocos    | DELETE      | /blocos/excluir/{id}                | Exclui um bloco pelo ID                |
| Blocos    | PUT         | /blocos/atualizar/{id}               | Atualiza um bloco                      |
| Vagas     | GET         | /vagas/todas                        | Retorna todas as vagas                 |
| Vagas     | GET         | /vagas/todas_cacheable              | Retorna vagas com cache                |
| Vagas     | GET         | /vagas/{id}                         | Retorna uma vaga por ID                |
| Vagas     | GET         | /vagas/paginadas                    | Retorna vagas paginadas                |
| Vagas     | POST        | /vagas/inserir                      | Insere uma nova vaga                   |
| Vagas     | DELETE      | /vagas/excluir/{id}                 | Exclui uma vaga pelo ID                |
| Vagas     | PUT         | /vagas/atualizar/{id}                | Atualiza uma vaga                      |
| Motos     | GET         | /motos/todas                        | Retorna todas as motos                 |
| Motos     | GET         | /motos/todas_cacheable              | Retorna motos com cache                |
| Motos     | GET         | /motos/{id}                         | Retorna uma moto por ID                |
| Motos     | GET         | /motos/paginadas                    | Retorna motos paginadas                |
| Motos     | GET         | /motos/moto_por_status/{status}              | Filtra motos por status                |
| Motos     | GET         | /motos/moto_por_modelo/{modelo}              | Filtra motos por modelo                |
| Motos     | GET         | /motos/moto_por_placa/{placa}               | Filtra motos por placa                 |
| Motos     | POST        | /motos/inserir                      | Insere uma nova moto                   |
| Motos     | DELETE      | /motos/excluir/{id}                 | Exclui uma moto pelo ID                |
| Motos     | PUT         | /motos/atualizar/{id}                | Atualiza uma moto                      |

> 💡 Use ferramentas como **Postman**, **Insomnia**, **curl** ou diretamente pelo navegador para testar esses endpoints localmente.

---
