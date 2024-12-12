Use localhost:3002/api/* for your request.

You need a MySQL database and connect it to your properties file. Then, you can use register route for create a new user. When you have a user, use /api/auth/login et get your own token. The token is required for all authenticated routes like /api/rents.

You can see the Swagger in this URL : localhost:3002/api/swagger-ui.html
