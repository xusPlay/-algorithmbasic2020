package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * T一定要是非基础类型，有基础类型需求包一层
 *
 * 加强堆的实现
 */
public class HeapGreater<T> {

    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * 大小
     *
     * @return
     */
    public int size() {
        return heapSize;
    }

    /**
     * 是否包含
     *
     * @param obj
     * @return
     */
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    /**
     * 增加一个对象
     * @param obj
     */
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    /**
     * 取出最顶上的值
     * @return
     */
    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    /**
     * 删除一个节点
     *
     * @param obj
     */
    public void remove(T obj) {
        // 获得最后一个元素
        T replace = heap.get(heapSize - 1);
        // 获得当前元素的索引
        int index = indexMap.get(obj);
        // 删除当前元素的索引
        indexMap.remove(obj);
        // 先把最后一个元素从堆中删除
        heap.remove(--heapSize);
        // 判断一下是不是最后一个节点
        if (obj != replace) {

            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    /**
     * 自己把堆调整有序
     * @param obj
     */
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    /**
     * 一直向上
     * @param index
     */
    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        // 堆中交换
        heap.set(i, o2);
        heap.set(j, o1);
        // 反向索引表交换
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

}
