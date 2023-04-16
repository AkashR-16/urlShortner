package com.akash.urlshortener.Repo;
import com.akash.urlshortener.Models.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShortURLRepo extends JpaRepository<ShortURL,Long> {
}
