package com.nowstartjava.tutorials.service;

import java.util.List;

import com.nowstartjava.tutorials.model.Tutorials;

public interface TutorialService {

	public Tutorials findOne(int id);

	public List<Tutorials> findAll();

	public void save(Tutorials tutorials);

	public void delete(Tutorials tutorials);

	public List<Tutorials> findAllByCategoryId(Integer id);

	public Tutorials findOneBySlug(String slug);

	public List<Tutorials> findAllByWriterId(Integer id);

	public void delete(Integer id);

	public void update(Tutorials tutorial);
}
