package test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.mill.calculation.QuotaCalculator.calculateQuota;
import static org.assertj.core.api.Assertions.assertThat;

public class QuotaCalculatorTest {

    @Test
    public void calculateQuotaMultiSeatTest() {
        assertThat(calculateQuota(1000, 8)).isEqualTo(new BigDecimal(112));
    }

    @Test
    public void calculateQuotaSingleSeatTest() {
        assertThat(calculateQuota(1000, 1)).isEqualTo(new BigDecimal(501));
    }
}
