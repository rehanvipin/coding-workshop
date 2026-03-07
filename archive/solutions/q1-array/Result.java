import java.util.List;

class Result {

    public static List<Integer> removeDuplicates(List<Integer> arrParam) {
        if (arrParam.isEmpty()) return arrParam;

        int i = 0;
        for (int j = 1; j < arrParam.size(); j++) {
            if (!arrParam.get(j).equals(arrParam.get(i))) {
                i++;
                arrParam.set(i, arrParam.get(j));
            }
        }
        return arrParam.subList(0, i + 1);
    }

    public static boolean search(List<Integer> arrParam, int targetParam) {
        int low = 0, high = arrParam.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int val = arrParam.get(mid);
            if (val == targetParam) return true;
            else if (val < targetParam) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }
}