## Repositório criado para armazenar o Desafio de Projeto DIO - Explorando o Rest Assured para automação de Testes de API.


-------
## ENDPOINTS - BOOKING

-------
- EndPoint: CreateToken
- Resource: auth
- Método: POST
- Descrição: API recebe um usuário e uma senha, e estando valido retorna um token de acesso.

-------
- EndPoint: GetBookinglds
- Resource: booking
- Método: GET
- Descrição: Gera uma lista com os IDs dos livros. É possível filtrar através de parâmetros como first name, last name, checkin ou checkout. Os parâmetros são opcionais, se a requisição não tiver parâmetros lista todos os livros.

-------
- EndPoint: GetBooking
- Resource: booking/:id
- Método: GET
- Descrição: Ao informar o ID do livro a API retornar todos os dados do livro. Caso o ID não exista retorna a mensagem de “Not Found”.

-------
- EndPoint: CreateBooking
- Resource: booking
- Método: POST
- Descrição: Adiciona um novo livro no sistema através de uma requisição com os dados do livro como firstname, lastname, totalprice, checkin e checkout.

-------
- EndPoint: UpdateBooking
- Resource: booking/:id
- Método: PUT
- Descrição: Requisição que passa todas as informações do livro e faz as alterações. É necessário um token de acesso para realizar alteração.

-------
- EndPoint: PartialUpdateBooking
- Resource: booking/:id
- Método: PATCH
- Descrição: Requisição para alterar uma determinada informação (firstname, lastname). É necessário um token de acesso para realizar alteração.

-------
- EndPoint: DeleteBooking
- Resource: booking/:id
- Método: DELETE
- Descrição: Ao informar o ID do livro a API apaga esse livro da lista e retorna “201 Created”. É necessário um token de acesso para essa operação.

-------
- EndPoint: HealthCheck
- Resource: ping
- Método: GET
- Descrição: Verifica se a API está respondendo a requisição. 

-------
