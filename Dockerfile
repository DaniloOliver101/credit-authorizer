# Usar a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Instalar Gradle
ENV GRADLE_VERSION=7.5.1
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    rm gradle-${GRADLE_VERSION}-bin.zip && \
    ln -s /opt/gradle-${GRADLE_VERSION} /opt/gradle && \
    echo 'export PATH=$PATH:/opt/gradle/bin' >> /etc/profile.d/gradle.sh && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Definir variáveis de ambiente
ENV GRADLE_HOME /opt/gradle
ENV PATH $GRADLE_HOME/bin:$PATH

# Configurar diretório de trabalho
WORKDIR /app

# Copiar o código do projeto para o diretório de trabalho
COPY . .

# Construir o projeto usando Gradle
RUN gradle build --no-daemon

# Expor a porta que a aplicação irá rodar
EXPOSE 8080

# Comando de entrada para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/build/libs/credit-authorizer-0.0.1-SNAPSHOT.jar"]
