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
 *
 * @author MaN
 */
public class MSGComandoList implements List<Command> {

    private final List<Command> delegatedList;

    public MSGComandoList(List<Command> delegatedList) {
        this.delegatedList = delegatedList;
    }

    public MSGComandoList() {
        this.delegatedList = new LinkedList<Command>();
    }

    public boolean addAll(int index, Collection<? extends Command> c) {
        if(c == null)
            return true;
        
        Collection<Command> c2 = new LinkedList<Command>();
        for (Iterator<? extends Command> it = c.iterator(); it.hasNext();) {
            Command comando = it.next();
            if (comando != null) {
                c2.add(comando);
            }
        }
        return delegatedList.addAll(index, c2);
    }

    public boolean addAll(Collection<? extends Command> c) {
        if(c == null)
            return true;

        Collection<Command> c2 = new LinkedList<Command>();
        for (Iterator<? extends Command> it = c.iterator(); it.hasNext();) {
            Command comando = it.next();
            if (comando != null) {
                c2.add(comando);
            }
        }
        return delegatedList.addAll(c2);
    }

    public void add(int index, Command element) {
        if (element != null) {
            delegatedList.add(index, element);
        }
    }

    public boolean add(Command e) {
        if (e == null) {
            return false;
        } else {
            return delegatedList.add(e);
        }
    }

    // ------------------------------------------------------------------ //
    public <T> T[] toArray(T[] a) {
        return delegatedList.toArray(a);
    }

    public Object[] toArray() {
        return delegatedList.toArray();
    }

    public List<Command> subList(int fromIndex, int toIndex) {
        return delegatedList.subList(fromIndex, toIndex);
    }

    public int size() {
        return delegatedList.size();
    }

    public Command set(int index, Command element) {
        return delegatedList.set(index, element);
    }

    public boolean retainAll(Collection<?> c) {
        return delegatedList.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return delegatedList.removeAll(c);
    }

    public Command remove(int index) {
        return delegatedList.remove(index);
    }

    public boolean remove(Object o) {
        return delegatedList.remove(o);
    }

    public ListIterator<Command> listIterator(int index) {
        return delegatedList.listIterator(index);
    }

    public ListIterator<Command> listIterator() {
        return delegatedList.listIterator();
    }

    public int lastIndexOf(Object o) {
        return delegatedList.lastIndexOf(o);
    }

    public Iterator<Command> iterator() {
        return delegatedList.iterator();
    }

    public boolean isEmpty() {
        return delegatedList.isEmpty();
    }

    public int indexOf(Object o) {
        return delegatedList.indexOf(o);
    }

    public int hashCode() {
        return delegatedList.hashCode();
    }

    public Command get(int index) {
        return delegatedList.get(index);
    }

    public boolean equals(Object o) {
        return delegatedList.equals(o);
    }

    public boolean containsAll(Collection<?> c) {
        return delegatedList.containsAll(c);
    }

    public boolean contains(Object o) {
        return delegatedList.contains(o);
    }

    public void clear() {
        delegatedList.clear();
    }
}
