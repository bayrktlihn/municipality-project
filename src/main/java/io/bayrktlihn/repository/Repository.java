package io.bayrktlihn.repository;

import java.util.List;

public interface Repository<TENTITY, TID> {

    List<? extends TENTITY> findAll();

    TENTITY findById(TID id);

    void deleteById(TID id);

    TENTITY save(TENTITY entity);


}
