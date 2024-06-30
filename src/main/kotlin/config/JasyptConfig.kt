package config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Configuration
class JasyptConfig {
    @Autowired
    private val resourceLoader: ResourceLoader? = null

    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor? {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.password = resourceLoader?.let { getJasyptEncryptorPassword(it) }
        config.algorithm = "PBEWithMD5AndDES" // 사용할 알고리즘
        config.setKeyObtentionIterations("1000")
        config.setPoolSize("1")
        config.providerName = "SunJCE"
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.stringOutputType = "base64"
        encryptor.setConfig(config)
        return encryptor
    }

}

private fun getJasyptEncryptorPassword(resourceLoader: ResourceLoader): String? {
    try {
        return BufferedReader(InputStreamReader(resourceLoader.getResource("classpath:check-this-file.txt")
            .getInputStream())
        ).use { reader -> reader.readLine()}
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}