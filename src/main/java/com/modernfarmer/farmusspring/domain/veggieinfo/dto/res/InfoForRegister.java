package com.modernfarmer.farmusspring.domain.veggieinfo.dto.res;

import com.modernfarmer.farmusspring.domain.veggieinfo.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


public record InfoForRegister (
    String _id,
    String name,
    Difficulty difficulty,
    String veggieImage,
    String period
){
}
