import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please enter a 1 if you would like to insert/delete a review \nPlease enter a 2 if you would like to read a previous review\n");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character after nextInt()

        // 1 = Insertion or deletion
        if (choice == 1) {
            System.out.println("Please enter a 1 again if you would like to insert a review\nPlease enter a 2 if you would like to delete a review\n");

            // 1 again = Insert review
            if (sc.nextInt() == 1) {
                sc.nextLine(); // Consume the newline character after nextInt()
                System.out.println("Please write today's date or the date of the review you would like to insert as follows:\nYYYY-MM-DD WITH DASHES INCLUDED!");
                String date = sc.nextLine();

                System.out.println("Please write your review in less than 64,000 Characters\n");
                String review = sc.nextLine();
                insertReview(review, date);

                // 2 = Delete a review
            } else if (sc.nextInt() == 2) {
                sc.nextLine(); // Consume the newline character after nextInt()
                System.out.println("Please enter the date of the review you want to delete as follows:\nYYYY-MM-DD WITH DASHES INCLUDED!");
                String deletionDate = sc.nextLine();
                deleteReview(deletionDate);
            }

            // 2 = Read a review
        } else if (choice == 2) {
            sc.nextLine(); // Consume the newline character after nextInt()
            System.out.println("Please enter the date of the review you would like to read as follows:\nYYYY-MM-DD WITH DASHES INCLUDED!\n");
            String date = sc.nextLine();
            System.out.println("The review for " + date + " states: " + getReview(date));

            // Anything other than 1 or 2 is entered
        } else {
            System.out.println("An error in your response was detected, shutting down :(");
            System.exit(0);
        }
    }

    private static boolean insertReview(String review, String date) {
        //Insert review into a new entry of Daily Review database -> Past Reviews table as (curr date, review)
        String query = "insert into Past_Reviews (review_date, review) values(?, ?) ";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, date); // Set the review_date
            stmt.setString(2, review); // Set the review_text

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the insert was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    private static String getReview(String date) {
        //Date is a user entered string representing YYYY-MM-DD which is primary key to query sql table
        String query = "Select * from Past_Reviews where review_date = ?";
        String review = "";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date); // Set the review_date
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    review = rs.getString("review");
                } else {
                    return "There was no review or the date does not exist.";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    private static void deleteReview(String date) {
        //In the case of accidental submission, spelling errors, etc...
        // delete a review and allow user chance to redo but limit range of redo, not change a year-old entry
        String query = "Delete from Past_Reviews where review_date = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date); // Set the review_date
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The review has been successfully deleted.");
            } else {
                System.out.println("No review found with the specified date.");
            }
        }   catch (SQLException e) {
            System.out.println("An error occurred while deleting the review");
                e.printStackTrace();
            }
        }
    }