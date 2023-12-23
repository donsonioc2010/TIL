package com.jong1.service;

import com.jong1.domain.item.Item;
import com.jong1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findById(itemId);
        findItem.change(name, price, stockQuantity); // setter 대신 change 메서드를 만들어서 사용하는 것이 좋다. (핵심 비즈니스 로직이 들어가기 때문
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
