package uk.ac.ncl.workshop_admin.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.FileReader;

public class CSVReader {

    public static void main(String[] args) {
      try(
          BufferedReader br = new BufferedReader(new FileReader("uniFormat.csv"));
          CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
      ) {
        for(CSVRecord record : parser) {
          System.out.println(record.get("user profile"));
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
