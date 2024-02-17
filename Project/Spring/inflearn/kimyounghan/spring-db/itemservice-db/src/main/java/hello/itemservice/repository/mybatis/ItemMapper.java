package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemMapper {

    void save(Item item);

    //2개 이상인 경우 @Param을 사용한다.
    void update(@Param("id") Long id, @Param("updateParam")ItemUpdateDto updateDto);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearch);

}
