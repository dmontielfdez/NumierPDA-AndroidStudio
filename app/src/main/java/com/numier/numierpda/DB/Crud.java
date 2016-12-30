package com.numier.numierpda.DB;

import java.util.List;

public interface Crud<T> {

	public boolean insert(List<T> listObjects);

	public List<T> getAll();

}
