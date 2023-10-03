# Phonebook #

**Started as a course project, further development as pet**
- Frontend: HTML, CSS, JS, Vue.js, Bootstrap
- Backend: Spring framework (Boot, Data) + Hibernate + H2 / MySQL
- Build: Spring + Maven

**Project functionality**
- Display existing contacts
- Adding new contacts
- Deleting contacts (without actual deleting records from the database)
- Switching <importance> of contacts
- Mock calls

**To run the project**
- Run Maven Spring Application (H2 In-memory database will run by default)
  or
- Run Docker
- Run MySQL service from /docker/docker-compose.yml
- Amend application.properties to use MySQL instead of H2
- Run Maven Spring Application