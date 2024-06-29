# Green Group

## Sprint 1 - Até 07/abril
  - Nota de grupo (8 pontos)
    - Modelo UML - restaurante, mesas, requisicoes, cliente (nota de grupo, 8 pontos)
	
  - Nota individual (12 pontos)
    - Implementações e testes + app
    - Documentação das classes.

### Commit f0bba39
Diagrama - sem métodos para cliente ou mesa. Sem relação mesa-requisição. Sem métodos para requisição - 4

Demais atividade: só há commits do Marcelo. - 8

Cliente - sem documentação nem testes. Sem nome ou id.

Mesa - sem documentação nem testes. Mesa não tem relação com cliente. Capacidade sem validação.  Sem verificação de ocupação. 

Restaurante - realizando lógica de mesa. sem requisição.

## Sprint 2 - Até 22/maio
  - Nota de grupo (6 pontos)
    - Modelo UML atualizado - cardápio e pedidos
	- Estrutura Spring
  
  - Nota individual (14 pontos)	
    - Implementações cardápio e pedidos
    - Controllers
    - Correções anteriores

### Commit ffb9f0c (22 maio)
Diagrama com fundo preto - legibilidade extremamente prejudicada - padrão UML - 4,8

Cardápio - ok, sem documentação. id é o nome do item. - Marcelo 

Pedido - errado (classe Item no lugar) - Marcelo 

Item - sem validação de preço, sem documentação - Marcelo - 10

Requisicao - sem métodos para inserir produtos no pedido, fechar conta. getPedido desnecessario - Lukas

Restaurante - dando get pedido na requisição. sem fechar pedido no encerramento. código localizar req. - Lukas

App - `static<teclado>`? - Lukas - 8

## Sprint 3 - Até 05/junho
  - Nota de grupo (6 pontos)
    - Modelo atualizado - menu fechado
  
  - Nota individual (14 pontos)	
    - Implementações menu fechado e app
    - Correções anteriores

### Revisão 12/06

Diagrama desatualizado. Exportação com fundo preto. Documentação inexistente - **2 REVISADO DIAGRAMA**

Cardápio: usar coleções adequadamente (for em key set....). getItens

Item: validar preço 

Pedido ok

Requisição. não pode ter getPedido

Restaurante ainda sem coleções e melhorar o uso das streams.

Main: lógicas de exibir cardápio e adicionar item no main com quebra de encapsulamento

Só há commits do Marcelo nesta sprint. (10)

Sprint 4 - Apresentação em 24/06
**Somente Marcelo.**
- Modelo atualizado: classes estão lá, mas o modelo está bem incorreto nas relações entre elas e notações uml
- Apresentação: ok 
- Execução main: cumpre requisitos, mas com problemas de modularidade (métodos para pedido / fechado e cardápio / fechado )
- Uso de exceções: ok
- Modularidade
  - Pedido fechado poderia conferir no cardápio o item (melhora o OCP). 
  - Códigos como ` requisicao.get().setPedido(pedidoFechado);` devem ser evitados... veja a exposição de encapsulamento.
