/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import org.dsaw.javacup.tactics.jvc2013.mansporting.MSGConstants;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 *
 * @author malvarez
 */
public class ThreadOperationManager {

    private static final Logger log = Logger.getLogger(ThreadOperationManager.class.getName());
    /**
     * Manejador de los hilos.
     */
    private final ExecutorService executorService;

    /**
     * Handler para las operaciones.
     */
    private ThreadOperationManager() {
        executorService = MSGConstants.EXECUTE_THREADED ? Executors.newCachedThreadPool() : null;
    }

    /**
     * Ejecuta las tareas indicadas en hilos separados y espera a su terminación.
     * @param <RESULT> tipo de los resultados que se publicarán.
     * @param operations operaciones a ejecturar.
     * @param callback  callback para notificar cuando se finalice.
     * @return resultados de las operaciones.
     */
    private <ID, RESULT> List<IdentificableFuture<ID, RESULT>> internalExecuteThreaded(List<? extends IdentificableCallable<ID, RESULT>> operations) {
        if (!MSGConstants.EXECUTE_THREADED) {
            throw new IllegalAccessError("No se pueden ejecutar hilos");
        }
        if (operations == null || operations.isEmpty()) {
            throw new IllegalArgumentException("operations no puede ser null o estar vacío");
        }
        try {
            List<IdentificableFuture<ID, RESULT>> futures = new LinkedList<IdentificableFuture<ID, RESULT>>();
            for (IdentificableCallable<ID, RESULT> callable : operations) {
                futures.add(new ThreadOperationFuture<ID, RESULT>(callable.getId(), executorService.submit(callable)));
            }
            return futures;
        } catch (Throwable e) {
            throw new RuntimeException("Error ejecutando la tarea en hilos", e);
        }
    }

    /**
     * Ejecuta las tareas indicadas en hilos separados y espera a su terminación.
     * @param <RESULT> tipo de los resultados que se publicarán.
     * @param operations operaciones a ejecturar.
     * @param callback  callback para notificar cuando se finalice.
     * @return resultados de las operaciones.
     */
    public <ID, RESULT> List<IdentificableData<ID, RESULT>> executeThreaded(List<? extends IdentificableCallable<ID, RESULT>> operations) {
        try {
            List<IdentificableData<ID, RESULT>> results = new LinkedList<IdentificableData<ID, RESULT>>();
            if (!MSGConstants.EXECUTE_THREADED) {
                for (IdentificableCallable<ID, RESULT> operation : operations) {
                    RESULT result = operation.call();
                    if (result != null) {
                        results.add(new ThreadOperationData(operation.getId(), result));
                    }
                }
            } else {
                for (IdentificableFuture<ID, RESULT> future : internalExecuteThreaded(operations)) {
                    RESULT result = future.get();
                    if (result != null) {
                        results.add(new ThreadOperationData(future.getId(), result));
                    }
                }
            }
            return results;
        } catch (Throwable e) {
            throw new RuntimeException("Error ejecutando la tarea en hilos", e);
        }
    }

    public static ThreadOperationManager initInstance() {
        return new ThreadOperationManager();
    }

    public List<Runnable> realeaseInstance() {
        return executorService.shutdownNow();
    }
}
