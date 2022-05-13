# CTSE - Assignment 3 - User Authentication

### Before run the project
- Create a Database and add collection called 'roles'. (In my case I used mongoDB)
- Assign your roles accordingly. Ex :- ' "name":"ROLE_ADMIN" ' and so on as you want.
- If you assign new role name, you need to add that role to "Role.java" as well.
- And also set that role into the registration method(In my case).

### EndPoints -  http://localhost:8080

|       |       |
| :---: | :-- |
| /api/signin | { "username":"Anne", "contactNo": "0788888888", "email":"example@gmail.com", "password":"123456", "userType":"buyer"} |
| /api/signup | { "username":"hirush", "password":"123456"} | 

