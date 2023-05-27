package com.matias.comparablefuture;

import com.matias.comparablefuture.model.DocumentoGedo;
import com.matias.comparablefuture.utils.Consultador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComparableFutureApplication {

	// variable estática para guardar el resultado
	public static List<DocumentoGedo> documentoGedos;

	public static void main(String[] args) {
		SpringApplication.run(ComparableFutureApplication.class, args);

		CompletableFuture<List<DocumentoGedo>> future = CompletableFuture.supplyAsync(() -> {
			// hacer una solicitud que tarda más de un minuto y medio
			return makeRequest();
		});

		// cuando se complete el futuro, se guardará el resultado en la variable estática
		future.thenAccept(result -> {
			documentoGedos = result;
		});

		// continuar haciendo otras cosas en el hilo principal sin bloquear
		doSomethingElse();

		// una vez que se necesita el resultado, se puede acceder a la variable estática
		System.out.println("El resultado de la solicitud es: " + documentoGedos);

	}

	private static List<DocumentoGedo> makeRequest() {
		// Simula una solicitud a Gedo que tarda más de un minuto y medio
		List<DocumentoGedo> documentos = new ArrayList<>();
		try {
			documentos.add(new DocumentoGedo("Prueba123"));
			// 90000 milisegundos es 1:30 min (lo que tarda LUE en cargar los documentos).
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return documentos;
	}

	private static void doSomethingElse() {
		System.out.println("Haciendo otra cosa mientras se hace la solicitud...");
		Consultador consultador = new Consultador();
		consultador.consultarCargaDocumentosGedo();
	}

}
