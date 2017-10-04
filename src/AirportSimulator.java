import java.util.Scanner;
import java.util.Random;
import java.util.LinkedList;

class AirportSimulator {

  // Inputs
  static int min_land = 4;
  static int min_takeoff = 2;
  static int average_land;
  static int average_takeoff;
  static int max_time;
  static int total_sim = 6000;
  static int current_time = 0;
  static int probability_arrival = 5;
  static int probability_takeoff = 5;
  static int average_time_between_takeoff = 20;
  static int average_time_between_landing = 20;

  // Outputs
  static int planes_taken_off;
  static int planes_landed;
  static int planes_crashed;
  static int average_wait_takeoff;
  static int average_wait_land;

  static int total_waiting_time_to_land;
  static int total_waiting_time_to_takeoff;

  // queues for keeping track of waiting times
  static LinkedList<Integer> planes_waiting_to_takeoff = new LinkedList<Integer>();
  static LinkedList<Integer> planes_waiting_to_land = new LinkedList<Integer>();

  public static void main(String[] args) {

    // Simulate until a specified time where current_time reflects minutes elasped
    //in the simulation
    while (current_time < total_sim) {

      // TODO do something

      while (!planes_waiting_to_land.isEmpty()) {

        int time_in = planes_waiting_to_land.remove();

        // Plane already crashed
        if (current_time - time_in > min_land) {

          planes_crashed++;

          // Landed successfully
        } else {

          total_waiting_time_to_land += current_time - time_in;

          planes_landed++;
        }

        check(min_land);
      }

      while (planes_waiting_to_land.isEmpty() && !planes_waiting_to_takeoff.isEmpty()) {

        int time_in = planes_waiting_to_takeoff.remove();

        total_waiting_time_to_takeoff += current_time - time_in;

        planes_taken_off++;

        check(min_takeoff);

      }


      // Checks every 20 minutes
      check(1);

    } // while loop
    System.out.println("landed: " + planes_landed);
    System.out.println("crashed: " + planes_crashed);
    System.out.println("avg wait land: " + (double) total_waiting_time_to_land / (double) planes_landed);

    System.out.println("taken off: " + planes_taken_off);
    System.out.println("avg wait takeoff: " + (double) total_waiting_time_to_takeoff/ (double) planes_taken_off);
  } // main

  public static void check(int number_of_checks) {

    int randomNum;
    int minimum= 1;
    int maximum = 100;

    for (int i = 0; i < number_of_checks; i++, current_time++) {

      // For every time interval we generate a number between 1 and 100 to determine whether
      // a plane has arrived.
      randomNum = minimum + (int)(Math.random() * maximum);

      // 5% of the time a plane can show up
      if (randomNum <= probability_takeoff) {

        //A plane showed up to takeoff at a given time, storing the time in the queue
        planes_waiting_to_takeoff.add(current_time);
      }

      // For every time interval we generate a number between 1 and 100 to determine whether
      // a plane has arrived.
      randomNum = minimum + (int)(Math.random() * maximum);

      // 5% of the time a plane can show up
      if (randomNum <= probability_arrival) {

        //
        planes_waiting_to_land.add(current_time);
      }
    }
  }
}// class
