package adt.hashtable.open;

import java.util.LinkedList;

import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		int probing = -1;
		int hash = -1;
		boolean insertable = true;
		Storable item = new HashtableElement(-1);
		HashFunctionLinearProbing<T> hashFunction = (HashFunctionLinearProbing<T>) this.getHashFunction();
		
		while (true) {
			probing++;
			hash = hashFunction.hash(element, probing);
			item = (Storable) this.table[hash];
			insertable = item == null || this.deletedElement.equals(item);
			
			if (insertable) {
				break;
			} else {
				this.COLLISIONS++;
				if (hash == this.capacity() - 1) {
					// resolver a dúvida dos collisions contados no overflow
					throw new HashtableOverflowException();
				}
			}
			
		}
		
		this.table[hash] = new HashtableElement(element.hashCode());
		this.elements++;
	}

	@Override
	public void remove(T element) {
		int probing = -1;
		int hash = -1;
		Storable item = new HashtableElement(-1);
		HashFunctionLinearProbing<T> hashFunction = (HashFunctionLinearProbing<T>) this.getHashFunction();
		
		while (!item.equals(element)) {
			probing++;
			hash = hashFunction.hash(element, probing);
			item = (Storable) this.table[hash];
			
			if (item == null) {
				break;
			}
			if (hash == this.capacity() - 1) {
				break;
			}
		}
		
		if (item != null && !this.deletedElement.equals(item)) { 
			if (item.equals(element)) {
				this.table[hash] = this.deletedElement;
				this.elements--;
			}
		}
	}

	@Override
	public T search(T element) {
		int probing = -1;
		int hash = -1;
		Storable item = new HashtableElement(-1);
		HashFunctionLinearProbing<T> hashFunction = (HashFunctionLinearProbing<T>) this.getHashFunction();
		
		while (!item.equals(element)) {
			probing++;
			hash = hashFunction.hash(element, probing);
			item = (Storable) this.table[hash];
			
			if (item == null) {
				break;
			}
			if (hash == this.capacity() - 1) {
				break;
			}
		}
		
		T result = null;
		
		if (item != null && !this.deletedElement.equals(item)) {
			if (item.equals(element)) {
				result = element;
			}
		}
		return result;
	}

	@Override
	public int indexOf(T element) {
		int probing = -1;
		int hash = -1;
		Storable item = new HashtableElement(-1);
		HashFunctionLinearProbing<T> hashFunction = (HashFunctionLinearProbing<T>) this.getHashFunction();
		
		while (!item.equals(element)) {
			probing++;
			hash = hashFunction.hash(element, probing);
			item = (Storable) this.table[hash];
			
			if (item == null) {
				break;
			}
			if (hash == this.capacity() - 1) {
				break;
			}
		}
		
		int result = -1;
		
		if (item != null && !this.deletedElement.equals(item)) { 
			result = hash;
		}
		return result;
	}

}
