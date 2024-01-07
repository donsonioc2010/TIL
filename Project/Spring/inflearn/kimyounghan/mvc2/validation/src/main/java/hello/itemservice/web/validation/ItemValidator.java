package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz); // Item.class.isAssignableFrom(clazz) : clazz가 Item.class 또는 Item의 자식 클래스인지 확인
    }

    /**
     * Errors는 BindingResult의 부모 인터페이스이다.
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required"); // required.item.itemName
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

    }
}
