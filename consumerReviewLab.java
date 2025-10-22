import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class consumerReviewLab {
    public static int reviewRatingAverage(int[] reviewRatings) {
        int averageRating = 0;
        int count = 0;
        for (int reviewRating : reviewRatings) {
            averageRating += reviewRating;
            count ++;
        }
         return averageRating / count;
    }
    public static void main(String[] args) {
        // Load adjectives into two aligned lists and a lookup map. Files have lines like: word,score
        List<String> adjectives = new ArrayList<>();
        List<Double> ratings = new ArrayList<>();
        Map<String, Double> adjectiveLookup = new HashMap<>();

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
                    try { score = Double.parseDouble(parts[1]); } catch (NumberFormatException ex) { /* leave 0.0 */ }
                }
                adjectives.add(word);
                ratings.add(score);
                adjectiveLookup.put(word, score);
            }
        } catch (IOException e) {
            System.err.println("Failed to read positiveAdjectives.txt: " + e.getMessage());
        }

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
                    try { score = Double.parseDouble(parts[1]); } catch (NumberFormatException ex) { /* leave 0.0 */ }
                }
                adjectives.add(word);
                ratings.add(score);
                adjectiveLookup.put(word, score);
            }
        } catch (IOException e) {
            System.err.println("Failed to read negativeAdjectives.txt: " + e.getMessage());
        }
        String[] iphoneReviews = {
            "while on IOS you just need to setup sleep mode in 15m minutes, otherwise sending will be aborted when display goes black, or you open another app. When i first tried IOS i was excited but in few days i realised that this OS so limited, for sake of battery longevity, or i dont know why.",
            "I currently have the iphone 16 pro. Siri is terrible and I hate the stock keyboard. I use Swiftkey. I also like ios notes app.",
            "I was liking iOS more it was easier but i miss universal back gesture,installing apps and so on… when iOS 26 beta arrived i install it and omg i hate it everything needs more clicks.",
            "MacBook is unmatched, which was my first Apple device, iPad is just a breeze compared to any other tablets, and seems like iPhone is unmatched too. It's just how it is.",
            "I have a personal iPhone and work Galaxy S23. I prefer iOS. I guess I am more used to it and it works better with my iPad and MacBook.",
            "But I am more comfortable with iOS. The visual flairs and effects (especially those in iOS 26) make using the phone a joy. Yeah, side loading is not as easy and I can't use custom launchers but things like the iPhone Lock Screen are very nice to look at. That's what I eventually came to the conclusion of.",
            "I don't want to pay thousands of dollars for a device that severely limits what I can do with it. If I wanted that I'd just buy my work laptop from my employer.",
            " Apple is shitting the bed in terms of innovation / implementation of AI and there are numerous things that are so annoying on iPhones that I’m just about over it.",
            "I feel like once I see what iOS 26 has to offer, and if they’ve fixed my gripes about the cursor, long texts getting cut off, etc. then I’ll see which way I lean. It’s hard to give up my Apple Watch and seamless connection to AirPods as well, but iPhones are missing some basic functionality that drives my wife and I nuts! But I utilize text to pay (phone bill to my mother in law) monthly and do enjoy iMessage in general, along with Face ID, Apple Pay, Apple Card integration in Apple Wallet, etc. TBD!",
            "iOS is way better & simpler to get things done, the Ecosystem, stays consistently fast no matter how many apps I download, easy access Apple Pay (super major for me), and I don’t need to give ppl (family) my number if I don’t want to and can just message them with my email or if I don’t have cellular plan w/o needing to download an extra IM app. Also, you can AirDrop pics or send money without needing someone’s phone number, which is great if you wanna avoid giving a manager your number.",
            "Same problem i have too .. ios 26 is just little design change.. liquid glass idk i got feeling its just like unfinished Vista.. Hard to see sometimes and so on no nothing was added and i miss that functions too so thats why im switching to pixel",
            "i used to have an iphone 8+ since its release and loved how easy it is to setup and use it.",
            "but the apps and their quality is better on the Apple side. Plus an iPhone can save your life. Apps have haptics that are a joke on Android.",
            "IOS feels like it is just trying to trap, control and monitor you. Do what Apple want you to do and that's it."
            
        };
        String[] androidReviews = {
        "Personally, i like Android more. Consistent gestures, flexibility, you can send 1GB video and use some other apps at the same time",
            " I'm considering going back to the Samsung line - maybe after the galaxy 26 line is introduced. Android does have a lot of bloatware, but I don't use my phone for business or work.",
            "I've always been on Android, though did strongly consider a Blackberry when I got my first smartphone. As far as cameras go though this is really gonna be down to the specific phone. They vary so wildly. I know there's plenty of vids on YouTube comparing phone cameras though so those could be helpful.",
            "I have tried pretty much all flagship phones this year, s25 ultra, fold 7, pixel 10 pro XL, one plus 13. Each time I think this is the one I realize just how buggy and unstable the experience is, both due to third party apps just lacking polish and the OS experience not being fluid. Android has way more features and flexibility but boy it is a buggy, stuttery and poorly supported mess. Third party apps feel like an after thought, with poorly designed UX, less features and tons of bugs.",
            "Biggest things for me are CarPlay, because Android Auto is straight ass. Gesture navigation is garbage on Android. Push notifications are generally way slower on Android for apps I’m logged into on both devices, such as Instagram, Outlook, Facebook, Slack, etc. The Play Store is also much worse quality than the App Store, with iOS versions of the same apps being better.",
            "My s25 still struggles with moving subjects, and the photos are generally worse quality. The video quality of the S25 is better than I expected, especially with stabilization, but the photos are way below my expectations for its price since it’s using such an old sensor.",
            "Samsung is just exact inverse of what makes Apple UI great, it sucks in almost everything except features, perhaps, Pixel Android is great, but Google hardware is absolutely terrible. I will leave Android, possibly for good.",
            "The quality on Samsungs camera seem to be dramatically going down, not sure if this is trend across all manufacturers, but I would avoid Samsung.",
            "Sounds like you like Android better and care about the camera, which there are a plethora of Android phones if you have a store with live phones nearby best to go to one and try the cameras out. There are many different variables like white balance, sharpness, etc and you might weight them differently than others, so it will take reading multiple reviews to get the idea.",
            "I will say that if camera quality is important to you there are plenty of Android phones out there with amazing camera quality you just need to buy one of those phones. Unlike iPhones Android phones aren't made by one company and aren't as standard so you just have to keep that in mind if that is really your only drawback.",
            "if you want great photos fast updates and be first just buy pixel and i guess on s25 is better video and gaming",
            " I will pick iPhone over top Samsung, due to extremely poor software quality , bloatware and extremely weak security also unlock able",
            " i bought a redmi note 14 6gb ram yesterday and its a pain in the ass so far to setup everything and remove all the bloatwares and adwares,",
            "Android is superior because it offers greater freedom, a wider selection of hardware, and insanely good cameras on Pixels and Galaxy devices, recently vivos phones camera are mind blowing. Android simply allows you to use your phone however you want"
            
        };

        int[] iphoneReviewRatings = {};
        int[] androidReviewRatings = {};

        for (String iphoneReview : iphoneReviews) {
            String[] words = iphoneReview.split(" ");
            for (String word : words) {
                    
            }
        }

        

        //Finding the average for each review:
        int iphoneReviewRatingAverage = reviewRatingAverage(iphoneReviewRatings);
        int androidReviewRatingAverage = reviewRatingAverage(androidReviewRatings);

        //Displaying the averages in the terminal
        System.out.println("The Average Review Rating for iPhone is " + iphoneReviewRatingAverage);
        System.out.println("The Average Review Rating for iPhone is " + androidReviewRatingAverage); 

        
    }
}       
