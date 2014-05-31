/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author malvarez
 */
class ThreadOperationFuture<I, E> implements IdentificableFuture<I, E> {

  private final I id;
  private final Future<E> future;

  public ThreadOperationFuture(I id, Future future) {
    this.id = id;
    this.future = future;
  }

  @Override
  public I getId() {
    return id;
  }

  @Override
  public boolean isDone() {
    return future.isDone();
  }

  @Override
  public boolean isCancelled() {
    return future.isCancelled();
  }

  @Override
  public E get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return future.get(timeout, unit);
  }

  @Override
  public E get() throws InterruptedException, ExecutionException {
    return future.get();
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return future.cancel(mayInterruptIfRunning);
  }
}
