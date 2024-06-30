package app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
@EnableMongoAuditing
@EnableMongoRepositories
@ConfigurationPropertiesScan
class ChatBackEndApplication

fun main(args: Array<String>) {
    runApplication<ChatBackEndApplication>(*args)
}