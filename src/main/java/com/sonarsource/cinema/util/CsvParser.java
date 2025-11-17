package com.sonarsource.cinema.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.sonarsource.cinema.exception.CsvParsingException;
import com.sonarsource.cinema.model.FoodItem;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser {

  public List<FoodItem> parseCsv(String csvUrl) {
    List<FoodItem> foodItemList = new ArrayList<>();
    InputStreamReader streamReader = getStreamReader(csvUrl);
    try (CSVReader csvReader = new CSVReaderBuilder(streamReader).withSkipLines(1).withCSVParser(getCsvParser()).build()) {
      String[] values;
      while ((values = csvReader.readNext()) != null) {

        long foodId = Long.parseLong(safeString(values[0]));
        String foodItem = safeString(values[1]);
        BigDecimal price = new BigDecimal(safeString(values[2]));

        foodItemList.add(new FoodItem(foodId, foodItem, price));
      }
      return foodItemList;
    } catch (IOException | CsvValidationException e) {
      throw new CsvParsingException(e.getMessage());
    }
  }

  private String safeString(String value) {

    if(value == null || value.isEmpty()){
      throw new IllegalArgumentException("Empty or Null field not allowed");
    }

    return value.trim();
  }


  private static InputStreamReader getStreamReader(String csvUrl)  {
    try {
      return new InputStreamReader(new URL(csvUrl).openStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new CsvParsingException(e.getMessage());
    }
  }

  private static CSVParser getCsvParser() {
    return new CSVParserBuilder()
      .withSeparator(';')
      .withIgnoreQuotations(true)
      .build();
  }

}
