package io.bayrktlihn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProvinceDto {

    private String province;

    private boolean metropolitan;

    private List<DistrictDto> districts;

}
