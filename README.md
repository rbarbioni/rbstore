**Projeto exemplo de integração de sistema com a API de pagamento MOIP**

Projeto contém Backend e FrontEnd com uma pequeno E-Commerce onde é possível adicionar itens no carrinho, fazer manipulação de quantidade e opções de desconto e acréssimo de valores. O checkout é realizado somente com opção de cartão de crédito que o mesmo é validado utilizando um validador da própria MOIP.

## Tecnologias
- Java 8
- Spring-Boot
- Spring-Web
- Validator
- Jackson
- Gradle
- AngularJS
- Bootstrap
## Como testar

</pre>
git clone https://github.com/rbarbioni/rbstore.git
cd rbstore
chmod -x gradlew
gradle clean build
java -jar build/libs/rbstore-0.0.1-SNAPSHOT.war

Aplicação será executada em http://localhost:8080

</pre>