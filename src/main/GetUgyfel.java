package main;

import dao.UgyfelDao;

public class GetUgyfel {

	private static UgyfelDao dao = new UgyfelDao();

	public static void main(String[] args) {
		System.out.println(dao.getUgyfel(9));
		System.out.println(dao.getUgyfel(10));
	}

}
