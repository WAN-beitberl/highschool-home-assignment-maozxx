package database;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newjdbc",
                    "root", "zxczxc123");


            Statement statment = connection.createStatement();

            ResultSet resultSet = null;
            int x =0;
        while (x != 8) {
            System.out.println("Enter number input 1-7  8 to end");
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
           x = myObj.nextInt();
            switch (x) {
                case 1: // get school avg
                {
                    resultSet = statment.executeQuery("SELECT AVG(grade_avg) AS 'Average Grade'\n" +
                            "FROM highschool\n" +
                            ";");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getDouble("Average Grade"));
                    }
                    break;
                }
                case 2: {// avg men grade
                    resultSet = statment.executeQuery("SELECT AVG(grade_avg) AS 'Average men grade'" +
                            "FROM highschool where gender = 'Male';");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getDouble("Average men grade"));
                    }
                    break;
                }
                case 3: { // avg female grade
                    resultSet = statment.executeQuery("SELECT AVG(grade_avg) AS 'Average Female grade'\n" +
                            "FROM highschool\n" +
                            "where gender = 'Female' \n" +
                            ";");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getDouble("Average Female grade"));
                    }
                    break;

                }

                case 4: { // avg hieght with purple car
                    resultSet = statment.executeQuery("SELECT avg(cm_height) as dickheads"+
                         " FROM highschool"
                          + " where cm_height > 200 and car_color = 'Purple' and has_car = 'true'"
                 + ";");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getDouble("dickheads"));
                    }
                    break;

                }

                case 5: { // get all friends and freinds id
                    System.out.println("Enter id for student 1-1000");
                    String stemp = myObj.next();
                    resultSet = statment.executeQuery("select friend_id,other_friend_id,id from highschool_friendships\n" +
                            " where highschool_friendships.friend_id = "+stemp+" or highschool_friendships.other_friend_id  =" +stemp+" ;");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt("friend_id"));
                    }
                    resultSet = statment.executeQuery("WITH friends_of_friends AS (\n" +
                            "  SELECT other_friend_id FROM highschool_friendships WHERE friend_id = "+stemp+"\n" +
                            "  UNION\n" +
                            "  SELECT friend_id FROM highschool_friendships WHERE other_friend_id = "+stemp+"\n" +
                            "),\n" +
                            "all_friends AS (\n" +
                            "  SELECT friend_id FROM highschool_friendships WHERE other_friend_id IN (SELECT * FROM friends_of_friends)\n" +
                            "  UNION\n" +
                            "  SELECT other_friend_id FROM highschool_friendships WHERE friend_id IN (SELECT * FROM friends_of_friends)\n" +
                            ")\n" +
                            "SELECT * FROM all_friends;");

                    while (resultSet.next()) {
                        int itemps = Integer.parseInt(stemp); int chec = resultSet.getInt("friend_id");
                        if ( chec != 0 && chec != itemps) {
                            System.out.println(resultSet.getInt("friend_id"));
                        }
                    }


                    break;
                }
                case 6: { // popularity ranking
                resultSet = statment.executeQuery("WITH friend_counts AS (\n" +
                                "  SELECT \n" +
                                "    idd, \n" +
                                "    (SELECT COUNT(*) FROM highschool.highschool_friendships WHERE (friend_id = idd OR other_friend_id = idd) and (idd <> 0)) as number_of_friends\n" +
                                "  FROM \n" +
                                "    (SELECT DISTINCT friend_id as idd FROM highschool.highschool_friendships\n" +
                                "    UNION\n" +
                                "    SELECT DISTINCT other_friend_id FROM highschool.highschool_friendships) as ids\n" +
                                ")\n" +
                                "\n" +
                                "SELECT \n" +
                                "  number_of_friends, \n" +
                                "  COUNT(*) as total, \n" +
                                "  ROUND(COUNT()/(SELECT COUNT() FROM friend_counts)*100, 2) as percentage\n" +
                                "FROM friend_counts\n" +
                                "GROUP BY number_of_friends\n" +
                                "ORDER BY number_of_friends;");
                        double sump=0;
                        double sumonep=0;
                        int counter = 0;
                        while (resultSet.next()) {
                            if(resultSet.getInt("number_of_friends")==1)
                            {
                                sumonep=resultSet.getDouble("percentage");
                                System.out.println("the percentage of students that have one friend is: "+sumonep+"%");
                            }
                            else
                            {
                                if(resultSet.getInt("number_of_friends")!=0)
                                {
                                sump += resultSet.getDouble("percentage");
                                }
                            }
                        }
                        System.out.println("the percentage of students that have two and above friends is: "+sump+"%");
                        System.out.println("the percentage of students that have no friends is: "+(100-sump-sumonep)+"%");
                        break;

                }
                case 7: { // using the view
                    System.out.println("Enter id for identification");
                    int id = myObj.nextInt();
                    resultSet = statment.executeQuery("SELECT grade_avg FROM new_view WHERE identification_card = " +id+";\n");

                    if (resultSet.next()) {
                        System.out.println("The grade point average for student with ID card " + id + " is " + resultSet.getDouble("grade_avg"));
                    } else {
                        System.out.println("No records found for student with ID card " + id);
                    }

                    break;

                }
                case 8:{ // end all
                    return;
                }
                case 9: { // get the 1000
                    resultSet = statment.executeQuery("select * from highschool_friendships \n" +
                            "join highschool on \n" +
                            "highschool_friendships.id =highschool.id\n");
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("first_name"));
                    }
                    break;

                }
            }
        }


            } catch(Exception e){ // incase of error
                e.printStackTrace();
            }

        }
    }


    //  public void inputer() {

    //}
