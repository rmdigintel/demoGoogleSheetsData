import com.justai.jaicf.template.scenario.intentCheckerScenario
import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test

class IntentCheckerTest: ScenarioTest(intentCheckerScenario) {

    @Test
    fun `Check Hello`() {
        query("привет") responds "/Hello"
    }
}