package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaTest {

	private StanzaMagica sm;
	private Attrezzo a1;

	@Before
	public void setUp() throws Exception {
		sm = new StanzaMagica("SM");
		a1 = new Attrezzo("Bastone", 4);
		
	}
	
	@Test
	public void testModificaAttrezzoRaddoppiaPeso() {
		assertEquals(8, sm.modificaAttrezzo(a1).getPeso());
	}

	@Test
	public void testModificaAttrezzoNome() {
		assertEquals("enotsaB", sm.modificaAttrezzo(a1).getNome());		
	}
	
	@Test
	public void testAddAttrezzo() {
		assertTrue(sm.addAttrezzo(a1));
	}
	
	@Test
	public void testAddAttrezzoSogliaSuperata() {
		sm.addAttrezzo(a1);
		assertTrue(sm.hasAttrezzo("Bastone"));
		sm.removeAttrezzo(a1);
		
		sm.addAttrezzo(a1);
		assertTrue(sm.hasAttrezzo("Bastone"));
		sm.removeAttrezzo(a1);
		
		sm.addAttrezzo(a1);
		assertTrue(sm.hasAttrezzo("Bastone"));
		sm.removeAttrezzo(a1);
		
		sm.addAttrezzo(a1);
		assertTrue(sm.hasAttrezzo("enotsaB"));
		
		
	}
	
	

}
