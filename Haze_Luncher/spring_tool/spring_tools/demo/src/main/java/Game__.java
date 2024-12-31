import java.util.*;

// User Class
class User {
    private String username;
    private String password;
    private List<String> library = new ArrayList<>();
    private List<String> friends = new ArrayList<>();
    private Map<String, List<String>> achievements = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public List<String> getLibrary() {
        return library;
    }

    public List<String> getFriends() {
        return friends;
    }

    public Map<String, List<String>> getAchievements() {
        return achievements;
    }

    public void addGame(String game) {
        library.add(game);
    }

    public void addFriend(String friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
        }
    }

    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    public void addAchievement(String game, String achievement) {
        achievements.computeIfAbsent(game, k -> new ArrayList<>()).add(achievement);
    }
}

// User Management
class UserManager {
    private Map<String, User> users = new HashMap<>();

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, new User(username, password));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }
}

// Game Management
class GameManager {
    private List<String> gameCatalog = Arrays.asList("Game1", "Game2", "Game3");

    public List<String> browseGames() {
        return gameCatalog;
    }

    public boolean purchaseGame(User user, String game) {
        if (gameCatalog.contains(game) && !user.getLibrary().contains(game)) {
            user.addGame(game);
            return true;
        }
        return false;
    }
}

// Main class for testing
public class GameLauncherBackend {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        GameManager gameManager = new GameManager();

        // User Registration
        userManager.registerUser("player1", "pass123");

        // User Login
        User user = userManager.loginUser("player1", "pass123");
        if (user != null) {
            System.out.println("Login Successful");

            // Browse Games
            System.out.println("Available Games: " + gameManager.browseGames());

            // Purchase Game
            if (gameManager.purchaseGame(user, "Game1")) {
                System.out.println("Game Purchased: Game1");
            }

            // Manage Friends
            user.addFriend("player2");
            user.addFriend("player3");
            user.removeFriend("player2");
            System.out.println("Friends: " + user.getFriends());

            // Track Achievements
            user.addAchievement("Game1", "First Win");
            System.out.println("Achievements: " + user.getAchievements());
        } else {
            System.out.println("Login Failed");
        }
    }
}
