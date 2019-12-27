# Enterprise Simulation

A game to simulate a corporate environment to help logistics students experience how administrate a company would be in real life. 
The game was developed using Java Spring Boot for back-end. Mainly vue, js and css for front-end. PostgreSQL as DBMS. 

It was developed by Analysis and Systems Development students at the **University of Technology - Fatec São José dos Campos** as an interdisciplinary project that covers the subjects Project Management, Team Management, Special Topics in IT and IT Governance.
The project was developed during the 6th semester applying concepts learned during the course, like agile methodologies.  

**Members** (Name and Gitlab username)
* André Luiz Rodrigues de Paulo @andre.rodrigues0018
* Elize Leite @ElizeLeite
* Felipe Gabriel @OFelipeGabriel
* Gabriel Frederico Takahashi Vargas @GabrielTaka
* Gabriel Tomé @TomeGabriel98
* Iago Saito @IagoSaito
* Jefferson Gonçalves Garcia @jeffersong19
* Joao Manoel Franco @francojmf
* Jonathan Lima @joohnnathans
* Kelvin Severino @KelvinSeverino
* Lucas Santos @lucas.7ss.ls
* Luiz Prianti @priantf
* Miqueas Cantaleano @gilbertmiqueas
* Natally Riqueto @riquetonatally
* Renan Palomino @renan.granusso
* Victor Augusto Santos Machado @victor.santos11
* Victor Massayuki Umehara @oMassa
* Victor Porto Braga @tinhobraga


**Executing the project**

Create a database named "jogo" on PostgreSQL. Using PgAdmin is the easiest way.

To execute the back-end on ItelliJ, find the Maven tab in the right corner of the screen. 
Then 
> Plugins > spring-boot > spring-boot:run

The main project route is *localhost:8082/springRest/*. You can access it by Postman.

To execute the front-end locally pointing to the local back-end then just install dependencies executing 
> npm install 

And run the project
> npm run dev

Heroku App is running completly in http://projetofatecb.herokuapp.com