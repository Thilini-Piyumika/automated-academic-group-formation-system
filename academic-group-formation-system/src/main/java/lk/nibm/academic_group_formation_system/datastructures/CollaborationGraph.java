package lk.nibm.academic_group_formation_system.datastructures;

import java.util.*;

public class CollaborationGraph {

    private final Map<String, Map<String, Integer>> graph = new HashMap<>();

    public boolean violatesThreshold(List<String> group, int threshold) {
        for (int i = 0; i < group.size(); i++) {
            for (int j = i + 1; j < group.size(); j++) {
                int weight = graph
                        .getOrDefault(group.get(i), Map.of())
                        .getOrDefault(group.get(j), 0);
                if (weight >= threshold) return true;
            }
        }
        return false;
    }

    public void updateGraph(List<String> group) {
        for (int i = 0; i < group.size(); i++) {
            for (int j = i + 1; j < group.size(); j++) {
                graph
                        .computeIfAbsent(group.get(i), k -> new HashMap<>())
                        .merge(group.get(j), 1, Integer::sum);

                graph
                        .computeIfAbsent(group.get(j), k -> new HashMap<>())
                        .merge(group.get(i), 1, Integer::sum);
            }
        }
    }
}
