package com.akash.urlshortener.Repo;
import com.akash.urlshortener.Models.URL_Record;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShortURLRepo extends JpaRepository<URL_Record,Long> {
}
