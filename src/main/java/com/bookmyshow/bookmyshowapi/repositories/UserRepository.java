package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByMobile(String mobile);
}
