package main;

import java.util.Date;

import dao.UgyfelDao;

public class GetDocuments {

	private static UgyfelDao dao = new UgyfelDao();

	public static void main(String[] args) {
		System.out.println(dao.getDocuments(new Date()));
		System.out.println(dao.getDocuments(new Date(1426892400000l)));
	}
}
