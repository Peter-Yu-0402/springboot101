import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTest {
    @DisplayName("1 + 2는 3이다.") // 테스트 이름
    @Test // 해당 애너테이션을 붙인 메서드는 테스트를 수행하는 메서드가 된다.
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        // 검증 메서드 사용
        Assertions.assertEquals(a + b, sum); // 값이 같은지 확인

        // 기댓값과 비교값이 가독성 있게 구분된 AssertJ
        assertThat(a + b).isEqualTo(sum);
    }
}
