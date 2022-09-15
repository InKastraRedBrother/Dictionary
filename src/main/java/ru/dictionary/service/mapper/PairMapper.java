package ru.dictionary.service.mapper;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.dictionary.model.Row;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;

import java.util.List;
import java.util.UUID;

@Data
@Component
public class PairMapper {
    Row row;


    public String fromDTOtoEntity(RequestAddPairWordsDTO requestAddPairWordsDTO) {

        Row row = new Row();
        row.setIdRow(UUID.randomUUID().toString());
        row.setIdWordKey(requestAddPairWordsDTO.getWordValue());
        row.setIdWordValue(requestAddPairWordsDTO.getWordValue());

        return "";
    }
}
