package com.akash.urlshortener.Controller;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.akash.urlshortener.Repo.ShortURLRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.akash.urlshortener.Models.ShortURL;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ShortURLController {

	@Autowired
	private ShortURLRepo shortURLRepo;
    private Map<String, ShortURL> shortenUrlList = new HashMap<>();

	@GetMapping(value= "/")
	public String getPage(){
		return "Welcome";
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value="/shortenurl")
	public ResponseEntity<Object> getShortenUrl(@RequestBody ShortURL shortenUrl) throws MalformedURLException {
		System.out.println("getShortenUrl::received Request"+ shortenUrl);
		String randomChar = getRandomChars();
		shortenUrl.setShort_url(randomChar);
		setShortUrl(randomChar, shortenUrl);
		List<ShortURL> shortURLList = shortURLRepo.findAll();

		shortURLRepo.save(shortenUrl);
		shortURLList = shortURLRepo.findAll();

		return new ResponseEntity<Object>(shortenUrl, HttpStatus.OK);
	}
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value="/s/{shorturl}")
	public void getFullUrl(HttpServletResponse response, @PathVariable("shorturl") String shorturl) throws IOException {
		System.out.println("getFullUrl::shorturl="+ shorturl);
		response.setHeader("Location", shorturl);
		String fullUrl= shortenUrlList.get(shorturl).getFull_url();
		ShortURL shortURL = new ShortURL();
		shortURL.setShort_url(shorturl);
		shortURL.setFull_url("");
		List<ShortURL> shortURLList = shortURLRepo.findAll();
		String longUrl= "";
		for (ShortURL su  : shortURLList)
		{
			if(su.getShort_url().equals(shortURL.getShort_url()))
				shortURL.setFull_url(su.getFull_url());
		}
		System.out.println("fullUrl="+ fullUrl);
		System.out.println("dbShortUrl fullurl="+ shortURL.getFull_url());

		response.setStatus(302);

		response.sendRedirect(fullUrl);

	}

	private void setShortUrl(String randomChar, ShortURL shortenUrl) throws MalformedURLException {
		 shortenUrl.setShort_url(randomChar);
		 shortURLRepo.save(shortenUrl);
		 shortenUrlList.put(randomChar, shortenUrl);
	}

	private String getRandomChars() {
		String randomStr = "";
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < 5; i++)
			randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
		return randomStr;
	}
	
}
