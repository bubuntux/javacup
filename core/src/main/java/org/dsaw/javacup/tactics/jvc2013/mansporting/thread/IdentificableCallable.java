/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dsaw.javacup.tactics.jvc2013.mansporting.thread;

import java.util.concurrent.Callable;

/**
 * @author malvarez
 */
public interface IdentificableCallable<I, E> extends Callable<E> {

  I getId();
}
