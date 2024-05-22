# Thoga-Kade-POS-FX

Thoga-Kade-POS-FX is a point-of-sale (POS) system developed using Java, JavaFX, and Maven. It provides functionalities for managing customers and placing orders efficiently. The project follows a **Layered Architecture** and utilizes various libraries and frameworks for enhanced functionality.

## Features

- **Add Customer Form:** Allows users to perform CRUD (Create, Read, Update, Delete) operations on customer data.
  
- **Place Order Form:** Enables users to place orders for items.
- **Database Interaction:** Utilizes MySQL for database management, with Hibernate for streamlined CRUD operations in the Add Customer Form.
- **JavaFX:** Utilizes JavaFX for building the user interface.
- **JFoenix:** Incorporates JFoenix library for improved UI components.
- **Lombok:** Uses Lombok library for reducing boilerplate code.
- **Gson:** Integrates Gson for JSON serialization and deserialization.
- **ModelMapper:** Utilizes ModelMapper for mapping between different Java bean properties.
- **Hibernate:** Implements Hibernate for streamlined database interactions.
- **Maven:** Utilizes Maven for project management and dependency resolution.
- **Design Patterns:** Implements various design patterns including Singleton, Factory, and Strategy to enhance code organization and maintainability.
- **Cross-platform Execution:** The application can be executed on multiple platforms thanks to its conversion to a JAR file.
- **Easy Deployment:** The application is packaged as an EXE file for simplified deployment on Windows systems.

## Interface

<div style="display: flex; justify-content: center; align-items: center;">
   <img src="https://github.com/ravinduheshan99/Thoga-Kade-POS-FX/blob/main/assets/img/01.png" alt="Img01" width="1200" height="600">
</div>

---

<div style="display: flex; justify-content: center; align-items: center;">
   <img src="https://github.com/ravinduheshan99/Thoga-Kade-POS-FX/blob/main/assets/img/02.png" alt="Img02" width="1200" height="600">
</div>

---

## Build and Execution

1. Ensure you have Java Development Kit (JDK) installed on your system.
2. Clone the repository:

    ```
    git clone https://github.com/ravinduheshan99/Thoga-Kade-POS-FX.git
    ```

3. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
4. Set up MySQL database and configure the database connection in the application.
5. Build the project using Maven:

    ```
    mvn clean install
    ```

6. Run the application:

    ```
    java -jar target/Thoga-Kade-POS-FX-1.0-SNAPSHOT.jar
    ```

## Configuration

Ensure you configure the following properties in `application.properties` file:

- Database connection properties (e.g., URL, username, password)
- Hibernate properties (if applicable)

## Contact
For any inquiries or feedback, please contact:

Email: ravinduheshan99@gmail.com

## Authors
- [@ravinduheshan99](https://github.com/ravinduheshan99)
