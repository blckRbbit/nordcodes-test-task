package com.blck_rbbt.nordCodes.repositories;

import com.blck_rbbt.nordCodes.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByHash(String hash);
    ShortUrl getByHash(String hash);
    void deleteByHash(String hash);
}
