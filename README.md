# Mini ERP - Sistema de Cadastro de Usuários e Produtos

## Descrição
Este é um Mini ERP que implementa funcionalidades básicas de cadastro de usuários, autenticação de login e gerenciamento de produtos em estoque. O sistema permite que os usuários se autentiquem e, após o login, possam gerenciar o estoque de produtos cadastrando novos produtos, visualizando o estoque, atualizando e removendo produtos.

## Tecnologias Utilizadas
- **Linguagem**: Java 21.0.1
- **Paradigma**: Programação Orientada a Objetos (POO)
- **Persistência de Dados**: Arquivos de texto (`users.txt`, `logs.txt`) para armazenamento de usuários e logs.
- **Controle de Fluxo**: Scanner para interação com o usuário via console.
- **Exceções Customizadas**: Uso de exceções personalizadas como `UserNotFoundException` e `ProductNotFoundException`.

## Desafios escolhidos

**2. Utilizar coleções (como listas, mapas e etc.) e algum padrão de projeto durante a implementação do projeto**
- Coleções: O projeto utiliza a classe List (especificamente uma ArrayList) para armazenar os usuários e os produtos no sistema. A coleção permite que os dados sejam manipulados dinamicamente, como a adição de novos usuários ou produtos, bem como a remoção de itens.
- Padrão de Projeto: O projeto utiliza o padrão de design Factory de maneira implícita. Embora não tenha sido formalmente identificado um padrão no código, a separação de responsabilidades e a utilização de diferentes classes (como AuthService, FileUtil, Logger) sugere uma abordagem modular e reutilizável.
**5. Implementar um sistema de logs para registrar e visualizar todas as ações realizadas no sistema**
- O projeto possui um sistema de logs implementado pela classe Logger, que registra ações importantes, como login de usuário, cadastro de produtos e modificações no estoque, em um arquivo de texto (logs.txt). Isso permite que as ações realizadas pelo usuário sejam monitoradas e auditadas ao longo do tempo.

## Estrutura do Projeto
A estrutura do projeto segue um padrão de organização de pacotes:

```bash
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           ├── model/            # Modelos de dados (User, Product, etc)
│   │           ├── service/          # Serviços (Autenticação, Cadastro de Produto)
│   │           └── utils/            # Utilitários (Logger, FileUtil)
│   ├── resources/
│   │   ├── logs.txt                  # Arquivo de logs
│   │   └── users.txt                 # Arquivo de usuários

```

## Instruções de uso

### 1. Clonar o repositório 
Primeiro, faça o clone do repositório em seu ambiente local:

```bash
git clone https://github.com/usuario/projeto-mini-erp.git

```

### 2. Compilar o Projeto
Este projeto não usa Maven ou Gradle, portanto, você precisa compilar manualmente o código Java. Para compilar, navegue até o diretório do projeto e execute o comando:

```bash
javac -d bin src/main/java/com/example/*.java

```

### 3. Executar o Projeto
Depois de compilar o projeto, você pode executá-lo com o seguinte comando:

```bash
java -cp bin com.example.Main

```


## Funcionalidades
- **Cadastro de Usuários**: Permite o cadastro de novos usuários no sistema.
- **Autenticação de Usuários**: Realiza a verificação de login de usuários cadastrados no sistema.
- **Cadastro de Produtos**: Permite o cadastro de novos produtos no estoque.
- **Gestão de Estoque**: Visualização do estoque de produtos, atualização de informações dos produtos e remoção de produtos.
- **Logs de Ações**: Registra ações importantes do sistema como login de usuários, cadastros e modificações no estoque em arquivos de log.

## Como Funciona o sistema

### Cadastro de Usuários
Os usuários são armazenados no arquivo `users.txt`, e o sistema verifica se o nome de usuário e a senha fornecidos são válidos no momento do login. Se a autenticação for bem-sucedida, o usuário tem acesso ao sistema de gerenciamento de produtos.

### Cadastro de Produtos
Cada produto tem as seguintes informações:

- **Nome**: Nome do produto.
- **Descrição**: Descrição do produto.
- **Quantidade**: Quantidade disponível em estoque (em Kg).
- **Preço**: Preço do produto.

Os produtos são armazenados em um array e podem ser gerenciados (adicionados, atualizados ou removidos) durante a execução do programa.

### Logs de Ações
O sistema registra todas as ações relevantes, como login de usuário, atualizações no estoque de produtos e erros. Esses logs são gravados no arquivo `logs.txt`.

### Exceções
- **UserNotFoundException**: Lançada quando o usuário não é encontrado durante o processo de login.
- **ProductNotFoundException**: Lançada quando um produto não é encontrado durante as operações de atualização ou remoção.