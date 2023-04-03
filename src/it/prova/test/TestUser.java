package it.prova.test;

import java.nio.channels.AcceptPendingException;
import java.time.LocalDate;
import java.util.List;

import it.prova.model.User;
import it.prova.service.MyServiceFactory;
import it.prova.service.user.UserService;

public class TestUser {

	public static void main(String[] args) {

		// parlo direttamente con il service
		UserService userService = MyServiceFactory.getUserServiceImpl();

		try {

			// ora con il service posso fare tutte le invocazioni che mi servono
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
//
//			testInserimentoNuovoUser(userService);
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
//
//			testRimozioneUser(userService);
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
//
//			testFindByExample(userService);
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
//
//			testUpdateUser(userService);
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
//			
//			testCercaTuttiQuelliCreatiPrimaDi(userService);
//			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			
			testCercaTuttiQuelliCheUsernameIniziaCon(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			
			testCercaPerCognomeENomeCheInziaCon(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");
			
			testAccedi(userService);
			System.out.println("In tabella ci sono " + userService.listAll().size() + " elementi.");

			// E TUTTI I TEST VANNO FATTI COSI'

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testInserimentoNuovoUser(UserService userService) throws Exception {
		System.out.println(".......testInserimentoNuovoUser inizio.............");
		User newUserInstance = new User("mauro", "rossi", "avavv", "bobobo", LocalDate.now());
		if (userService.inserisciNuovo(newUserInstance) != 1)
			throw new RuntimeException("testInserimentoNuovoUser FAILED ");

		System.out.println(".......testInserimentoNuovoUser PASSED.............");
	}

	private static void testRimozioneUser(UserService userService) throws Exception {
		System.out.println(".......testRimozioneUser inizio.............");
		// recupero tutti gli user
		List<User> interoContenutoTabella = userService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idDelPrimo = interoContenutoTabella.get(0).getId();
		// ricarico per sicurezza con l'id individuato
		User toBeRemoved = userService.findById(idDelPrimo);
		if (userService.rimuovi(toBeRemoved.getId()) != 1)
			throw new RuntimeException("testRimozioneUser FAILED ");

		System.out.println(".......testRimozioneUser PASSED.............");
	}

	private static void testFindByExample(UserService userService) throws Exception {
		System.out.println(".......testFindByExample inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		userService.inserisciNuovo(new User("Asallo", "Bianchi", "pier", "pwd@1", LocalDate.now()));
		userService.inserisciNuovo(new User("Astolfo", "Verdi", "ast", "pwd@2", LocalDate.now()));

		// preparo un example che ha come nome 'as' e ricerco
		List<User> risultatifindByExample = userService.findByExample(new User("as"));
		if (risultatifindByExample.size() != 2)
			throw new RuntimeException("testFindByExample FAILED ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.
		for (User userItem : risultatifindByExample) {
			userService.rimuovi(userItem.getId());
		}

		System.out.println(".......testFindByExample PASSED.............");
	}

	private static void testUpdateUser(UserService userService) throws Exception {
		System.out.println(".......testUpdateUser inizio.............");

		// inserisco i dati che poi modifico
		if (userService.inserisciNuovo(new User("Giovanna", "Sastre", "gio", "pwd@3", LocalDate.now())) != 1)
			throw new RuntimeException("testUpdateUser: inserimento preliminare FAILED ");

		// recupero col findbyexample e mi aspetto di trovarla
		List<User> risultatifindByExample = userService.findByExample(new User("Giovanna", "Sastre"));
		if (risultatifindByExample.size() != 1)
			throw new RuntimeException("testUpdateUser: testFindByExample FAILED ");

		// mi metto da parte l'id su cui lavorare per il test
		Long idGiovanna = risultatifindByExample.get(0).getId();

		// ricarico per sicurezza con l'id individuato e gli modifico un campo
		String nuovoCognome = "Perastra";
		User toBeUpdated = userService.findById(idGiovanna);
		toBeUpdated.setCognome(nuovoCognome);
		if (userService.aggiorna(toBeUpdated) != 1)
			throw new RuntimeException("testUpdateUser FAILED ");

		System.out.println(".......testUpdateUser PASSED.............");
	}
	
	private static void testCercaTuttiQuelliCreatiPrimaDi(UserService userService) throws Exception {
		System.out.println(".......testCercaTuttiQuelliCreatiPrimaDi inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		userService.inserisciNuovo(new User("Pino", "Salemi", "Pino87", "pwd@1", LocalDate.parse("2021-01-01")));
//		userService.inserisciNuovo(new User("Astolfo", "Verdi", "ast", "pwd@2", LocalDate.now()));

		// preparo un example che ha come nome 'as' e ricerco
		List<User> risultatiMetodo = userService.cercaTuttiQuelliCreatiPrimaDi(LocalDate.parse("2022-01-01"));
		if (risultatiMetodo.size() < 1)
			throw new RuntimeException("testCercaTuttiQuelliCreatiPrimaDi FAILED ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.
		for (User userItem : risultatiMetodo) {
			userService.rimuovi(userItem.getId());
		}

		System.out.println(".......testCercaTuttiQuelliCreatiPrimaDi PASSED.............");
	}

	private static void testCercaTuttiQuelliCheUsernameIniziaCon(UserService userService) throws Exception {
		System.out.println(".......testCercaTuttiQuelliCheUsernameIniziaCon inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		userService.inserisciNuovo(new User("Zaira", "Salemi", "Zaza87", "pwd@1", LocalDate.parse("2021-01-01")));
//		userService.inserisciNuovo(new User("Astolfo", "Verdi", "ast", "pwd@2", LocalDate.now()));

		// preparo un example che ha come nome 'as' e ricerco
		List<User> risultatiMetodo = userService.cercaTuttiQuelliCheUsernameIniziaCon("z");
		if (risultatiMetodo.size() < 1)
			throw new RuntimeException("testCercaTuttiQuelliCheUsernameIniziaCon FAILED ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.
		for (User userItem : risultatiMetodo) {
			userService.rimuovi(userItem.getId());
		}

		System.out.println(".......testCercaTuttiQuelliCheUsernameIniziaCon PASSED.............");
	}
	
	private static void testCercaPerCognomeENomeCheInziaCon(UserService userService) throws Exception {
		System.out.println(".......testCercaPerCognomeENomeCheInziaCon inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		userService.inserisciNuovo(new User("Zaira", "Salemi", "Zaza87", "pwd@1", LocalDate.parse("2021-01-01")));
//		userService.inserisciNuovo(new User("Astolfo", "Verdi", "ast", "pwd@2", LocalDate.now()));

		// preparo un example che ha come nome 'as' e ricerco
		List<User> risultatiMetodo = userService.cercaPerCognomeENomeCheInziaCon("Salemi","Z");
		if (risultatiMetodo.size() < 1)
			throw new RuntimeException("testCercaPerCognomeENomeCheInziaCon FAILED ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.
		for (User userItem : risultatiMetodo) {
			userService.rimuovi(userItem.getId());
		}

		System.out.println(".......testFindByExample PASSED.............");
	}
	
	private static void testAccedi(UserService userService) throws Exception {
		System.out.println(".......testCercaPerCognomeENomeCheInziaCon inizio.............");
		// inserisco i dati che poi mi aspetto di ritrovare
		userService.inserisciNuovo(new User("Zaira", "Salemi", "Zaza87", "pwd@1", LocalDate.parse("2021-01-01")));
//		userService.inserisciNuovo(new User("Astolfo", "Verdi", "ast", "pwd@2", LocalDate.now()));

		// preparo un example che ha come nome 'as' e ricerco
		User risultatoMetodo = userService.accedi("Zaza87","pwd@1");
		if (risultatoMetodo == null )
			throw new RuntimeException("testCercaPerCognomeENomeCheInziaCon FAILED ");

		// se sono qui il test è ok quindi ripulisco i dati che ho inserito altrimenti
		// la prossima volta non sarebbero 2 ma 4, ecc.

			userService.rimuovi(risultatoMetodo.getId());
		

		System.out.println(".......testFindByExample PASSED.............");
	}
	
}
