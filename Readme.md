# This is a spring-boot project

### Pre-setup
- Elasticsearch: Project contains postman collection for creating index/type creation.

### How to run!
  - cd to project directory whereever unpacked
  - Setup Elasticsearch as mentioned under pre-setup section. (6.3.0 version is used)
  - run ``mvn clean install spring-boot:run``
  - open browser: http://*hostname*:8080/swagger-ui.html
  - Add sample content using addContents API
  - Afterwards, perform read operation using GET api "getSuggestions" and select isSpringDat to *true*