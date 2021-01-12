package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.Tier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TierRepository extends JpaRepository<Tier,Long> {
    List<Tier> findAllByScreenId(Long screenId);
}
