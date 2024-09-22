import java.util.HashMap;

public class UserDatabase {
    private static HashMap<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }
}
