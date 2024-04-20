package io.bayrktlihn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bayrktlihn.component.Interface;
import io.bayrktlihn.component.MyComponent;
import io.bayrktlihn.config.AppConfig;
import io.bayrktlihn.dto.DistrictDto;
import io.bayrktlihn.dto.MunicipalityDto;
import io.bayrktlihn.dto.ProvinceDto;
import io.bayrktlihn.entity.Accrual;
import io.bayrktlihn.entity.Country;
import io.bayrktlihn.entity.District;
import io.bayrktlihn.entity.Municipality;
import io.bayrktlihn.entity.Neighbourhood;
import io.bayrktlihn.entity.Province;
import io.bayrktlihn.entity.RealTaxpayer;
import io.bayrktlihn.entity.Taxpayer;
import io.bayrktlihn.enums.Gender;
import io.bayrktlihn.repository.CountryRepository;
import io.bayrktlihn.repository.DistrictRepository;
import io.bayrktlihn.repository.MunicipalityRepository;
import io.bayrktlihn.repository.NeighbourhoodRepository;
import io.bayrktlihn.repository.ProvinceRepository;
import io.bayrktlihn.repository.TaxpayerRepository;
import io.bayrktlihn.service.AccrualService;
import io.bayrktlihn.util.date.Dates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {

        try (AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            expressionExample();
//            lookupExample();
//            listOrderedInjections(applicationContext);
//            listBeans(applicationContext);

//            initializeDb(applicationContext);
//
//            TaxpayerRepository taxpayerRepository = applicationContext.getBean(TaxpayerRepository.class);
//            List<Taxpayer> allRealTaxpayers = taxpayerRepository.findAllRealTaxpayers();


            Accrual accrual = new Accrual();
            accrual.setAmount(new BigDecimal("100"));
            accrual.setPaymentDueDate(Dates.create(2021, 1, 1));

            AccrualService bean = applicationContext.getBean(AccrualService.class);
            BigDecimal bigDecimal = bean.calculateTotalAmount(accrual);
            System.out.println(bigDecimal);


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
        TaxpayerRepository taxpayerRepository = applicationContext.getBean(TaxpayerRepository.class);


        for (MunicipalityDto municipalityDto : municipalityDtos) {
            Country country = new Country();
            country.setName(municipalityDto.getCountry());
            country = countryRepository.save(country);


            for (ProvinceDto provinceDto : municipalityDto.getProvinces()) {
                Province province = new Province();
                province.setName(provinceDto.getProvince());
                province.setCountry(country);
                province = provinceRepository.save(province);


                Municipality metropolitanMunicipality;
                if (provinceDto.isMetropolitan()) {
                    Municipality municipality = new Municipality();
                    municipality.setName(provinceDto.getProvince() + " Büyükşehir Belediyesi");
                    municipality.setMetropolitan(true);
                    municipality.setProvince(province);
                    municipality = municipalityRepository.save(municipality);
                    metropolitanMunicipality = municipality;
                } else {
                    metropolitanMunicipality = null;
                }

                Optional<DistrictDto> provinceCenterDistrict = provinceDto.getDistricts().stream().filter(item -> item.isProvinceCenter() && metropolitanMunicipality == null).findFirst();

                Municipality provinceCeMunicipality = null;
                if (provinceCenterDistrict.isPresent()) {
                    District district = new District();
                    district.setName(provinceCenterDistrict.get().getDistrict());
                    district.setProvince(province);
                    district = districtRepository.save(district);

                    Municipality municipality = new Municipality();
                    municipality.setMetropolitan(false);
                    municipality.setProvince(province);
                    municipality.setMetropolitanMunicipality(metropolitanMunicipality);
                    municipality.setDistrict(district);
                    municipality.setName(provinceDto.getProvince() + " Belediyesi");
                    municipality.setProvinceCenter(true);
                    provinceCeMunicipality = municipalityRepository.save(municipality);
                }

                List<DistrictDto> notProvinceCenterDistricts = provinceDto.getDistricts().stream().filter(item -> !item.isProvinceCenter()).toList();
                for (DistrictDto districtDto : notProvinceCenterDistricts) {
                    District district = new District();
                    district.setName(districtDto.getDistrict());
                    district.setProvince(province);
                    district = districtRepository.save(district);

                    Municipality municipality = new Municipality();

                    municipality.setMetropolitan(false);
                    municipality.setProvince(province);
                    municipality.setMetropolitanMunicipality(metropolitanMunicipality);
                    municipality.setDistrict(district);
                    municipality.setName(districtDto.getDistrict() + " Belediyesi");
                    municipality.setProvinceCenterMunicipality(provinceCeMunicipality);
                    municipality = municipalityRepository.save(municipality);


                    for (String neighbourhoodName : districtDto.getNeighbourhoods()) {
                        Neighbourhood neighbourhood = new Neighbourhood();
                        neighbourhood.setName(neighbourhoodName);
                        neighbourhood.setDistrict(district);
                        neighbourhood = neighbourhoodRepository.save(neighbourhood);
                    }
                }


            }

        }


        RealTaxpayer realTaxpayer = new RealTaxpayer();
        realTaxpayer.setFirstName("kerim");
        realTaxpayer.setLastName("yılmaz");
        realTaxpayer.setGender(Gender.MAN);
        realTaxpayer.setFatherName("ferhat");
        realTaxpayer.setMotherName("yasemin");
        realTaxpayer.setBirthDate(Dates.createStartOfDay(1996, 8, 20));
        realTaxpayer.setIdentificationNumber("20108748475");

        Taxpayer taxpayer = new Taxpayer();
        taxpayer.setRegistrationNumber("20108748475");
        taxpayer.setRealTaxpayer(realTaxpayer);

        taxpayerRepository.save(taxpayer);


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