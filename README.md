## Projeto de integração API-V2 para pagamentos MOIP

Projeto contém Backend e FrontEnd com uma pequeno E-Commerce onde é possível adicionar itens no carrinho, fazer manipulação de quantidade e opções de desconto e acréssimo de valores. O checkout é realizado somente com opção de cartão de crédito que o mesmo é validado utilizando SDK da MOIP.
Não foi utilizado camada de persistência de dados, todos os dados são carregados a partir de arquivos .json no resources do projeto.

## Tecnologias
- Java 8
- Spring-Boot
- Spring-Web
- Spring-IntegrationTests
- JUnit
- Validator
- Jackson
- Gradle
- NPM
- AngularJS
- Bootstrap
## Como Rodar

<pre>
git clone https://github.com/rbarbioni/rbstore.git
cd rbstore
chmod -x gradlew
gradle clean build
java -jar build/libs/rbstore-0.0.1-SNAPSHOT.war

Aplicação será executada em http://localhost:8080

</pre>

## Como Testar

Os produtos são fictícios e os valores não representam a realidade.
Quando for requirido autenticação utilize:
<pre>
e-mail: joaosilva@email.com
password: testemoip 
</pre>

Na parte de pagamento, utilizar as informações abaixo:

<pre>

Cartão de Crédito: 5555666677778884
Código Verificação: 123
Mês de Expiração: Dezembro
Ano de Expiração: 2018

</pre>

Opcionalmente poderá utilizar Código Promocional que dará 5% de desconto:

<pre>
OISO334332K9
112DKIXK2223
09OOID33DCD3
</pre>

Pagamento parcelado acréssimo de 2.5%

## Live on Openshift
https://rbstore-pusherad.rhcloud.com

