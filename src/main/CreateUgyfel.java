package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.UgyfelDao;
import dao.data.Dokumentum;
import dao.data.Jogi;
import dao.data.Termeszetes;
import dao.data.Type;

public class CreateUgyfel {

	private static UgyfelDao dao = new UgyfelDao();

	public static void main(String[] args) {
		List<Dokumentum> documents = new ArrayList<>();
		Dokumentum dokumentum = new Dokumentum();
		dokumentum.setIssueDate(new Date());
		dokumentum.setNumber("number");
		Type type = new Type();
		type.setCode("code");
		type.setUnique(true);
		dokumentum.setType(type);
		documents.add(dokumentum);

		Termeszetes termeszetes = new Termeszetes();
		termeszetes.setFirstName("Tamás");
		termeszetes.setLastName("Munkácsi");
		termeszetes.setCustomer(true);
		termeszetes.setLastIdentified(new Date());
		termeszetes.setBirthDate(new Date(639871200000l));
		termeszetes.setDocuments(documents);

		Jogi jogi = new Jogi();
		jogi.setName("Munkácsi kft.");
		jogi.setShortName("Munk kft.");
		jogi.setCustomer(true);
		jogi.setLastIdentified(new Date());
		jogi.setEstablishmentDate(new Date(639871200000l));
		jogi.setDocuments(documents);

		dao.saveUgyfel(termeszetes);
		dao.saveUgyfel(jogi);
	}
}
