package it.uniroma3.diadia.giocatore;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class GiocatoreTest {
	
	private Giocatore g; 
	
	@Before
	public void setUp() throws Exception {
		g = new Giocatore();
	}

	@Test
	public void testGetCfu() {
		assertEquals(20, g.getCfu());
	}
	
	@Test
	public void testSetCfu() {
		g.setCfu(13);
		assertEquals(13, g.getCfu());
	}

}
