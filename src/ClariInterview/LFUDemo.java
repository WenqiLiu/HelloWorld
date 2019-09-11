/**
 * Implement a LFU cache: only provide getObj(obj.uuid) api for client use.
 * Operations: 1) if obj not in the cache, (a). has available spot, put a new Obj.
 *                                           (b). delete the Least Frequent unit, then put new Obj.
 *               2) if obj is in the cache, update the frequency of the obj, return obj.
 */
package ClariInterview;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUDemo {


    class MyCache <T> {

        final private Integer MAX_SIZE;
        private Map<String, T> map;
        private LinkedHashSet[] list;
        private Integer maxFrequency;
        private Integer minFrequency;

        public MyCache(Integer MAX_SIZE) {
            this.MAX_SIZE = MAX_SIZE;
            this.map = new HashMap<> ();
            this.list = new LinkedHashSet[MAX_SIZE];
        }

        class CacheNode {
            public final String uuid;
            public T value;
            public Integer frequency;

            public CacheNode(String uuid, T value, Integer frequency) {
                this.uuid = uuid;
                this.value = value;
                this.frequency = frequency;
            }
        }


        public T getObj(String uuid) {
            T t = null;

            return t;
        }

        private void updateFrequency (CacheNode node) {

        }

    }
}
