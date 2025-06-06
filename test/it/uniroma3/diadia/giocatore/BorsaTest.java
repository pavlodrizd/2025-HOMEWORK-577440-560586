package it.uniroma3.diadia.giocatore;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class BorsaTest {
	
	private Borsa b;
	private Attrezzo a, l, tp, spada, chiave;

	@Before
	public void setUp() throws Exception {
		b = new Borsa();
		a = new Attrezzo("a", 1);
		l = new Attrezzo("l", 5);
		tp = new Attrezzo ("tp", 50);
		
		spada = new Attrezzo("spada", 3);
		chiave = new Attrezzo("chiave", 1);
	}

	@Test
	public void testAddAttrezzoLeggero() {
		assertTrue(b.addAttrezzo(l));
	}
	
	@Test
	public void testAddAttrezzoTroppoPesante() {
		assertFalse(b.addAttrezzo(tp));
	}
	
	@Test
	public void testGetPeso() {
		b.addAttrezzo(a);
		b.addAttrezzo(l);
		assertEquals(6, b.getPeso());
	}
	
	@Test
	public void testRemoveAttrezzo() {
		b.addAttrezzo(a);
		
		assertEquals(null, b.removeAttrezzo("F"));
		assertEquals(a, b.removeAttrezzo("a"));
	}
	
	@Test
	public void testGetContenutoOrdinatoPerPerso() {
		b.addAttrezzo(l);
		b.addAttrezzo(a);
		b.addAttrezzo(new Attrezzo("c", 3));
		
		assertEquals(b.getContenutoOrdinatoPerPeso().get(0), a);
		assertEquals(b.getContenutoOrdinatoPerPeso().get(1), b.getAttrezzo("c"));
		assertEquals(b.getContenutoOrdinatoPerPeso().get(2), l);		
	}
	
	@Test
	public void testGetContenutoOrdinatoPerNome() {
		b.addAttrezzo(l);
		b.addAttrezzo(a);
		b.addAttrezzo(new Attrezzo("c", 3));
		
		List<Attrezzo> list = new ArrayList<Attrezzo>(b.getContenutoOrdinatoPerNome());
		
		assertEquals(list.get(0), a);
		assertEquals(list.get(1), b.getAttrezzo("c"));
		assertEquals(list.get(2), l);		
	}
	
	@Test
	public void testGetContenutoRaggruppatoPerPeso() {
		b.addAttrezzo(a); 
		b.addAttrezzo(l);  
		b.addAttrezzo(spada);  
		b.addAttrezzo(chiave);

		Map<Integer, Set<Attrezzo>> raggruppamento = b.getContenutoRaggruppatoPerPeso();

		assertEquals(3, raggruppamento.size());

		assertTrue(raggruppamento.containsKey(1));
		Set<Attrezzo> attrezziPeso1 = raggruppamento.get(1);
		assertEquals(2, attrezziPeso1.size());
		assertTrue(attrezziPeso1.contains(a));
		assertTrue(attrezziPeso1.contains(chiave));

		assertTrue(raggruppamento.containsKey(3));
		Set<Attrezzo> attrezziPeso3 = raggruppamento.get(3);
		assertEquals(1, attrezziPeso3.size()); 
		assertTrue(attrezziPeso3.contains(spada));


		assertTrue(raggruppamento.containsKey(5));
		Set<Attrezzo> attrezziPeso5 = raggruppamento.get(5);
		assertEquals(1, attrezziPeso5.size());
		assertTrue(attrezziPeso5.contains(l));
	}
	
	
	@Test
	public void testGetSortedSetOrdinatoPerPeso() {
	    b.addAttrezzo(l);   
	    b.addAttrezzo(a); 
	    b.addAttrezzo(spada); 
	    b.addAttrezzo(chiave);

	    SortedSet<Attrezzo> sortedSetPerPeso = b.getSortedSetOrdinatoPerPeso();
	    List<Attrezzo> listaOrdinata = new ArrayList<>(sortedSetPerPeso);

	    assertEquals(4, listaOrdinata.size());
	    assertEquals(a, listaOrdinata.get(0));
	    assertEquals(chiave, listaOrdinata.get(1));
	    assertEquals(spada, listaOrdinata.get(2));
	    assertEquals(l, listaOrdinata.get(3));
	}

}
