import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

class Result {

    public static List<String> recommendFriends(List<String> userList, List<List<String>> friendsList, String targetUser) {
        // Step 1: Build user -> friends map
        Map<String, Set<String>> userToFriends = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            userToFriends.put(userList.get(i), new HashSet<>(friendsList.get(i)));
        }

        // Step 2: Target's friends
        Set<String> targetFriends = userToFriends.getOrDefault(targetUser, new HashSet<>());

        // Step 3: Iterate over all users and calculate mutual friends
        int maxMutual = 0;
        List<String> recommendations = new ArrayList<>();

        for (String candidate : userList) {
            if (candidate.equals(targetUser)) continue; // skip self
            if (targetFriends.contains(candidate)) continue; // skip existing friends

            Set<String> candidateFriends = userToFriends.getOrDefault(candidate, new HashSet<>());
            
            // Find mutual friends count
            int mutualCount = 0;
            for (String f : candidateFriends) {
                if (targetFriends.contains(f)) {
                    mutualCount++;
                }
            }

            if (mutualCount > 0) {
                if (mutualCount > maxMutual) {
                    maxMutual = mutualCount;
                    recommendations.clear();
                    recommendations.add(candidate);
                } else if (mutualCount == maxMutual) {
                    recommendations.add(candidate);
                }
            }
        }

        return recommendations;
    }
}