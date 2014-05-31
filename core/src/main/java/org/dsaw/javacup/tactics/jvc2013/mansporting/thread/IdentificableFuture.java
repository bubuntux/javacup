/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import java.util.concurrent.Future;

/**
 *
 * @author malvarez
 */
public interface IdentificableFuture<I, E> extends Future<E> {

    I getId();
}
