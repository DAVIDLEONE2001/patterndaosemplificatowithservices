package it.prova.service.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import it.prova.dao.user.UserDAO;
import it.prova.model.User;

public interface UserService {

	// questo mi serve per injection
	public void setUserDao(UserDAO userDao);

	public List<User> listAll() throws Exception;

	public User findById(Long idInput) throws Exception;

	public int aggiorna(User input) throws Exception;

	public int inserisciNuovo(User input) throws Exception;

	public int rimuovi(Long idDaRimouovere) throws Exception;

	public List<User> findByExample(User input) throws Exception;
	
	//##########################################################################################
	//DA FARE PER ESERCIZIO: OVVIAMENTE BISOGNA RICREARE LA CONTROPARTE IN UserDAO e UserDAOImpl
	public List<User> cercaTuttiQuelliCheUsernameIniziaCon(String iniziale) throws Exception; //testato
	
	public List<User> cercaTuttiQuelliCreatiPrimaDi(LocalDate dataConfronto) throws Exception; //testato
	
	public List<User> cercaPerCognomeENomeCheInziaCon(String cognomeInput, String inzialeNomeInput) throws Exception; //testato

	public User accedi(String loginInput, String passwordInput) throws Exception;
}
