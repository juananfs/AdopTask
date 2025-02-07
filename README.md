# AdopTask

**Proyecto TFG**  
AdopTask es un proyecto de Trabajo Fin de Grado (TFG) que integra una API backend y una interfaz de usuario para gestionar protectoras de animales, permitiendo reflejar los cambios en un buscador de animales en adopción.

## Tecnologías empleadas

- **Backend**: Java, Spring Boot
- **Frontend**: JavaScript, JSX, CSS, React
- **Base de datos**: MongoDB (accesible y administrable mediante [mongo-express](https://github.com/mongo-express/mongo-express))
- **Contenedores**: Docker, docker-compose

## Requisitos

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

## Instalación y Ejecución

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/juananfs/AdopTask.git
   ```

2. **Acceder al directorio del proyecto**

   ```bash
   cd AdopTask
   ```

3. **Construir e iniciar los contenedores**

   Utiliza el siguiente comando para construir la imagen y levantar todos los servicios definidos:

   ```bash
   docker-compose up --build
   ```

   Este comando iniciará:
   - **adoptask-api** en el puerto `8080`
   - **adoptask-ui** en el puerto `80`
   - **MongoDB** en el puerto `27017`
   - **mongo-express** en el puerto `8081`

4. **Acceder a la aplicación**

   - **Interfaz de usuario**: [http://localhost](http://localhost)
   - **API**: [http://localhost:8080](http://localhost:8080)
   - **Mongo Express**: [http://localhost:8081](http://localhost:8081)

## Estructura del Proyecto

La organización del repositorio es la siguiente:

```
AdopTask/
├── adoptask-api/       # Código fuente de la API backend (Java/Spring Boot)
├── adoptask-ui/        # Código fuente de la interfaz de usuario (JavaScript/React)
├── docker-compose.yml  # Configuración para la orquestación de contenedores
└── .gitignore          # Archivos y directorios excluidos de Git
```

## Configuración

Los servicios se configuran mediante variables de entorno definidas en el archivo `docker-compose.yml`.

- La zona horaria: `TZ: "Europe/Madrid"`
- La conexión a MongoDB: `SPRING_DATA_MONGODB_URI: mongodb://root:root@mongo:27017/AdopTask?authSource=admin`

Puedes modificar estas variables según las necesidades de tu entorno.

---

# AdopTask (English Version)

**Final Degree Project**  
AdopTask is a Final Degree Project that integrates a backend API and a user interface to manage animal shelters, allowing updates to be reflected in an adoption animal search engine.

## Technologies used

- **Backend**: Java, Spring Boot
- **Frontend**: JavaScript, JSX, CSS, React
- **Database**: MongoDB (accessible and manageable via [mongo-express](https://github.com/mongo-express/mongo-express))
- **Containers**: Docker, docker-compose

## Requirements

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

## Installation and Execution

1. **Clone the repository**

   ```bash
   git clone https://github.com/juananfs/AdopTask.git
   ```

2. **Access the project directory**

   ```bash
   cd AdopTask
   ```

3. **Build and start the containers**

   Use the following command to build the image and start all defined services:

   ```bash
   docker-compose up --build
   ```

   This command will start:
   - **adoptask-api** on port `8080`
   - **adoptask-ui** on port `80`
   - **MongoDB** on port `27017`
   - **mongo-express** on port `8081`

4. **Access the application**

   - **User interface**: [http://localhost](http://localhost)
   - **API**: [http://localhost:8080](http://localhost:8080)
   - **Mongo Express**: [http://localhost:8081](http://localhost:8081)

## Project Structure

The repository is organized as follows:

```
AdopTask/
├── adoptask-api/       # Backend API source code (Java/Spring Boot)
├── adoptask-ui/        # User interface source code (JavaScript/React)
├── docker-compose.yml  # Container orchestration configuration
└── .gitignore          # Files and directories excluded from Git
```

## Configuration

Services are configured using environment variables defined in the `docker-compose.yml` file. For example, in the *adoptask-api* service:

- Time zone: `TZ: "Europe/Madrid"`
- MongoDB connection: `SPRING_DATA_MONGODB_URI: mongodb://root:root@mongo:27017/AdopTask?authSource=admin`

You can modify these variables according to your environment needs.
