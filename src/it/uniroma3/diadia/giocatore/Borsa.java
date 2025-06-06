package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import it.uniroma3.diadia.Configuratore;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa{
	public final static int DEFAULT_PESO_MAX_BORSA = Configuratore.getPesoMax();
	private Map<String, Attrezzo> attrezzi;
	private int pesoMax;

	public Borsa() {
		this(DEFAULT_PESO_MAX_BORSA);
	}

	public Borsa(int pesoMax) {
		this.pesoMax = pesoMax;
		this.attrezzi = new HashMap<>();
	}

	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
			return false;

		else {
			this.attrezzi.put(attrezzo.getNome(), attrezzo);
			return true;
		}
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		
		return this.attrezzi.get(nomeAttrezzo);
		
	}

	public int getPeso() {
		int peso = 0;

		for(Attrezzo attrezzo : attrezzi.values()) {
			peso += attrezzo.getPeso();
		}

		return peso;
	}

	public boolean isEmpty() {
		return attrezzi.isEmpty();
	}

	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}

	public Attrezzo removeAttrezzo(String nomeAttrezzo) throws NoSuchElementException {
		
		return this.attrezzi.remove(nomeAttrezzo);
		
	}
	
	public List<Attrezzo> getContenutoOrdinatoPerPeso(){
		List<Attrezzo> l = new ArrayList<Attrezzo>(this.attrezzi.values());
		Collections.sort (l, new Comparator<Attrezzo>() {
			@Override
			public int compare(Attrezzo a1, Attrezzo a2) {
				if(a1.getPeso()==a2.getPeso()) {
					return a1.getNome().compareTo(a2.getNome());
				}
				else
					return a1.getPeso()-a2.getPeso();
			}
		});
		return l;
	}
	
	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		SortedSet<Attrezzo> s = new TreeSet<>(new Comparator<Attrezzo>() {
			@Override
			public int compare(Attrezzo a1, Attrezzo a2) {
				return a1.getNome().compareTo(a2.getNome());
			}
		});
		
		s.addAll(this.attrezzi.values());
		
		return s;
		
		
	}
	
	Map<Integer,Set<Attrezzo>> getContenutoRaggruppatoPerPeso(){
		Map<Integer, Set<Attrezzo>> m = new TreeMap<>();
		for(Attrezzo a : this.attrezzi.values()){
			if(m.containsKey(a.getPeso())) {
				m.get(a.getPeso()).add(a);
			}
			else {
				Set<Attrezzo>  s =new HashSet<Attrezzo>();
				s.add(a);
				m.put(a.getPeso(), s);
			}
		}
		return m;
	}
	
	SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso(){
		SortedSet<Attrezzo> s = new TreeSet<Attrezzo>(new Comparator<Attrezzo>()  {
			@Override
			public int compare(Attrezzo a1, Attrezzo a2) {
				if(a1.getPeso()==a2.getPeso()) {
					return a1.getNome().compareTo(a2.getNome());
				}
				else
					return a1.getPeso()-a2.getPeso();
			}
		});
		s.addAll(this.attrezzi.values());
		return s;
	}
	
	

	public String toString() {
		StringBuilder s = new StringBuilder();
		if (!this.isEmpty()) {
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
			s.append("\n");
			//s.append(this.getContenutoOrdinatoPerPeso().toString());
			//s.append("\n");
			//s.append(this.getContenutoOrdinatoPerNome().toString());
			//s.append("\n");
			s.append(this.getContenutoRaggruppatoPerPeso().toString());
		}
		else
			s.append("Borsa vuota");
		return s.toString();
	}
	
	
}