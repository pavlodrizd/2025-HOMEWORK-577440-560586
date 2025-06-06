package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;


public class FabbricaDiComandiFisarmonicaTest {
	
	private FabbricaDiComandiFisarmonica f;
	private Comando c;
	private IO io;

	@Before
	public void setUp() throws Exception {
	    f = new FabbricaDiComandiFisarmonica(io);
	}


	@Test
	public void testComandoNonValidoVuoto() {
		c = f.costruisciComando("");
		assertEquals("non valido", c.getNome());
		assertNull(c.getParametro());
	}
	
	@Test 
	public void testComandoNonValido() {
		c = f.costruisciComando("comandoinsesistente");
		assertEquals("non valido", c.getNome());
		assertNull(c.getParametro());
	}
	
	@Test
	public void testComandoVai() {
		c = f.costruisciComando("vai nord");
		assertEquals("vai", c.getNome());
		assertEquals("nord", c.getParametro());
	}
	
	@Test
	public void testComandoPrendi() {
		c = f.costruisciComando("prendi oggetto");
		assertEquals("prendi", c.getNome());
		assertEquals("oggetto", c.getParametro());

	}
	
	@Test
	public void testComandoPosa() {
		c = f.costruisciComando("posa oggetto");
		assertEquals("posa", c.getNome());
		assertEquals("oggetto", c.getParametro());

	}

	@Test
	public void testComandoAiuto() {
		c = f.costruisciComando("aiuto");
		assertEquals("aiuto", c.getNome());
		assertNull(c.getParametro());
	}
	
	@Test
	public void testComandoFine() {
		c = f.costruisciComando("fine");
		assertEquals("fine", c.getNome());
		assertNull(c.getParametro());

	}
	
	@Test
	public void testComandoGuarda() {
		c = f.costruisciComando("guarda");
		assertEquals("guarda", c.getNome());
		assertNull(c.getParametro());
	}

}
