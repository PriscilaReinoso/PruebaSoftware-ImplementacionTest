package com.grupoK.Tp1SistemasDistribuidos.test;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.grupoK.Tp1SistemasDistribuidos.entities.Rol;
import com.grupoK.Tp1SistemasDistribuidos.entities.Usuario;
import com.grupoK.Tp1SistemasDistribuidos.enums.TipoRoles;
import com.grupoK.Tp1SistemasDistribuidos.exceptions.UserEmailAlreadyExistsException;
import com.grupoK.Tp1SistemasDistribuidos.exceptions.UserUsernameAlreadyExistsException;
import com.grupoK.Tp1SistemasDistribuidos.repositories.IUsuarioRepository;
import com.grupoK.Tp1SistemasDistribuidos.serviceImp.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@Mock
	private IUsuarioRepository usuarioResposity;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	@Test
	public void agregarUsuarioOK() {
		Usuario usuarioIsertar = new Usuario(0, "pri15", "mypass", "priscila@gmail.com",
				"priscila", "reinoso", "1122334455", null, null, null, null, new Rol(1, TipoRoles.PRESIDENTE));
		
		//Stubs
	    Mockito.when(usuarioResposity.findByUsername("pri15")).thenReturn(Optional.empty());
	    Mockito.when(usuarioResposity.findByEmail("priscila@gmail.com")).thenReturn(Optional.empty());
	    Mockito.when(usuarioResposity.save(usuarioIsertar)).thenReturn(usuarioIsertar);
	    
	    //Test
		Usuario usuarioInsertado = usuarioService.saveOrUpdate(usuarioIsertar);
		
		//Assert
		Assertions.assertEquals(usuarioIsertar, usuarioInsertado);
		
		// Verify
		Mockito.verify(usuarioResposity).findByUsername("pri15");
		Mockito.verify(usuarioResposity).findByEmail("priscila@gmail.com");
		Mockito.verify(usuarioResposity).save(usuarioIsertar);
	}
	
	@Test
	public void agregarUsuarioUsernameExistente() {
		Usuario usuarioExistente = new Usuario(0, "pri15", "mypass", "priscila@gmail.com",
				"priscila", "reinoso", "1122334455", null, null, null, null, new Rol(1, TipoRoles.PRESIDENTE));
		
		//Stubs
	    Mockito.when(usuarioResposity.findByUsername("pri15")).thenReturn(Optional.of(usuarioExistente));
	    
	    //Assert
	    Assertions.assertThrows(UserUsernameAlreadyExistsException.class, 
	    			() -> usuarioService.saveOrUpdate(usuarioExistente));
	    
	    // Verify
	    Mockito.verify(usuarioResposity).findByUsername("pri15");
	    Mockito.verify(usuarioResposity.save(usuarioExistente));
	}
	
	@Test
	public void agregarUsuarioEmailExistente() {
		Usuario usuarioExistente = new Usuario(0, "pri15", "mypass", "priscila@gmail.com",
				"priscila", "reinoso", "1122334455", null, null, null, null, new Rol(1, TipoRoles.PRESIDENTE));
		
		//Stubs
	    Mockito.when(usuarioResposity.findByEmail("priscila@gmail.com")).thenReturn(Optional.of(usuarioExistente));
	    
	    //Assert
	    Assertions.assertThrows(UserEmailAlreadyExistsException.class, 
	    			() -> usuarioService.saveOrUpdate(usuarioExistente));
	   
	    // Verify
	    Mockito.verify(usuarioResposity).findByUsername("pri15");
	    Mockito.verify(usuarioResposity, Mockito.never()).save(Mockito.any());
	}
}
