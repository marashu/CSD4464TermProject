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

    /**
     * method for making tables
     * @param args
     */
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
            
            //Set the correct answer
            SetCorrectAnswer(conn);
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
     * @param conn
    */
    public static void DropTable(Connection conn)
    {
        System.out.println("Checking for existing tables.");
		
        try
        {
            // Get a Statement object.
            Statement stmt  = conn.createStatement();;

            //drop a couple constraints that prevent tables from dropping
            try
            {
                // Drop the Answers table.
                stmt.execute("ALTER TABLE questions DROP CONSTRAINT QUESTIONS_CORRECT_ANSWER_ID_FK");
                System.out.println("Removed FK from questions");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            try
            {
                // Drop the Answers table.
                stmt.execute("ALTER TABLE scores DROP CONSTRAINT scores_player_id_fk");
                System.out.println("Removed FK from scores");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            
            //now drop the tables
            try
            {
                // Drop the Answers table.
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
                // Drop the Scores table.
                stmt.execute("DROP TABLE scores");
                System.out.println("Scores table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
            
            

        }
        catch(SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    //A function to add the players table

    /**
     * method for creating players table
     * @param conn
     */
    public static void BuildPlayersTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE players(" +
                "player_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                "username VARCHAR(30)  NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
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

    /**
     * method for creating score table 
     * @param conn
     */
    public static void BuildScoresTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("CREATE TABLE scores(" +
                "score_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,  INCREMENT BY 1)," +
                "player_id INTEGER NOT NULL," +
                "total_score DEC(9,0))");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Scores table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("SCORES TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the questions table

    /**
     * method for creating question table 
     * @param conn
     */
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
                "correct_answer_id INTEGER)");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Questions table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("QUESTIONS TABLE ERROR: " + ex.getMessage());
        }
    }
    
    //A function to add the questions table

    /**
     * method for builing answers table
     * @param conn
     */
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

    /**
     * method for inserting questions
     * @param conn
     */
    public static void PopulateQuestionsTable(Connection conn)
    {
        try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
         
            // Create the table.
            stmt.execute("INSERT INTO questions " +
                "VALUES (401, 'What is the longest river in the world?',NULL)," +
                "(402, 'How many megabytes are in one gigabyte?',NULL)," +
                "(403, 'Which Apollo mission landed the first humans on the Moon?',NULL)," +
                "(404, 'What does 8 * 8 equal?',NULL)," +
                "(405, 'What country has the longest coastline?',NULL)," +
                "(406, 'What did the \"D\" in \"D-Day\" stand for?',NULL)," +
                "(407, 'Who holds the record for the most victories in a row on the professional golf tour?',NULL)," +
                "(408, 'What element in the periodic table has the symbol of ''S''?',NULL)," +
                "(409, 'What does 162 / 9 equal?',NULL)," +
                "(410, 'Who is the 20th Prime Minister of Canada?',NULL)," +
                "(411, 'What is the lowest layer of the earth''s atmosphere?',NULL),"
                    + "(412, 'Which U. S. State provided the title of a Bee Gees hit single?',NULL),"
                    + "(413, 'Who is the first person a newly elected pope meets?',NULL),"
                    + "(414, 'In Greek mythology, who was Minos'' mother?',NULL),"
                    + "(415, 'Vienna is the setting for what Shakespeare play?',NULL),"
            + "(416, 'Who won Superbowl XLVII/48 in 2014?',NULL),"
            + "(417, 'Who won the Oscar for best picture in 2009?',NULL),"
            + "(418, 'What is the national sport of Canada?',NULL),"
            + "(419, 'What year was Pacman first released?',NULL),"
            + "(420, 'What band has won the most grammys?',NULL),"
            + "(421, 'Which country has the largest population of wild tigers?',NULL),"
            + "(422, 'What is the capital of Egypt?',NULL),"
            + "(423, 'Which physiologist is famous for his work in classical conditioning?',NULL),"
            + "(424, 'Which country is the hottest in the world?',NULL),"
            + "(425, 'Which dog breed is the most popular in the United States?',NULL)");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Questions table populated.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    //A function to add the questions table

    /**
     * method for inserting answers table
     * @param conn
     */
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
                "(540, 410, 'Paul Martin'),"
                    + "(541, 411, 'Thermosphere'),"
                    + "(542, 411, 'Mesosphere'),"
                    + "(543, 411, 'Stratosphere'),"
                    + "(544, 411, 'Troposphere'),"
                    + "(545, 412, 'Carolina'),"
                    + "(546, 412, 'Louisiana'),"
                    + "(547, 412, 'California'),"
                    + "(548, 412, 'Massachusetts'),"
                    + "(549, 413,'The president of Italy'),"
                    + "(550, 413,'The monarch of England'),"
                    + "(551, 413,'The president of the United States'),"
                    + "(552, 413,'His tailor'),"
                    + "(553, 414,'Arachne'),"
                    + "(554, 414,'Persephone'),"
                    + "(555, 414,'Lysistrata'),"
                    + "(556, 414,'Europa'),"
                    + "(557, 415,'Taming of the Shrew'),"
                    + "(558, 415,'The Merchant of Venice'),"
                    + "(559, 415,'As You Like It'),"
                    + "(560, 415,'Measure for Measure'),"
            + "(561, 416,'Denver Broncos'),"
            + "(562, 416,'Green Bay Packers'),"
            + "(563, 416,'New England Patriots'),"
            + "(564, 416,'Seattle Seahawks'),"
            + "(565, 417,'District 9'),"
            + "(566, 417,'Avatar'),"
            + "(567, 417,'Up'),"
            + "(568, 417,'The Hurt Locker'),"
            + "(569, 418,'Hockey'),"
            + "(570, 418,'Baseball'),"
            + "(571, 418,'Skiing'),"
            + "(572, 418,'Lacrosse'),"
            + "(573, 419,'1983'),"
            + "(574, 419,'1986'),"
            + "(575, 419,'1985'),"
            + "(576, 419,'1980'),"
            + "(577, 420,'Foo Fighters'),"
            + "(578, 420,'Metallica'),"
            + "(579, 420,'Coldplay'),"
            + "(580, 420,'U2'),"
            + "(581, 421,'Indonesia'),"
            + "(582, 421,'Russia'),"
            + "(583, 421,'Malaysia'),"
            + "(584, 421,'India'),"
            + "(585, 422,'Alexandria'),"
            + "(586, 422,'Aswan'),"
            + "(587, 422,'Luxor'),"
            + "(588, 422,'Cairo'),"
            + "(589, 423,'Wilhelm Wundt'),"
            + "(590, 423,'Raymond Heimbecker'),"
            + "(591, 423,'Claude Bernard'),"
            + "(592, 423,'Ivan Pavlov'),"
            + "(593, 424,'Algeria'),"
            + "(594, 424,'Saudi Arabia'),"
            + "(595, 424,'Iraq'),"
            + "(596, 424,'Libya'),"
            + "(597, 425,'German Shepherd'),"
            + "(598, 425,'French Bulldog'),"
            + "(599, 425,'Golden Retriever'),"
            + "(600, 425,'Labrador Retriever')");
            
            
            
            //Output to show the database has been successfully created
            System.out.println("Answers table populated.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
        
        
    }

    /**
     * method for adding constraints
     * @param conn
     */
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
            
            
            
            //add table constraints
            stmt.execute("ALTER TABLE scores " +
                "ADD CONSTRAINT scores_score_id_pk " +
                "PRIMARY KEY(score_id)");
            
            stmt.execute("ALTER TABLE scores " +
                "ADD CONSTRAINT scores_player_id_fk " +
                "FOREIGN KEY(player_id) " +
                "REFERENCES players(player_id)");
            
            
            
            //add table constraints
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_question_id_pk " +
                "PRIMARY KEY(question_id)");
            
            
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_question_uq " +
                "UNIQUE(question)");
            
            
            
            //add table constraints
            stmt.execute("ALTER TABLE answers " +
                "ADD CONSTRAINT answers_answer_id_pk " +
                "PRIMARY KEY(answer_id)");
            
            stmt.execute("ALTER TABLE questions " +
                "ADD CONSTRAINT questions_correct_answer_id_fk " +
                "FOREIGN KEY(correct_answer_id) " +
                "REFERENCES answers(answer_id)");
            
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
    
    /**
     * method for update correct answer
     * @param conn
     */
    public static void SetCorrectAnswer(Connection conn)
    {
         try
	{
            // Get a Statement object.
            Statement stmt = conn.createStatement();
            
           
            
            stmt.execute("UPDATE questions SET correct_answer_id=503"
                    + "WHERE question_id=401" );
            stmt.execute("UPDATE questions SET correct_answer_id=508"
                    + "WHERE question_id=402" );
            stmt.execute("UPDATE questions SET correct_answer_id=511"
                    + "WHERE question_id=403" );
            stmt.execute("UPDATE questions SET correct_answer_id=516"
                    + "WHERE question_id=404" );
            stmt.execute("UPDATE questions SET correct_answer_id=517"
                    + "WHERE question_id=405" );
            stmt.execute("UPDATE questions SET correct_answer_id=522"
                    + "WHERE question_id=406" );
            stmt.execute("UPDATE questions SET correct_answer_id=527"
                    + "WHERE question_id=407" );
            stmt.execute("UPDATE questions SET correct_answer_id=530"
                    + "WHERE question_id=408" );
            stmt.execute("UPDATE questions SET correct_answer_id=535"
                    + "WHERE question_id=409" );
            stmt.execute("UPDATE questions SET correct_answer_id=539"
                    + "WHERE question_id=410" );
            stmt.execute("UPDATE questions SET correct_answer_id=544"
                    + "WHERE question_id=411" );
            stmt.execute("UPDATE questions SET correct_answer_id=548"
                    + "WHERE question_id=412" );
            stmt.execute("UPDATE questions SET correct_answer_id=552"
                    + "WHERE question_id=413" );
            stmt.execute("UPDATE questions SET correct_answer_id=556"
                    + "WHERE question_id=414" );
            stmt.execute("UPDATE questions SET correct_answer_id=560"
                    + "WHERE question_id=415" );
            stmt.execute("UPDATE questions SET correct_answer_id=564"
                    + "WHERE question_id=416" );
            stmt.execute("UPDATE questions SET correct_answer_id=568"
                    + "WHERE question_id=417" );
            stmt.execute("UPDATE questions SET correct_answer_id=572"
                    + "WHERE question_id=418" );
            stmt.execute("UPDATE questions SET correct_answer_id=576"
                    + "WHERE question_id=419" );
            stmt.execute("UPDATE questions SET correct_answer_id=580"
                    + "WHERE question_id=420" );
            stmt.execute("UPDATE questions SET correct_answer_id=584"
                    + "WHERE question_id=421" );
            stmt.execute("UPDATE questions SET correct_answer_id=588"
                    + "WHERE question_id=422" );
            stmt.execute("UPDATE questions SET correct_answer_id=592"
                    + "WHERE question_id=423" );
            stmt.execute("UPDATE questions SET correct_answer_id=596"
                    + "WHERE question_id=424" );
            stmt.execute("UPDATE questions SET correct_answer_id=600"
                    + "WHERE question_id=425" );
            
            //Output to show the database has been successfully created
            System.out.println("Updated answers");
        }
        catch (SQLException ex)
        {
            System.out.println("UPDATE ERROR: " + ex.getMessage());
        }
    }
}
