package io.bayrktlihn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class DistrictDto {
    private String district;
    private List<String> neighbourhoods = new ArrayList<>();
    private boolean provinceCenter;
}
