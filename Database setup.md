My db in MySQL Workbench looks like this:

Database: daily_reviews

Conatins 1 table: Past_Reviews

            Past Reviews Has attributes: review_date - DATE   NOT NULL   PRIMARY KEY,
                                         review - TEXT

Potential additions: 

  Table of common chores / tasks that can be used to count how many times the word was used / times it was done on a certain date's review
  
  Table of users to simulate or actually have multiple users using the application and only letting them view their own reviews: this will require rebuilding the past_reviews table due to the date being the primary key 
