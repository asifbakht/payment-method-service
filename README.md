# Project Requirements

This project requires the following dependencies and tools to be installed:

- **Java 17**: Make sure you have Java Development Kit (JDK) version 17 or higher installed on your system. You can download it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use a distribution provided by your operating system.

- **Maven 3.8.1**: Ensure you have Apache Maven version 3.8.1 or higher installed on your system. Maven is a build automation tool used primarily for Java projects. You can download it from the [official Apache Maven website](https://maven.apache.org/download.cgi) or use a package manager provided by your operating system.

- **Docker**: Docker is required for containerizing the application. Ensure that Docker is installed and running on your system. You can download Docker Desktop from the [official Docker website](https://www.docker.com/products/docker-desktop).

## Installing Java 17

Follow these steps to install Java 17 on your system:

1. Download the JDK installer from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
2. Follow the installation instructions provided by Oracle for your operating system.
3. After installation, verify that Java 17 is correctly installed by running the following command in your terminal or command prompt:

    ```bash
    java -version
    ```

    You should see output confirming the Java version installed.

## Installing Maven 3.8.1

Follow these steps to install Maven 3.8.1 on your system:

1. Download the Maven binary zip file from the [official Apache Maven website](https://maven.apache.org/download.cgi).
2. Extract the contents of the zip file to a directory on your system.
3. Add the `bin` directory of the extracted Maven folder to your system's PATH environment variable.
4. Verify that Maven is correctly installed by running the following command in your terminal or command prompt:

    ```bash
    mvn -version
    ```

    You should see output confirming the Maven version installed.

## Installing Docker

Follow the installation instructions provided by Docker for your operating system:

- [Docker Desktop for Windows](https://docs.docker.com/desktop/install/windows-install/)
- [Docker Desktop for macOS](https://docs.docker.com/desktop/install/mac-install/)
- [Docker Engine for Linux](https://docs.docker.com/engine/install/)

After installation, ensure that Docker is running by starting the Docker Desktop application or using appropriate commands for your operating system.

# Creating a JAR File using Maven

 ## Steps to Create a JAR File

1. **Navigate to Your Project Directory**: Open a terminal or command prompt and navigate to the root directory of your Maven project where the `pom.xml` file is located.

2. **Run Maven Package Command**: Maven provides a `mvn clean package` goal, which compiles the source code, runs any tests, and packages the application into a distributable format, such as a JAR file. Run the following command:

    ```bash
    mvn clean package
    ```

    This command tells Maven to execute the `package` goal defined in the `pom.xml` file. Maven will compile the source code, run any tests in the project, and package the application into a JAR file.



# Creating a JAR File and Docker Image

This guide outlines the steps to create a JAR file using Maven and then create a Docker image to containerize the application.

## Prerequisites

Before proceeding, ensure that you have the following installed on your system:

- Java Development Kit (JDK)
- Apache Maven
- Docker

## Creating a JAR File with Maven

1. **Navigate to Your Project Directory**: Open a terminal or command prompt and navigate to the root directory of your Maven project where the `pom.xml` file is located.

2. **Run Maven Package Command**: Maven provides a `package` goal, which compiles the source code, runs any tests, and packages the application into a distributable format, such as a JAR file. Run the following command:

    ```bash
    mvn package
    ```

    This command tells Maven to execute the `package` goal defined in the `pom.xml` file. Maven will compile the source code, run any tests in the project, and package the application into a JAR file.

3. **Locate the JAR File**: After the build process completes successfully, you can find the generated JAR file in the `target` directory within your project directory.

    ```bash
    cd target
    ```

    Inside the `target` directory, you should see the JAR file with a name corresponding to your project's artifact ID, version, and packaging type (usually `jar`).

## Creating a Docker Image

1. **Build Docker Image**: Open a terminal or command prompt and navigate to the root directory of your project. Run the following Docker command to build the Docker image:

    ```bash
    docker build -t your-docker-image-name .
    ```

    Replace `your-docker-image-name` with the desired name for your Docker image.

2. **Create Docker Image TAG**: Once the Docker image is built, you can create a tag using the following command:

    ```bash
    docker tag image-name-tag user-name/image-name
    ```
    Replace `image-name-tag` with the your custom provided tag name.
    Replace `user-name` with the name of your Docker hub user-name.
    Replace `image-name` with the name of your Docker image.

3. **Push Docker Image**: Once the Docker image tag is created, you can push that image to your docker hub account with following command:

    ```bash
    docker push user-name/image-name
    ```
    Replace `user-name` with the name of your Docker hub user-name.
    Replace `image-name` with the name of your Docker image.

4. **Use your docker image in ECS INFRA**: After the docker image is pushed clone following git project AWS INFRA repository:

    ```bash
    git clone https://github.com/asifbakht/aexp-sb-infra.git

    goto aexp-sb-infra folder and open variables.tf file and search for "payment_image" and replace the url with your docker hub image url

    variable "payment_image" {
      default = "docker.io/asifbakht/payment-service:latest"
    }   

    ```

4. **Open AEXP Infra repository readme.md file for more details**

