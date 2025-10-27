import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class consumerReviewLab {
    public static double reviewRatingAverage(double[] reviewRatings) {
        double averageRating = 0;
        double count = 0;
        for (double reviewRating : reviewRatings) {
            averageRating += reviewRating;
            count++;
        }
        return averageRating / count;
    }

    public static void main(String[] args) {
        List<String> adjectives = new ArrayList<>();
        List<Double> ratings = new ArrayList<>();
        Map<String, Double> adjectiveLookup = new HashMap<>();

        // Load positive adjectives
        Path posPath = Paths.get("positiveAdjectives.txt");
        try (BufferedReader br = Files.newBufferedReader(posPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                String[] parts = trimmed.split(",", 2);
                String word = parts[0].toLowerCase();
                double score = 0.0;
                if (parts.length > 1) {
                    try { score = Double.parseDouble(parts[1]); } catch (NumberFormatException ex) { }
                }
                adjectives.add(word);
                ratings.add(score);
                adjectiveLookup.put(word, score);
            }
        } catch (IOException e) {
            System.err.println("Failed to read positiveAdjectives.txt: " + e.getMessage());
        }

        // Load negative adjectives
        Path negPath = Paths.get("negativeAdjectives.txt");
        try (BufferedReader br = Files.newBufferedReader(negPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                String[] parts = trimmed.split(",", 2);
                String word = parts[0].toLowerCase();
                double score = 0.0;
                if (parts.length > 1) {
                    try { score = Double.parseDouble(parts[1]); } catch (NumberFormatException ex) { }
                }
                adjectives.add(word);
                ratings.add(score);
                adjectiveLookup.put(word, score);
            }
        } catch (IOException e) {
            System.err.println("Failed to read negativeAdjectives.txt: " + e.getMessage());
        }
        System.out.println("Loaded " + ratings + " adjectives.");
        String[] iphoneReviews = {
            "while on IOS you just need to setup sleep mode in 15m minutes, otherwise sending will be aborted when display goes black, or you open another app.",
            "I currently have the iphone 16 pro. Siri is terrible and I hate the stock keyboard.",
            "I was liking iOS more it was easier but i miss universal back gesture,installing apps and so on…",
            "MacBook is unmatched, which was my first Apple device, iPad is just a breeze compared to any other tablets.",
            "I have a personal iPhone and work Galaxy S23. I prefer iOS.",
            "But I am more comfortable with iOS. The visual flairs and effects make using the phone a joy.",
            "I don't want to pay thousands of dollars for a device that severely limits what I can do with it.",
            "Apple is shitting the bed in terms of innovation / implementation of AI and there are numerous things that are so annoying on iPhones.",
            "I feel like once I see what iOS 26 has to offer, and if they’ve fixed my gripes about the cursor, long texts getting cut off, etc.",
            "iOS is way better & simpler to get things done, the Ecosystem, stays consistently fast.",
            "Same problem i have too .. ios 26 is just little design change.",
            "i used to have an iphone 8+ since its release and loved how easy it is to setup and use it.",
            "but the apps and their quality is better on the Apple side.",
            "IOS feels like it is just trying to trap, control and monitor you."
        };

        String[] androidReviews = {
            "Personally, i like Android more. Consistent gestures, flexibility.",
            "I'm considering going back to the Samsung line - maybe after the galaxy 26 line is introduced.",
            "I've always been on Android, though did strongly consider a Blackberry.",
            "I have tried pretty much all flagship phones this year.",
            "Biggest things for me are CarPlay, because Android Auto is straight ass.",
            "My s25 still struggles with moving subjects, and the photos are generally worse quality.",
            "Samsung is just exact inverse of what makes Apple UI great.",
            "The quality on Samsungs camera seem to be dramatically going down.",
            "Sounds like you like Android better and care about the camera.",
            "I will say that if camera quality is important to you there are plenty of Android phones.",
            "if you want great photos fast updates and be first just buy pixel.",
            "I will pick iPhone over top Samsung, due to extremely poor software quality.",
            "i bought a redmi note 14 6gb ram yesterday and its a pain in the ass so far.",
            "Android is superior because it offers greater freedom, a wider selection of hardware."
        };

        // Compute ratings for each review
        double[] iphoneReviewRatings = new double[iphoneReviews.length];
        for (int i = 0; i < iphoneReviews.length; i++) {
            double reviewScore = 0; // must be double
            String[] words = iphoneReviews[i].split(" ");
            for (String word : words) {
                String cleanWord = word.toLowerCase().replaceAll("[^a-z]", ""); // remove punctuation
                if (adjectives.contains(cleanWord) || reviewScore >= 0) {
                    int index = adjectives.indexOf(cleanWord);
                    double wordScore = ratings.get(index);
                    reviewScore += wordScore;

                    // debug print
                    System.out.println("iPhone Review " + i + " - Word: " + cleanWord + " | Score: " + wordScore + " | Cumulative reviewScore: " + reviewScore);
                }
            }
            iphoneReviewRatings[i] = reviewScore;
            System.out.println("Total score for review " + i + ": " + reviewScore + "\n");
        }

        System.out.println("iPhone Review Ratings:");
        System.out.println(Arrays.toString(iphoneReviewRatings));

        double[] androidReviewRatings = new double[androidReviews.length];
        for (int i = 0; i < androidReviews.length; i++) {
            double reviewScore = 0; // must be double
            String[] words = androidReviews[i].split(" ");
            for (String word : words) {
                String cleanWord = word.toLowerCase().replaceAll("[^a-z]", ""); // remove punctuation
                if (adjectives.contains(cleanWord) || reviewScore >= 0) {
                    int index = adjectives.indexOf(cleanWord);
                    double wordScore = ratings.get(index);
                    reviewScore += wordScore;

                    // debug print
                    System.out.println("Android Review " + i + " - Word: " + cleanWord + " | Score: " + wordScore + " | Cumulative reviewScore: " + reviewScore);
                }
            }
            androidReviewRatings[i] = reviewScore;
            System.out.println("Total score for review " + i + ": " + reviewScore + "\n");
        }
        System.out.println("Android Review Ratings:");
        System.out.println(Arrays.toString(androidReviewRatings));

        //Finding the average for each review
        double iphoneReviewRatingAverage = reviewRatingAverage(iphoneReviewRatings);
        double androidReviewRatingAverage = reviewRatingAverage(androidReviewRatings);

        //Displaying the averages in the terminal
        System.out.println("The Average Review Rating for iPhone is " + iphoneReviewRatingAverage);
        System.out.println("The Average Review Rating for Android is " + androidReviewRatingAverage); 
    }
}


