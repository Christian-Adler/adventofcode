import java.util.ArrayList;

public class Rating {
  public final ArrayList<RatingItem> ratingItems;

  private Rating(ArrayList<RatingItem> ratingItems) {
    this.ratingItems = ratingItems;
  }

  public static Rating from(String input) {
    String[] split = Util.cleanFrom(input, "{", "}").split(",");
    ArrayList<RatingItem> ratingItems = new ArrayList<>();
    for (String s : split) {
      ratingItems.add(RatingItem.from(s));
    }
    return new Rating(ratingItems);
  }

  @Override
  public String toString() {
    return "Rating{" +
        "ratingItems=" + ratingItems +
        '}';
  }
}