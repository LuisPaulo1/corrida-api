# corrida-api
## Sobre o projeto
É uma API desenvolvida com Java e Spring Boot que recebe um arquivo de texto como entrada e executa um algoritmo para ler o conteúdo do arquivo.

### Algoritmo da corrida-api extrai as seguintes informações do arquivo de texto
- Posição de chegada de cada Super-herói
- Código do Super-herói
- Nome Super-herói
- Quantidade de voltas completadas
- Tempo Total de Prova
- A melhor volta de cada super-herói. 
- A melhor volta da corrida
- O cálculo da velocidade média de cada super-herói durante toda a corrida

### Collection do Postman para corrida-api
Importar o link no Postman: https://www.getpostman.com/collections/435b70885c41d5ad65b3

### Tecnologias utilizadas
- Java 11
- Spring (boot, web, validation)
- Lombok

## Como executar corrida-api
### Pré-requisitos
- Java 11

```bash
### Clonar o projeto
git clone https://github.com/LuisPaulo1/corrida-api.git

### Entrar na pasta do projeto corrida-api
cd corrida-api

### Executar
./mvnw spring-boot:run
```

### Para utilizar a API é necessário criar um arquivo com a extensão txt contendo o conteúdo abaixo para ser utilizado nas requisições. Ex: corrida.txt

```bash
Hora;Super-Heroi;Nº Volta;Tempo Volta;Velocidade média da volta
23:49:08.277;038–Superman;1;1:02.852;44,275 
23:49:10.858;033–Flash;1;1:04.352;43,243 
23:49:11.075;002–Mercúrio;1;1:04.108;43,408 
23:49:12.667;023–Sonic;1;1:04.414;43,202 
23:49:30.976;015–PAPALÉGUA;1;1:18.456;35,47 
23:50:11.447;038–Superman;2;1:03.170;44,053 
23:50:14.860;033–Flash;2;1:04.002;43,48 
23:50:15.057;002–Mercúrio;2;1:03.982;43,493 
23:50:17.472;023–Sonic;2;1:04.805;42,941 
23:50:37.987;015–PAPALÉGUA;2;1:07.011;41,528 
23:51:14.216;038–Superman;3;1:02.769;44,334 
23:51:18.576;033–Flash;3;1:03.716;43,675 
23:51:19.044;002–Mercúrio;3;1:03.987;43,49 
23:51:21.759;023–Sonic;3;1:04.287;43,287 
23:51:46.691;015–PAPALÉGU;3;1:08.704;40,504 
23:52:01.796;011–GATOAJATO;1;3:31.315;13,169 
23:52:17.003;038–Superman;4;1:02.787;44,321 
23:52:22.586;033–Flash;4;1:04.010;43,474 
23:52:22.120;002–Mercúrio;4;1:03.076;44,118 
23:52:25.975;023–Sonic;4;1:04.216;43,335 
23:53:06.741;015–PAPALÉGUA;4;1:20.050;34,763 
23:53:39.660;011–GATOAJATO;2;1:37.864;28,435 
23:54:57.757;011–GATOAJATO;3;1:18.097;35,633
```
### Observações
- A primeira linha do arquivo pode ser desconsiderada (Cabeçalho).
- O conteúdo do arquivo não deve conter o cabeçalho.
- A corrida termina quando o primeiro colocado completa 4 voltas.

# Autor

Luis Paulo

https://www.linkedin.com/in/luis-paulo-souza-a54358134/
