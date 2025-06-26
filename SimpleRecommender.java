import java.util.*;

public class SimpleRecommender {

    // Simulated user-item rating data
    static Map<Integer, Map<Integer, Double>> userData = new HashMap<>();

    public static void main(String[] args) {
        loadSampleData();

        int targetUserId = 1;

        // Find similar users
        Map<Integer, Double> similarityScores = new HashMap<>();
        for (int userId : userData.keySet()) {
            if (userId != targetUserId) {
                double sim = cosineSimilarity(userData.get(targetUserId), userData.get(userId));
                similarityScores.put(userId, sim);
            }
        }

        // Recommend items
        Set<Integer> recommendedItems = new HashSet<>();
        for (int similarUser : similarityScores.keySet()) {
            if (similarityScores.get(similarUser) > 0.1) {
                for (int itemId : userData.get(similarUser).keySet()) {
                    if (!userData.get(targetUserId).containsKey(itemId)) {
                        recommendedItems.add(itemId);
                    }
                }
            }
        }

        // Print recommendations
        System.out.println("Recommended items for user " + targetUserId + ":");
        for (int itemId : recommendedItems) {
            System.out.println("Item ID: " + itemId);
        }
    }

    // Cosine similarity between two users' rating vectors
    private static double cosineSimilarity(Map<Integer, Double> user1, Map<Integer, Double> user2) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;

        for (int itemId : user1.keySet()) {
            if (user2.containsKey(itemId)) {
                dotProduct += user1.get(itemId) * user2.get(itemId);
            }
            norm1 += Math.pow(user1.get(itemId), 2);
        }

        for (double val : user2.values()) {
            norm2 += Math.pow(val, 2);
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2) + 1e-9);
    }

    // Load hardcoded user-item rating data
    private static void loadSampleData() {
        userData.put(1, Map.of(101, 5.0, 102, 3.0));
        userData.put(2, Map.of(101, 4.0, 103, 5.0));
        userData.put(3, Map.of(104, 4.0, 102, 2.0));
    }
}
