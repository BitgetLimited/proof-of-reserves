package com.upex.util;

import java.lang.reflect.Array;
import java.util.*;

public class CollectionUtils {
    private static Integer INTEGER_ONE = new Integer(1);

    public CollectionUtils() {
    }

    public static Collection union(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();

        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;

            for(int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }

        return list;
    }

    public static Collection intersection(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();

        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;

            for(int m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }

        return list;
    }

    public static Collection disjunction(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();

        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;

            for(int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)) - Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }

        return list;
    }

    public static Collection subtract(Collection a, Collection b) {
        ArrayList list = new ArrayList(a);
        Iterator it = b.iterator();

        while(it.hasNext()) {
            list.remove(it.next());
        }

        return list;
    }

    public static boolean containsAny(Collection coll1, Collection coll2) {
        Iterator it;
        if (coll1.size() < coll2.size()) {
            it = coll1.iterator();

            while(it.hasNext()) {
                if (coll2.contains(it.next())) {
                    return true;
                }
            }
        } else {
            it = coll2.iterator();

            while(it.hasNext()) {
                if (coll1.contains(it.next())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Map getCardinalityMap(Collection coll) {
        Map count = new HashMap();
        Iterator it = coll.iterator();

        while(it.hasNext()) {
            Object obj = it.next();
            Integer c = (Integer)((Integer)count.get(obj));
            if (c == null) {
                count.put(obj, INTEGER_ONE);
            } else {
                count.put(obj, new Integer(c + 1));
            }
        }

        return count;
    }

    public static boolean isSubCollection(Collection a, Collection b) {
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Iterator it = a.iterator();

        Object obj;
        do {
            if (!it.hasNext()) {
                return true;
            }

            obj = it.next();
        } while(getFreq(obj, mapa) <= getFreq(obj, mapb));

        return false;
    }

    public static boolean isProperSubCollection(Collection a, Collection b) {
        return a.size() < b.size() && isSubCollection(a, b);
    }

    public static boolean isEqualCollection(Collection a, Collection b) {
        if (a.size() != b.size()) {
            return false;
        } else {
            Map mapa = getCardinalityMap(a);
            Map mapb = getCardinalityMap(b);
            if (mapa.size() != mapb.size()) {
                return false;
            } else {
                Iterator it = mapa.keySet().iterator();

                Object obj;
                do {
                    if (!it.hasNext()) {
                        return true;
                    }

                    obj = it.next();
                } while(getFreq(obj, mapa) == getFreq(obj, mapb));

                return false;
            }
        }
    }

    public static boolean addIgnoreNull(Collection collection, Object object) {
        return object == null ? false : collection.add(object);
    }

    public static void addAll(Collection collection, Iterator iterator) {
        while(iterator.hasNext()) {
            collection.add(iterator.next());
        }

    }

    public static void addAll(Collection collection, Enumeration enumeration) {
        while(enumeration.hasMoreElements()) {
            collection.add(enumeration.nextElement());
        }

    }

    public static void addAll(Collection collection, Object[] elements) {
        int i = 0;

        for(int size = elements.length; i < size; ++i) {
            collection.add(elements[i]);
        }

    }

    /** @deprecated */
    public static Object index(Object obj, int idx) {
        return index(obj, new Integer(idx));
    }

    /** @deprecated */
    public static Object index(Object obj, Object index) {
        if (obj instanceof Map) {
            Map map = (Map)obj;
            if (map.containsKey(index)) {
                return map.get(index);
            }
        }

        int idx = -1;
        if (index instanceof Integer) {
            idx = (Integer)index;
        }

        if (idx < 0) {
            return obj;
        } else if (obj instanceof Map) {
            Map map = (Map)obj;
            Iterator iterator = map.keySet().iterator();
            return index(iterator, idx);
        } else if (obj instanceof List) {
            return ((List)obj).get(idx);
        } else if (obj instanceof Object[]) {
            return ((Object[])((Object[])obj))[idx];
        } else {
            if (obj instanceof Enumeration) {
                Enumeration it = (Enumeration)obj;

                while(it.hasMoreElements()) {
                    --idx;
                    if (idx == -1) {
                        return it.nextElement();
                    }

                    it.nextElement();
                }
            } else {
                if (obj instanceof Iterator) {
                    return index((Iterator)obj, idx);
                }

                if (obj instanceof Collection) {
                    Iterator iterator = ((Collection)obj).iterator();
                    return index(iterator, idx);
                }
            }

            return obj;
        }
    }

    private static Object index(Iterator iterator, int idx) {
        while(iterator.hasNext()) {
            --idx;
            if (idx == -1) {
                return iterator.next();
            }

            iterator.next();
        }

        return iterator;
    }

    public static int size(Object object) {
        int total = 0;
        if (object instanceof Map) {
            total = ((Map)object).size();
        } else if (object instanceof Collection) {
            total = ((Collection)object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[])((Object[])object)).length;
        } else if (object instanceof Iterator) {
            Iterator it = (Iterator)object;

            while(it.hasNext()) {
                ++total;
                it.next();
            }
        } else if (object instanceof Enumeration) {
            Enumeration it = (Enumeration)object;

            while(it.hasMoreElements()) {
                ++total;
                it.nextElement();
            }
        } else {
            if (object == null) {
                throw new IllegalArgumentException("Unsupported object type: null");
            }

            try {
                total = Array.getLength(object);
            } catch (IllegalArgumentException var3) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }

        return total;
    }

    public static boolean sizeIsEmpty(Object object) {
        if (object instanceof Collection) {
            return ((Collection)object).isEmpty();
        } else if (object instanceof Map) {
            return ((Map)object).isEmpty();
        } else if (object instanceof Object[]) {
            return ((Object[])((Object[])object)).length == 0;
        } else if (object instanceof Iterator) {
            return !((Iterator)object).hasNext();
        } else if (object instanceof Enumeration) {
            return !((Enumeration)object).hasMoreElements();
        } else if (object == null) {
            throw new IllegalArgumentException("Unsupported object type: null");
        } else {
            try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException var2) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
    }

    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static void reverseArray(Object[] array) {
        int i = 0;

        for(int j = array.length - 1; j > i; ++i) {
            Object tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            --j;
        }

    }

    private static final int getFreq(Object obj, Map freqMap) {
        Integer count = (Integer)freqMap.get(obj);
        return count != null ? count : 0;
    }
}
