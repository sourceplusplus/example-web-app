package example.webapp.configuration

import example.webapp.model.User
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["spp.example.webapp.repository"],
    entityManagerFactoryRef = "cardEntityManagerFactory"
)
class UserStorageConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.card")
    fun cardDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("app.datasource.card.configuration")
    fun cardDataSource(): DataSource {
        return cardDataSourceProperties()
            .initializeDataSourceBuilder()
            .build()
    }

    @Bean(name = ["cardEntityManagerFactory"])
    fun cardEntityManagerFactory(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(cardDataSource())
            .packages(User::class.java)
            .build()
    }
}