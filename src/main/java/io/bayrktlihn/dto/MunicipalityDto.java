package io.bayrktlihn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MunicipalityDto {
    private String country;

    private List<ProvinceDto> provinces;
}
