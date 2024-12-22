package aoc;

import aoc.util.Util;

import java.util.*;

public class Secret {
  private long secret = 0;
  private final List<Long> priceDifferences = new ArrayList<>();
  private final List<Long> priceList = new ArrayList<>();
  private final Map<String, Long> sublistKey2Price = new HashMap<>();

  public Secret(long secret) {
    this.secret = secret;
  }

  public long getSecret() {
    return secret;
  }

  public long getPrice() {
    return secret % 10;
  }

  public List<Long> getPriceDifferences() {
    return priceDifferences;
  }

  public List<Long> getPriceList() {
    return priceList;
  }

  public void buildCache() {
    for (int i = 4; i < priceDifferences.size(); i++) {
      List<Long> subList = priceDifferences.subList(i - 4, i);
      String visitedKey = subList.toString();
      if (sublistKey2Price.containsKey(visitedKey))
        continue;

      sublistKey2Price.put(visitedKey, getPriceList().get(i - 1));
    }
  }

  public Collection<String> getCacheKeys() {
    return sublistKey2Price.keySet();
  }

  public Long getPriceForSubListKey(String subListKey) {
    return sublistKey2Price.get(subListKey);
  }

  public void evolve() {
    long soFarPrice = getPrice();
    mixAndPrune(secret * 64);
    mixAndPrune(secret / 32);
    mixAndPrune(secret * 2048);
    priceDifferences.add(getPrice() - soFarPrice);
    priceList.add(getPrice());
  }

  private void mixAndPrune(long mixVal) {
    mix(mixVal);
    prune();
  }

  private void mix(long mixVal) {
    secret = secret ^ mixVal;
  }

  private void prune() {
    secret = secret % 16777216;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\r\n");
    for (int i = 0; i < priceList.size(); i++) {
      builder.append(priceList.get(i)).append(" ").append(priceDifferences.get(i)).append("\r\n");

    }

    return Util.leftPad(secret, 10, " ") + ": " + +getPrice() + builder.toString();
  }
}
