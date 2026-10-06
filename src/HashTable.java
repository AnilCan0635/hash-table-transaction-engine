package dataHomework;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HashTable<K, V> {

	private static class Entry<K, V> {
		private K key;
		private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
	}
	
	

	public static int findNextPrime(int n) {
		if (n < 2) {
			//// prime numbers start from 2, so if n is less than 2, return 2
			return 2;
		}

		int nextNumber = n + 1;

		while (true) {
			if (isPrime(nextNumber)) {
				return nextNumber;
			}
			nextNumber++;
		}
	}

	//// helper method to check if a number is prime
	private static boolean isPrime(int num) {
		if (num < 2) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	static Scanner sc = new Scanner(System.in);

	private int hash_function = 0;

	public void hashChoice() {
		hash_function = sc.nextInt();
		while (hash_function != 1 && hash_function != 2) {
			System.out.println("Invalid choice please try again.");
			hash_function = sc.nextInt();
		}
	}

	private int handling = 0;

	public void handlingChoice() {
		handling = sc.nextInt();
		while (handling != 1 && handling != 2) {
			System.out.println("Invalid choice please try again.");
			handling = sc.nextInt();
		}
	}

	private int load = 0;
	private double LOAD_FACTOR;

	public void adjust_load_factor() {
		load = sc.nextInt();
		while (load != 1 && load != 2) {
			System.out.println("Invalid choice please try again.");
			handling = sc.nextInt();
		}
		switch (load) {
		case 1:
			LOAD_FACTOR = 0.5;
			break;
		case 2:
			LOAD_FACTOR = 0.8;
			break;
		default:
			throw new IllegalArgumentException("Invalid choice: " + load);
		}
	}

	private static final int INITIAL_CAPACITY = 11;
	private int collisionCount;

	private Entry<K, V>[] table;
	private int size;

	@SuppressWarnings("unchecked")
	public HashTable() {
		this.table = new Entry[INITIAL_CAPACITY];
		this.size = 0;
		this.collisionCount = 0;
	}

	public int getCollisionCount() {
		return collisionCount;
	}

	private int hashSSF(K key, int length) {
		int hash = 0;
		for (int k = 0; k < ((String) key).length(); k++) {
			if (((String) key).charAt(k) != '-') {
				hash += ((String) key).charAt(k);
			}
		}
		return hash % length;
	}

	private int hashPAF(K key, int length) {
		int n = ((String) key).length();
		int hash;
		int z = 37;
		int total = 0;
		for (int i = 0; i < n; i++) {
			if (((String) key).charAt(i) != '-') {
				int chValue = getCharacterValue(((String) key).charAt(i));
				total += (chValue * (int) Math.pow(z, n - i));
			}
		}
		hash = Math.abs(total % length);
		return hash;
	}

	private int getCharacterValue(char ch) {
		if (Character.isLetter(ch)) {
			ch = Character.toLowerCase(ch);
			return ch - 'a' + 1;
		} else if (Character.isDigit(ch)) {
			return ch - '0' + 26;
		} else {
			throw new IllegalArgumentException("Invalid character: " + ch);
		}
	}

	/// Linear probing
	private int linearProbe(K key, int probe, int length) {
		switch (hash_function) {
		case 1:
			return (hashSSF(key, length) + probe) % length;
		case 2:
			return (hashPAF(key, length) + probe) % length;
		default:
			throw new IllegalArgumentException("Invalid choice:" + hash_function);
		}
	}

	private int doubleHash(K key, int probe, int length) {
	    int q = 7; /// prime number less than N (table size)
	    int h;
	    switch (hash_function) {
	        case 1:
	            h = hashSSF(key, length);
	            break;
	        case 2:
	            h = hashPAF(key, length);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid choice:" + hash_function);
	    }

	    int d = (q - ((h % q + q) % q)); //// secondary hash function d(k) = q - h % q

	    int index = (h + probe * d) % length;

	    return Math.abs(index);
	}

	public void put(K key, V value) {
		if (size >= LOAD_FACTOR * table.length) {
			resize(findNextPrime(table.length * 2));
		}
		int index;
		switch (hash_function) {
		case 1:
			index = hashSSF(key, table.length);
			break;
		case 2:
			index = hashPAF(key, table.length); //// PAF
			break;
		default:
			throw new IllegalArgumentException("Invalid choice:" + hash_function);
		}
		int probe = 0;

		if (table[index] == null) {
			table[index] = new Entry<>(key, value);
		} else {
			while (table[index] != null) {
				collisionCount++;
				probe++;
				switch (handling) {
				case 1:
					index = linearProbe(key, probe, table.length);
					break;
				case 2:
					index = doubleHash(key, probe, table.length); /// DH
					break;
				default:
					throw new IllegalArgumentException("Invalid choice:" + handling);
				}
			}
			table[index] = new Entry<>(key, value);
		}

		size++;
	}

	public V get(K key) {
		int index;
		switch (hash_function) {
		case 1:
			index = hashSSF(key, table.length);
			break;
		case 2:
			index = hashPAF(key, table.length); //// PAF
			break;
		default:
			throw new IllegalArgumentException("Invalid choice:" + hash_function);
		}

		int probe = 0;

		while (table[index] != null) {
			if (table[index].getKey().equals(key)) {
				return table[index].getValue();
			}
			probe++;
			switch (handling) {
			case 1:
				index = linearProbe(key, probe, table.length);
				break;
			case 2:
				index = doubleHash(key, probe, table.length); /// DH
				break;
			default:
				throw new IllegalArgumentException("Invalid choice:" + handling);
			}
		}
		return null;
	}

	public boolean contains(K key) {
		int index;
		switch (hash_function) {
		case 1:
			index = hashSSF(key, table.length);
			break;
		case 2:
			index = hashPAF(key, table.length); //// PAF
			break;
		default:
			throw new IllegalArgumentException("Invalid choice:" + hash_function);
		}
		int probe = 0;

		while (table[index] != null) {
			if (table[index].getKey().equals(key)) {
				return true;
			}
			probe++;
			switch (handling) {
			case 1:
				index = linearProbe(key, probe, table.length);
				break;
			case 2:
				index = doubleHash(key, probe, table.length); /// DH
				break;
			default:
				throw new IllegalArgumentException("Invalid choice:" + handling);
			}
		}
		return false;
	}

	private void resize(int capacity) {
		Entry<K, V>[] newTable = new Entry[capacity];
		for (Entry<K, V> entry : table) {
			if (entry != null) {
				int index;
				switch (hash_function) {
				case 1:
					index = hashSSF(entry.getKey(), newTable.length);
					break;
				case 2:
					index = hashPAF(entry.getKey(), newTable.length); //// PAF
					break;
				default:
					throw new IllegalArgumentException("Invalid choice:" + hash_function);
				}

				int probe = 0;

				while (newTable[index] != null) {
					probe++;
					switch (handling) {
					case 1:
						index = linearProbe(entry.getKey(), probe, newTable.length);
						break;
					case 2:
						index = doubleHash(entry.getKey(), probe, newTable.length); /// DH
						break;
					default:
						throw new IllegalArgumentException("Invalid choice:" + handling);
					}
				}

				newTable[index] = entry;
			}
		}
		table = newTable;
	}

	public void remove(K key) {
	    int index;
	    switch (hash_function) {
	        case 1:
	            index = hashSSF(key, table.length);
	            break;
	        case 2:
	            index = hashPAF(key, table.length); //// PAF
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid choice:" + hash_function);
	    }
	    int probe = 0;

	    while (table[index] != null) {
	        if (table[index].getKey().equals(key)) {
	            table[index] = null;
	            size--;

	            switch (handling) {
	                case 1:
	                    index = linearProbe(key, probe, table.length);
	                    break;
	                case 2:
	                    index = doubleHash(key, probe, table.length); /// DH
	                    break;
	                default:
	                    throw new IllegalArgumentException("Invalid choice:" + handling);
	            }

	            while (table[index] != null) {
	                Entry<K, V> entryToRehash = table[index];
	                table[index] = null;
	                size--;
	                put(entryToRehash.getKey(), entryToRehash.getValue());
	                probe++;
	                switch (handling) {
	                    case 1:
	                        index = linearProbe(entryToRehash.getKey(), probe, table.length);
	                        break;
	                    case 2:
	                        index = doubleHash(entryToRehash.getKey(), probe, table.length); /// DH
	                        break;
	                    default:
	                        throw new IllegalArgumentException("Invalid choice:" + handling);
	                }
	            }

	            if (size <= LOAD_FACTOR * table.length / 4) {
	                resize(findNextPrime(table.length / 2));
	            }

	            return;
	        }
	        probe++;
	        switch (handling) {
	            case 1:
	                index = linearProbe(key, probe, table.length);
	                break;
	            case 2:
	                index = doubleHash(key, probe, table.length); /// DH
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid choice:" + handling);
	        }
	    }
	}

	public List<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}

