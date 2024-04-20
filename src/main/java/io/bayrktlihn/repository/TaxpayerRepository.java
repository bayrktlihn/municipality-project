package io.bayrktlihn.repository;

import io.bayrktlihn.entity.Taxpayer;

import java.util.List;

public interface TaxpayerRepository extends Repository<Taxpayer, Long> {



    List<Taxpayer> findAllRealTaxpayers();



}
