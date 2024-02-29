package com.levelup.common.domain.constant;

import lombok.Getter;

@Getter
public enum SkillName {

    C("C"),
    C_PLUS("C++"),
    C_SHARP("C#"),
    GOLANG("GO"),
    RUST("Rust"),
    JAVA("Java"),
    KOTLIN("Kotlin"),
    PYTHON("Python"),
    RUBY("Ruby"),
    JAVA_SCRIPT("Javascript"),
    TYPE_SCRIPT("Typescript"),
    SCALA("SCALA"),
    R("R"),
    PHP("PHP"),
    SWIFT("Swift"),
    PERL("Perl"),
    SHELL_SCRIPT("Shell script"),

    HTML("HTML"),
    CSS("CSS"),

    IOS("IOS"),
    ANDROID("Android"),

    UNIX("UNIX"),
    LINUX("Linux"),
    WINDOWS("Windows"),
    MAC("MAC"),

    SERVLET("SERVLET"),
    TOMCAT("TOMCAT"),
    NETTY("NETTY"),

    JSP("JSP"),
    THYMELEAF("Thymeleaf"),
    SPRING("Spring Framework"),
    SPRING_BOOT("Spring Boot"),
    NODE_JS("NodeJS"),
    EXPRESS_JS("ExpressJS"),
    NEST_JS("NestJS"),
    FLASK("Flask"),
    DJANGO("django"),
    LARAVEL("Laravel"),
    CODEIGNITER("Codeigniter"),

    JQUERY("Jquery"),
    NUXT_JS("NuxtJS"),
    NEXT_JS("NextJS"),
    REACT("React"),
    REDUX("Redux"),
    ANGULAR("Angular"),
    SVELTE("Svelte"),

    MYSQL("MySQL"),
    MSSQL("MSSQL"),
    ORACLE("ORACLE"),
    H2("H2"),
    POSTGRES_SQL("PostgresSQL"),
    MONGO_DB("MongoDB"),
    NEO4J("Neo4j"),
    CASSANDRA("Cassandra"),

    MYBATIS("mybatis"),
    JPA("JPA"),
    HIBERNATE("Hibernate"),
    TYPE_ORM("typeORM"),
    SQL_ALCHEMY("SQLAlchemy"), //파이썬 ORM
    PEEWEE("Peewee"), //파이썬 ORM

    REDIS("Redis"),
    MEMCACHED("Memcached"),
    ARCUS("Arcus"),

    KAFKA("KAFKA"),
    RABBIT_MQ("RabbitMQ"),

    ELASTIC_SEARCH("Elasticsearch"),

    REST_API("REST API"),
    GRAPHQL("GraphQL"),

    GRPC("gRPC"),
    ARMERIA("Armeria"),

    GIT("Git"),
    GITHUB("Github"),
    GIT_ACTION("Git Action"),
    GITLAB("GitLab"),

    JENKINS("Jenkins"),

    AWS("AWS"),
    AZURE("azure"),
    GCP("gcp"),
    ;

    private String name;

    SkillName(String name) {
        this.name = name;
    }
}
