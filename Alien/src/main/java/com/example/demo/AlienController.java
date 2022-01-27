package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienController
{
	@Autowired
	AlienRepo repo;

	@RequestMapping("/")
	public String home()
	{
		return "home.jsp";
	}
	
	@DeleteMapping("/deletealien/{aid}")
	public String deleteAlien(@PathVariable int aid)
	{
		Aliens a = repo.getById(aid);
		repo.delete(a);
		return "deleted";
	}
	
	@PostMapping(path="/addalien",consumes= {"application/json"})
	public Aliens addAlien(@RequestBody Aliens alien)
	{
		repo.save(alien);
		return alien;
	}
	@GetMapping(path="/getaliens")
	public List<Aliens> getAliens()
	{
		return repo.findAll();
			
	}
	@PutMapping(path="/updatealien",consumes= {"application/json"})
	public Aliens saveOrUpdateAlien(@RequestBody Aliens alien)
	{
		repo.save(alien);
		return alien;
	}
	
	
	@RequestMapping("/findalien/{aid}")
	public Optional<Aliens> getAlien(@PathVariable("aid")int aid)
	{
		return repo.findById(aid);
		
		
	}
}