/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author c0692516
 */
public class CreateTables {
     public static void main(String[] args)
    {
        // Create a named constant for the URL.
        // NOTE: This value is specific for Java DB.
        final String DB_URL = "jdbc:derby://localhost:1527/TriviaGame;user=root;password=password";
        Connection conn = null;
        try
        {
            // Create a connection to the database.
            conn = DriverManager.getConnection(DB_URL);
					 
            // If the DB already exists, drop the tables.
            DropTable(conn);
            
            // Build the tables.
            BuildPlayersTable(conn);
            BuildScoresTable(conn);
            BuildQuestionsTable(conn);
            BuildAnswersTable(conn);
            
            AddConstraints(conn);

            //populate the questions and the answers
            PopulateQuestionsTable(conn);
            PopulateAnswersTable(conn);
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
        finally
        {
            try {
                if (conn != null) 
                { 
                    //close the connection
                    conn.close(); 
                }
            }
            catch (Exception ex) {
                // log this error
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
     
     /**
    * The dropTables method drops any existing
    * in case the database already exists.
    */
    public static void DropTable(Connection conn)
    {
        System.out.println("Checking for existing tables.");
		
        try
        {
            // Get a Statement object.
            Statement stmt  = conn.createStatement();;

            try
            {
                // Drop the Players table.
                stmt.execute("DROP TABLE players");
                System.out.println("Players table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the Scores table.
                stmt.execute("DROP TABLE scores");
                System.out.println("Scores table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the Questions table.
                stmt.execute("DROP TABLE questions");
                System.out.println("Questions table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the UnpaidOrder table.
                stmt.execute("DROP TABLE answers");
                System.out.println("Answers table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the UnpaidOrder table.
                stmt.execute("DROP SEQUENCE players_seq");
                System.out.println("Players sequence dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The sequence simply did not exist.
            }
            try
            {
                // Drop the UnpaidOrder table.
                stmt.execute("DROP SEQUENCE scores_seq");
                System.out.println("Scores sequence dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The sequence simply did not exist.
            }

        }
        catch(SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    //A function to add the players table
    public static void BuildPlayersTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE players(" +
                "player_id INTEGER NOT NULL," +
                "username VARCHAR(30)  NOT NULL," +
                "password VARCHAR(40) NOT NULL," +
                "email_address VARCHAR(40)  NOT NULL," +
                "creation_date DATE," +
                "first_name VARCHAR(20)," +
                "last_name VARCHAR(20))");
            
            //Output to show the database has been successfully created
            System.out.println("Players table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("PLAYER TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the scores table
    public static void BuildScoresTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE scores(" +
                "score_id INTEGER NOT NULL," +
                "player_id INTEGER NOT NULL," +
                "total_score DEC(9,0)," +
                "average_score DEC(9,0)," +
                "highest_score DEC(9,0))");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Scores table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("SCORES TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the questions table
    public static void BuildQuestionsTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE questions(" +
                "question_id INTEGER NOT NULL," +
                "question VARCHAR(100) NOT NULL," +
                "correct_answer_id INTEGER NOT NULL)");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Questions table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("QUESTIONS TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the questions table
    public static void BuildAnswersTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE answers(" +
                "Answer_id INTEGER NOT NULL," +
                "Question_id INTEGER NOT NULL," +
                "Answer VARCHAR(100) NOT NULL)");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Answers table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("ANSWERS TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the questions table
    public static void PopulateQuestionsTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("INSERT INTO questions " +
                "VALUES (401, 'What is the longest river in the world?', 503)," +
                "(402, 'How many megabytes are in one gigabyte?', 508)," +
                "(403, 'Which Apollo mission landed the first humans on the Moon?', 511)," +
                "(404, 'What does 8 * 8 equal?', 516)," +
                "(405, 'What country has the longest coastline?', 517)," +
                "(406, 'What did the \"D\" in \"D-Day\" stand for?', 522)," +
                "(407, 'Who holds the record for the most victories in a row on the professional golf tour?', 527 )," +
                "(408, 'What element in the periodic table has the symbol of ''S''?',530 )," +
                "(409, 'What does 162 / 9 equal?', 535)," +
                "(410, 'Who is the 20th Prime Minister of Canada?', 539)");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Questions table populated.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    //A function to add the questions table
    public static void PopulateAnswersTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("INSERT INTO answers " +
                "VALUES (501, 401, 'Congo River')," +
                "(502, 401, 'Amazon River')," +
                "(503, 401, 'Nile River')," +
                "(504, 401, 'Yangtze River')," +
                "(505, 402, '100')," +
                "(506, 402, '500')," +
                "(507, 402, '10000')," +
                "(508, 402, '1000')," +
                "(509, 403, '7')," +
                "(510, 403, '9')," +
                "(511, 403, '11')," +
                "(512, 403, '13')," +
                "(513, 404, '80')," +
                "(514, 404, '68')," +
                "(515, 404, '72')," +
                "(516, 404, '64')," +
                "(517, 405, 'Canada')," +
                "(518, 405, 'Russia')," +
                "(519, 405, 'Chile')," +
                "(520, 405, 'Australia')," +
                "(521, 406, 'Day')," +
                "(522, 406, 'Doom')," +
                "(523, 406, 'Dwight')," +
                "(524, 406, 'Dunkirk')," +
                "(525, 407, 'Jack Nicklaus')," +
                "(526, 407, 'Arnold Palmer')," +
                "(527, 407, 'Byron Nelson')," +
                "(528, 407, 'Ben Hogan')," +
                "(529, 408, 'Sodium')," +
                "(530, 408, 'Sulfur')," +
                "(531, 408, 'Silicon')," +
                "(532, 408, 'Selenium')," +
                "(533, 409, '15')," +
                "(534, 409, '19')," +
                "(535, 409, '18')," +
                "(536, 409, '16')," +
                "(537, 410, 'Stephen Harper')," +
                "(538, 410, 'Justin Trudeau')," +
                "(539, 410, 'Jean Chretien')," +
                "(540, 410, 'Paul Martin')");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Answers table populated.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
        
        
    }
    public static void AddConstraints(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
            
            //add table constraints
            stmt.execute("ALTER TABLE players " +
                "ADD CONSTRAINT players_player_id_pk " +
                "PRIMARY KEY(player_id)");
            
            stmt.execute("ALTER TABLE players " +
                "ADD CONSTRAINT players_username_uq " +
                "UNIQUE(username)");
            
            stmt.execute("ALTER TABLE players " +
                "ADD CONSTRAINT players_email_address_uq " +
                "UNIQUE(email_address)");
            
            stmt.execute("CREATE SEQUENCE players_seq " +
                "START WITH 1 " +
                "MAXVALUE 100 " +
                "INCREMENT BY 1 " );
            
            //add table constraints
            stmt.execute("ALTER TABLE scores " +
                "ADD CONSTRAINT scores_score_id_pk " +
                "PRIMARY KEY(score_id)");
            
            stmt.execute("ALTER TABLE scores " +
                "ADD CONSTRAINT scores_player_id_fk " +
                "FOREIGN KEY(player_id) " +
                "REFERENCES players(player_id)");
            
            stmt.execute("CREATE SEQUENCE scores_seq " +
                "START WITH 101 " +
                "MAXVALUE 200 " +
                "INCREMENT BY 1 " );
            
            //add table constraints
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_question_id_pk " +
                "PRIMARY KEY(question_id)");
            
            stmt.execute("ALTER TABLE questions" +
                "ADD CONSTRAINT questions_correct_answer_id_pk " +
                "PRIMARY KEY(correct_answer_id)");
            
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_question_uq " +
                "UNIQUE(question)");
            
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_correct_answer_id_fk " +
                "FOREIGN KEY(correct_answer_id) " +
                "REFERENCES answers(answer _id)");
            
            //add table constraints
            stmt.execute("ALTER TABLE answers " +
                "ADD CONSTRAINT answers_answer_id_pk " +
                "PRIMARY KEY(answer_id)");
            
            stmt.execute("ALTER TABLE answers " +
                "ADD CONSTRAINT answers_answer_uq " +
                "UNIQUE(answer)");
            
            stmt.execute("ALTER TABLE answers " +
                "ADD CONSTRAINT answers_question_id_fk " +
                "FOREIGN KEY(question_id) " +
                "REFERENCES questions(question_id)");
            
            //Output to show the database has been successfully created
            System.out.println("Constraints added.");
        }
        catch (SQLException ex)
        {
            System.out.println("CONSTRAINT ERROR: " + ex.getMessage());
        }
    }
}
