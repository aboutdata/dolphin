package com.sabsari.dolphin.core.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.RandomAccess;

/**
 * List containing sorted elements.
 * use binary search to access each element O(logN).
 * thread-safe.
 * 
 * @author sabsari
 */
public class SortedArrayList<E extends Comparable<E>> implements Iterable<E>, RandomAccess, Cloneable, Serializable {

	private static final long serialVersionUID = 3643195437801191322L;

	private ArrayList<E> elementList;
		
	public SortedArrayList() {
		elementList = new ArrayList<E>();
	}
	
	public int size() {		
		return elementList.size();
	}
	
	public boolean isEmpty() {
		return elementList.isEmpty();
	}
		
	public E get(int index) {
		return elementList.get(index);
	}
	
	public boolean contains(E element) {
		int index = getIndex(element);
		if (index < 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Iterator<E> iterator() {
	    return elementList.iterator();
	}
	
    public Object[] toArray() {
        return elementList.toArray();
    }
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		try {
			SortedArrayList<E> c = (SortedArrayList<E>) super.clone();
			c.elementList = (ArrayList<E>) elementList.clone();
			return c;			
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}		
	}
		
	public boolean add(E element) {
	    synchronized (elementList) {
	        int index = getIndexToInsert(element);
	        if (index < 0) {
	            return false;
	        }
	        else {      
	            elementList.add(index, element);
	            return true;
	        }
        }
	}
	
	public void clear() {
	    synchronized (elementList) {
	        elementList.clear();
	    }
	}
	
	public void remove(E element) {
	    synchronized (elementList) {
    		int index = getIndex(element);
    		if (index >= 0) {
    			elementList.remove(index);
    		}
	    }
	}

	private int getIndexToInsert(E element) {
		int start = 0;
		int end = elementList.size() - 1;
		int mid;
		int compared;
		
		while (start <= end) {
			mid = (start + end) / 2;
			compared = element.compareTo(elementList.get(mid));
			
			if (compared < 0)
				end = mid - 1;
			else if (compared > 0)
				start = mid + 1;
			else
				return -1; // duplicate!
		}
		
		return start;
	}
	
	private int getIndex(E element) {
		int start = 0;
		int end = elementList.size() - 1;
		int mid;
		int compared;
		
		while (start <= end) {
			mid = (start + end) / 2;
			compared = element.compareTo(elementList.get(mid));
			
			if (compared < 0)
				end = mid - 1;
			else if (compared > 0)
				start = mid + 1;
			else
				return mid;
		}
		
		return -1;			// not exist
	}
}
