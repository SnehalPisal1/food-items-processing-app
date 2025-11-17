package com.sonarsource.cinema;

import com.sonarsource.cinema.exception.GlobalException;
import com.sonarsource.cinema.model.FoodItem;
import com.sonarsource.cinema.service.FoodService;
import com.sonarsource.cinema.util.CsvParser;

import java.util.List;
import java.util.logging.Logger;

public class Main {
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public static void main(String[] args) {
    logger.info("Welcome to SonarSource Cinema!");
    try {
      List<FoodItem> foodItemList = printMovies();

      FoodService foodService = new FoodService(foodItemList);
      if (args.length < 1) {
        throw new IllegalArgumentException("Empty Input");
      }
      switch (args[0]) {
        case "--sort":
          if (args.length < 2 || args[1].isEmpty()) {
            throw new IllegalArgumentException("Argument is missing");
          }
          logger.info("---Sort Operation---");
          logger.info("Sorted Food Items :");

          List<FoodItem> sorted = foodService.sortByPrice(args[1]);
          sorted.forEach(System.out :: println);
          break;

        case "--filter-price":
          logger.info("---Filter Operation---");
          if (args.length < 3 || args[1].isEmpty() || args[2].isEmpty()) {
            throw new IllegalArgumentException("Argument is missing");
          }
          List<FoodItem> filtered = foodService.filterPrice(args[1], args[2]);
          filtered.forEach(System.out :: println);
          break;
        case "--filter-name":
          if (args.length < 2 || args[1].isEmpty()) {
            throw new IllegalArgumentException("Argument is missing");
          }
          List<FoodItem> filteredByName = foodService.filterName(args[1]);
          filteredByName.forEach(System.out :: println);
          break;

        case "--stats":
          foodService.statistics();
          break;
        default:
          throw new IllegalArgumentException("Invalid input");
      }
    }catch(Exception ex){
      GlobalException.handleException(ex);
    }
  }

  public static List<FoodItem> printMovies() {
    CsvParser csvParser = new CsvParser();
    return  csvParser.parseCsv("https://raw.githubusercontent.com/aurelien-poscia-sonarsource/sonar-cinema/refs/heads/main/src/main/resources/food.csv");

  }
}
