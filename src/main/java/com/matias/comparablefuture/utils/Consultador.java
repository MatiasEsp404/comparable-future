package com.matias.comparablefuture.utils;

import com.matias.comparablefuture.ComparableFutureApplication;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Consultador implements Runnable{
  public void consultarCargaDocumentosGedo() {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    Runnable task = new Consultador();
    executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
  }

  @Override
  public void run() {
    System.out.println("Documentos cargados hasta el momento: " + ComparableFutureApplication.documentoGedos);
  }
}
