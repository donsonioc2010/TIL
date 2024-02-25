package jong1.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// SpringBoot2까지는 @RequestMapping만 사용해도 Controller로 인식이 가능했다.
//@RequestMapping
//@ResponseBody
// SpringBoot3부터는 Controller에 대한 명시 필수
@RestController
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
