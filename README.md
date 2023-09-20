# Phonebook #

**Started as a course project, further development to practice**
- Frontend: HTML, CSS, JS, Vue.js, Bootstrap
- Backend: Spring framework + Hibernate + H2 / MySQL
- Build: Spring + Maven

**Project functionality**
- Display existing contacts
- Adding new contacts
- Deleting contacts (without actual deleting records from the database)
- Switching <importance> of contacts
- Call table + API for receiving calls by contact

**To run the project**
- Run Maven Spring Application (H2 In-memory data base will run by default)
  or
- Get Docker
- Run MySQL service from /docker/docker-compose.yml
- Amend application.properties to use MySQL instead of H2
- Run Maven Spring Application