# CTSE - Assignment 3 - User Authentication

### Before run the project
- Create a Database and add collection called 'roles'. (In my case I used mongoDB)
- Assign your roles accordingly. Ex :- ' "name":"ROLE_ADMIN" ' and so on as you want.
- If you assign new role name, you need to add that role to "Role.java" as well.
- And also set that role into the registration method(In my case).

### EndPoints :-  
- Localhost : http://localhost:8080
- RemoteHost : http://52.255.56.85:8080

|       |       |
| :---: | :-- |
| /api/auth/sign-in | { "username":"Anne", "contactNo": "0788888888", "email":"example@gmail.com", "password":"123456", "userType":"buyer"} |
| /api/auth/sign-up | { "username":"Anne", "password":"123456"} | 

