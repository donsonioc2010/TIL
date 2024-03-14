package jong1.pay;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ProdPayClient implements PayClient {

    @Override
    public void pay(int money) {
        log.info("운영결제를 진행합니다. 금액 >>> {}", money);
    }
}
