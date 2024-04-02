package io.bayrktlihn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bayrktlihn.component.Interface;
import io.bayrktlihn.component.MyComponent;
import io.bayrktlihn.config.AppConfig;
import io.bayrktlihn.dto.DistrictDto;
import io.bayrktlihn.dto.MunicipalityDto;
import io.bayrktlihn.dto.ProvinceDto;
import io.bayrktlihn.entity.Country;
import io.bayrktlihn.entity.District;
import io.bayrktlihn.entity.Municipality;
import io.bayrktlihn.entity.Neighbourhood;
import io.bayrktlihn.entity.Province;
import io.bayrktlihn.repository.CountryRepository;
import io.bayrktlihn.repository.DistrictRepository;
import io.bayrktlihn.repository.MunicipalityRepository;
import io.bayrktlihn.repository.NeighbourhoodRepository;
import io.bayrktlihn.repository.ProvinceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {

        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            expressionExample();
//            lookupExample();
//            listOrderedInjections(applicationContext);
//            listBeans(applicationContext);

            initializeDb(applicationContext);


//            MyComponent myComponent = applicationContext.getBean(MyComponent.class);
//
//            myComponent.run();
//
//            myComponent.walk();

        }


    }

    private static void initializeDb(AnnotationConfigApplicationContext applicationContext) throws IOException {
        AppConfig appConfig = applicationContext.getBean(AppConfig.class);
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);

        List<MunicipalityDto> municipalityDtos = objectMapper.readValue(appConfig.getResourceFile().getInputStream(), new TypeReference<List<MunicipalityDto>>() {
        });

        MunicipalityRepository municipalityRepository = applicationContext.getBean(MunicipalityRepository.class);
        ProvinceRepository provinceRepository = applicationContext.getBean(ProvinceRepository.class);
        CountryRepository countryRepository = applicationContext.getBean(CountryRepository.class);
        DistrictRepository districtRepository = applicationContext.getBean(DistrictRepository.class);
        NeighbourhoodRepository neighbourhoodRepository = applicationContext.getBean(NeighbourhoodRepository.class);


        for (MunicipalityDto municipalityDto : municipalityDtos) {
            Country country = new Country();
            country.setName(municipalityDto.getCountry());
            country = countryRepository.save(country);


            for (ProvinceDto provinceDto : municipalityDto.getProvinces()) {
                Province province = new Province();
                province.setName(provinceDto.getProvince());
                province.setCountry(country);
                province = provinceRepository.save(province);


                Municipality metropolitanMunicipality = null;
                if (provinceDto.isMetropolitan()) {
                    Municipality municipality = new Municipality();
                    municipality.setName(provinceDto.getProvince()+ " Büyükşehir Belediyesi");
                    municipality.setMetropolitan(true);
                    municipality.setProvince(province);
                    municipality = municipalityRepository.save(municipality);
                    metropolitanMunicipality = municipality;
                }



                for (DistrictDto districtDto : provinceDto.getDistricts()) {

                    Municipality municipality = new Municipality();

                    municipality.setMetropolitan(true);
                    municipality.setProvince(province);
                    municipality.setMetropolitanMunicipality(metropolitanMunicipality);

                    if(districtDto.isProvinceCenter() && metropolitanMunicipality == null){
                        municipality.setName(provinceDto.getProvince() + " Belediyesi");
                    } else {
                        municipality.setName(districtDto.getDistrict() + " Belediyesi");
                    }

                    municipality = municipalityRepository.save(municipality);

                    District district = new District();
                    district.setName(districtDto.getDistrict());
                    district.setProvince(province);
                    district = districtRepository.save(district);

                    for (String neighbourhoodName : districtDto.getNeighbourhoods()) {
                        Neighbourhood neighbourhood = new Neighbourhood();
                        neighbourhood.setName(neighbourhoodName);
                        neighbourhood.setDistrict(district);
                        neighbourhood = neighbourhoodRepository.save(neighbourhood);
                    }

                }

            }

        }
    }

    private static void listBeans(AnnotationConfigApplicationContext applicationContext) {
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            log.info(bean.toString());
        }
    }

    private static void listOrderedInjections(AnnotationConfigApplicationContext applicationContext) {
        MyComponent myComponent = applicationContext.getBean(MyComponent.class);
        List<Interface> interfaceList = myComponent.getInterfaceList();
        interfaceList.forEach(System.out::println);
    }

    private static void expressionExample() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'Hello World'");
        String expressionString = expression.getValue(String.class);
        System.out.println(expressionString);
    }

    private static void lookupExample() throws NamingException {
        InitialContext initialContext = new InitialContext();
        initialContext.lookup("java:/comp/env/jdbc/postgres");
    }
}