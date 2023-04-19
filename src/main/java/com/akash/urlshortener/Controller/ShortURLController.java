package com.akash.urlshortener.Controller;

import com.akash.urlshortener.Models.URL_Record;
import com.akash.urlshortener.Repo.ShortURLRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class ShortURLController {

	private final String ShortUrlDomain = "localhost:9001/s/";
	private final String ClientDomain = "http://localhost:3000";


	@Autowired
	private ShortURLRepo shortURLRepo;

	@GetMapping(value= "/")
	public String getPage(){
		return "Welcome to Akash's URL Shortener. Please make a get request to /shortenurl";
	}

	@CrossOrigin(origins = ClientDomain)
	@PostMapping(value="/shortenurl")
	public ResponseEntity<Object> shortenURL(@RequestBody URL_Record url_record) throws MalformedURLException {
		if(url_record!=null) {
			System.out.println("Received Shorten Request= " + url_record.getFull_url());
			url_record.setFull_url(sanitizeURL(url_record.getFull_url()));
			System.out.println("Sanitized Shorten Request = " + url_record.getFull_url());
			URL_Record db_match_url = findShortUrlByUrl(url_record.getFull_url());
			if (db_match_url == null) {
				String randomString = getRandomChars();
				url_record.setShort_url(ShortUrlDomain+randomString);
				shortURLRepo.save(url_record);
			}
			else
				url_record.setShort_url(db_match_url.getShort_url());;
	
			return new ResponseEntity<Object>(url_record, HttpStatus.OK);
		}
		else
			return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin(origins = ClientDomain)
	@PostMapping(value="/expandurl")
	@Description("Expand the Short URL")
	public ResponseEntity<Object> expandURL(@RequestBody URL_Record url_record) throws MalformedURLException {
		if(url_record!=null) {

			System.out.println("Received Expand Request= " + url_record.getShort_url());
			url_record.setShort_url(sanitizeURL(url_record.getShort_url()));
			System.out.println("Sanitized Expand Request = " + url_record.getShort_url());
			String expandedUrl = findUrlByShortUrl(url_record.getShort_url());
			if (expandedUrl == null) {
				return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);

			} else {
				url_record.setFull_url(expandedUrl);
				return new ResponseEntity<Object>(url_record, HttpStatus.OK);
			}
		}
		else
			return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin(origins = ClientDomain)
	@GetMapping(value="/s/{shorturl}")
	public void redirectShortURL(HttpServletResponse response, @PathVariable("shorturl") String shortUrl) throws IOException {
		if(shortUrl == null || shortUrl == ""){
			System.out.println(" Error Request");
			response.addHeader("Location", ClientDomain );
			response.setContentType("text/html");
			response.setStatus(302);
			response.sendRedirect(ClientDomain);
		}
		shortUrl= ShortUrlDomain+shortUrl;
		System.out.println("getFullUrl::shorturl= "+ shortUrl);
		URL_Record URLRecord = new URL_Record();
		URLRecord.setShort_url(shortUrl);
		URLRecord.setFull_url("");
		String fullUrl= findUrlByShortUrl(URLRecord.getShort_url());
		if(fullUrl != null) {
			System.out.println(" fullurl=" + fullUrl);
			response.addHeader("Location", "http://" + fullUrl);
			response.setContentType("text/html");
			response.setStatus(302);
			response.sendRedirect("http://" + fullUrl);
		}
		else {
			System.out.println(" Error Request");
			response.addHeader("Location", ClientDomain );
			response.setContentType("text/html");
			response.setStatus(302);
			response.sendRedirect(ClientDomain);
		}
	}

	private String findUrlByShortUrl(String shortUrl)
	{
		List<URL_Record> URLRecordList = shortURLRepo.findAll();
		String longUrl= null;
		for (URL_Record su  : URLRecordList)
		{
			if(su.getShort_url().equals(shortUrl))
				longUrl= su.getFull_url();
		}
		return longUrl;
	}
	private URL_Record findShortUrlByUrl(String url)
	{
		List<URL_Record> URLRecordList = shortURLRepo.findAll();
		URL_Record urlRecord = null;
		for (URL_Record su  : URLRecordList)
		{
			if(su.getFull_url().equals(url))
				urlRecord = su;
		}
		return urlRecord;
	}

	private String getRandomChars() {
		String randomStr = "";
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < 5; i++)
			randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
		return randomStr;
	}
	private String sanitizeURL(String url) {
		if (url.length()>=7 && url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.length()>=8 && url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}
	
}
