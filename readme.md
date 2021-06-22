# Info
if User is not in session scope default user with -1 permission is added to session.
Permissions:

* 3 - Admin -> all permissions
* 2 - Employee -> can add and remove books 
* 1 - Activated user -> can browse books
* 0 - no access, waiting for admin for activation
* -1 - user not logged in

GET API
========

# /
main page

params:

	* none

redirect

	* index.jsp:strona=main

# /JGSS/login
searches for user in database checks password if correct adds it to session

params:
	
	* username:string
	* password:string

redirect:

	* message.jsp -> index.jsp:strona=main


# /JGSS/logout
removes user from session

params:

	* none

redirect

	* message.jsp -> index.jsp:strona=main
	
POST API
==================

# /JSON?action=deleteUser

JSON content:

	* user id

# /JSON?action=addUser
register user

JSON content:

	* username
	* password

# /JSON?action=editUser
register user

JSON content:

	* id
	* value to edit


# /JSON?action=deleteBook

JSON content:

	* book id

# /JSON?action=addBook
add book

JSON content:

	* ISBN
	* Title
	* Series
	* Publisher
	* Author

# /JSON?action=editBook

JSON content:

	* id
	* value to edit


JSP
========

# index.jsp

sessionScope bean:

	* userdb:User

params:
	
	* page:string -> display content based on page string, check permissions

# menu.jsp
		display menu items based on permissions, and login status

menu items:

		* searchByAuthor
		* searchByISBN
		* searchByTitle
		* searchByPublisher
		* advancedSearch
		* adminPanel
		* register

sessionScope bean:

	* userdb:User

params:

	* none

# message.jsp

params:
	
	* text -> text to display
	* returnUrl -> return url

# search.jsp
searches and displays book search results

appScope bean:

	* bookdb:SQL

params:

	* ISBN(optional)
	* Author(optional)
	* Title(optional)
	* Series(optional)
	* Publisher(optional)
