package com.cdu.edu.student;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * description:
 *
 * @author haifeng
 * @version 1.1
 * @date 2020/4/4 0004 下午 17:59
 * @since jdk 12.0.2
 */
@Component
public class SortUtil {

    /**
     * description: 快速排序驱动函数
     *
     * @param studentClassSet 待排序的Set集合
     * @return java.lang.String[] 已排好序的String数组
     */
    public String[] quickSort(Set studentClassSet) {
        int len = studentClassSet.size();
        String[] array = (String[]) studentClassSet.toArray(new String[len]);

        if (len == 0 || len == 1) {
            return null;
        }
        sort(array, 0, len - 1);
        return array;
    }

    /**
     * description: 快速排序核心算法
     *
     * @param array 待排序数组
     * @param left  这一轮排序的左区间
     * @param right 这一轮排序的右区间
     */
    public void sort(String[] array, int left, int right) {
        if (left > right) {
            return;
        }
        // base中存放基准数
        String base = array[left];
        int i = left, j = right;
        while (i != j) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while (array[j].compareTo(base) >= 0 && i < j) {
                j--;
            }

            // 再从左往右边找，直到找到比base值大的数
            while (array[i].compareTo(base) <= 0 && i < j) {
                i++;
            }

            // 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置
            if (i < j) {
                String tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        array[left] = array[i];
        array[i] = base;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        sort(array, left, i - 1);
        sort(array, i + 1, right);
    }
}
