/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting;

import org.dsaw.javacup.model.command.Command;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author MaN
 */
public class MSGComandoList implements List<Command> {

  private final List<Command> delegatedList;

  public MSGComandoList(List<Command> delegatedList) {
    this.delegatedList = delegatedList;
  }

  public MSGComandoList() {
    this.delegatedList = new LinkedList<>();
  }

  @Override
  public boolean addAll(int index, Collection<? extends Command> c) {
    if (c == null) {
      return true;
    }

    Collection<Command> c2 = new LinkedList<>();
    for (Iterator<? extends Command> it = c.iterator(); it.hasNext(); ) {
      Command comando = it.next();
      if (comando != null) {
        c2.add(comando);
      }
    }
    return delegatedList.addAll(index, c2);
  }

  @Override
  public boolean addAll(Collection<? extends Command> c) {
    if (c == null) {
      return true;
    }

    Collection<Command> c2 = new LinkedList<>();
    for (Iterator<? extends Command> it = c.iterator(); it.hasNext(); ) {
      Command comando = it.next();
      if (comando != null) {
        c2.add(comando);
      }
    }
    return delegatedList.addAll(c2);
  }

  @Override
  public void add(int index, Command element) {
    if (element != null) {
      delegatedList.add(index, element);
    }
  }

  @Override
  public boolean add(Command e) {
    if (e == null) {
      return false;
    } else {
      return delegatedList.add(e);
    }
  }

  // ------------------------------------------------------------------ //
  @Override
  public <T> T[] toArray(T[] a) {
    return delegatedList.toArray(a);
  }

  @Override
  public Object[] toArray() {
    return delegatedList.toArray();
  }

  @Override
  public List<Command> subList(int fromIndex, int toIndex) {
    return delegatedList.subList(fromIndex, toIndex);
  }

  @Override
  public int size() {
    return delegatedList.size();
  }

  @Override
  public Command set(int index, Command element) {
    return delegatedList.set(index, element);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return delegatedList.retainAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return delegatedList.removeAll(c);
  }

  @Override
  public Command remove(int index) {
    return delegatedList.remove(index);
  }

  @Override
  public boolean remove(Object o) {
    return delegatedList.remove(o);
  }

  @Override
  public ListIterator<Command> listIterator(int index) {
    return delegatedList.listIterator(index);
  }

  @Override
  public ListIterator<Command> listIterator() {
    return delegatedList.listIterator();
  }

  @Override
  public int lastIndexOf(Object o) {
    return delegatedList.lastIndexOf(o);
  }

  @Override
  public Iterator<Command> iterator() {
    return delegatedList.iterator();
  }

  @Override
  public boolean isEmpty() {
    return delegatedList.isEmpty();
  }

  @Override
  public int indexOf(Object o) {
    return delegatedList.indexOf(o);
  }

  public int hashCode() {
    return delegatedList.hashCode();
  }

  @Override
  public Command get(int index) {
    return delegatedList.get(index);
  }

  public boolean equals(Object o) {
    return delegatedList.equals(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return delegatedList.containsAll(c);
  }

  @Override
  public boolean contains(Object o) {
    return delegatedList.contains(o);
  }

  @Override
  public void clear() {
    delegatedList.clear();
  }
}
