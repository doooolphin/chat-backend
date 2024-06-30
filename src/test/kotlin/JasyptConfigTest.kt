import org.assertj.core.api.Assertions
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import kotlin.test.Test

class JasyptConfigTest {
    @Test
    fun makeJasypt() {
        val pbeEnc = StandardPBEStringEncryptor()
        pbeEnc.setPassword("key")
        val value = pbeEnc.encrypt("value")
        println("url = $value")

        // 테스트용 복호화
        val des = pbeEnc.decrypt(value)
        println("des = $des")
        Assertions.assertThat("value").isEqualTo(pbeEnc.decrypt(value))
    }
}