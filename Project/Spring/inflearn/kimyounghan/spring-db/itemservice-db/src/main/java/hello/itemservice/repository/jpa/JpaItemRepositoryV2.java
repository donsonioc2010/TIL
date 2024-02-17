package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

    private final SpringDataJpaItemRepository itemRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        itemRepository.findById(itemId)
            .ifPresent(item -> {
                item.setItemName(updateParam.getItemName());
                item.setPrice(updateParam.getPrice());
                item.setQuantity(updateParam.getQuantity());
            });
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        if (StringUtils.hasText(cond.getItemName()) && cond.getMaxPrice() != null) {
//            return itemRepository.findAllByItemNameLikeAndPriceLessThanEqual(cond.getItemName(), cond.getMaxPrice());
            return itemRepository.findItems("%"+cond.getItemName()+"%", cond.getMaxPrice());
        } else if (StringUtils.hasText(cond.getItemName())) {
            return itemRepository.findAllByItemNameLike("%"+ cond.getItemName()+ "%");
        } else if (cond.getMaxPrice() != null) {
            return itemRepository.findAllByPriceLessThanEqual(cond.getMaxPrice());
        } else {
            return itemRepository.findAll();
        }
    }
}
