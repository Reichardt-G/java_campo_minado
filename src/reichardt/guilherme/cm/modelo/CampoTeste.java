package reichardt.guilherme.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CampoTeste {
	
	private Campo campo = new Campo(3,3);
	
	@Test
	void testeVizinhoRealDistancia1() {
		Campo vizinho = new Campo(3,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDiagonal() {
		Campo vizinho = new Campo(2,2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1,1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuplaChamada() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void abrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}
	
	@Test
	void abrirNaoMinadoMarcado	() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void abrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	@Test
	void abrirMinado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
		//assertFalse(campo.abrir());
	}
	
	@Test
	void testeAbrirComVizinhos() {
		Campo cA = new Campo(2,2);
		Campo cB = new Campo(1,1);
		
		cA.adicionarVizinho(cB);
		campo.adicionarVizinho(cA);
		campo.abrir(); //Abrimos apenas o campo atual, mas os campos 2,2 e 1,1 devem estar abertos também
		
		assertTrue(cA.isAberto() && cB.isAberto()); //retorna true se cA e cB estiverem abertos
		
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		
		Campo c11 = new Campo(1,1);
		Campo c12 = new Campo(1,2);
		Campo c22 = new Campo(2,2);
		
		c12.minar();
		c22.adicionarVizinho(c11);
		c22.adicionarVizinho(c12);
		
		campo.adicionarVizinho(c22);
		campo.abrir();
		
		assertTrue(c22.isAberto() && !c11.isAberto());
		
	}
	
}
